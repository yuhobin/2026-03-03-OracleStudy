package com.sist.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sist.dao.DataDAO;
import com.sist.vo.FoodVO;

/*
 * https://www.menupan.com/restaurant/bestrest/bestrest.asp?pt=rt
 * 
 * https://www.menupan.com/restaurant/bestrest/bestrest.asp?pt=wk
 * 
 * https://www.menupan.com/restaurant/bestrest/bestrest.asp?pt=nw
 * 
 * https://www.menupan.com/restaurant/bestrest/bestrest.asp?trec=8628&pt=rt&page=2
 */
public class FoodCrawler {
	private static String BASE_URL="https://www.menupan.com/restaurant/bestrest/bestrest.asp?trec=8628&pt=rt";
	private static String[] category= {
			"rt",
			"wt",
			"nw"
	};
	public static void main(String[] args) {
		/*
		 * <p class="listName"><span class="restName">
		 * <a href="/restaurant/onepage.asp?acode=D200342" target="_blank">아름드리카페</a></span></p>
		 */
		/*
		 * <div class="areaBasic">
		<dl class="restName">
			<dt>업체명<span style="color:#ffffff;cursor:default;" ondblclick="$('#id_basicdata_reporter').toggle();">.</span></dt>
			<dd class="name">아름드리카페&nbsp;&nbsp;<span id="id_basicdata_reporter" style="display:none">[D200342 : 제휴영업팀]</span></dd>
		</dl>
		<dl class="restType">
			<dt>업종</dt>
			<dd class="type">카페/주점-카페</dd>
		</dl>

		<dl class="restTel">
			<dt>전화번호</dt>
			<dd class="tel1">(070) 8872-4418</dd>

		</dl>
		<dl class="restAdd">
			<dt>주소</dt>
			<dd class="add1"><a href="/map/restmap/map_search.asp?acode=D200342" target="_blank">강원 동해시 평릉동 487-1</a></dd>

			<dd class="add2">[새주소] <a href="/map/restmap/map_search.asp?acode=D200342" target="_blank">강원 동해시 평원5길 4</a></dd>

		</dl>

		<dl class="restGrade">
			<dt>평점</dt>
			<dd class="rate">
				<p class="point"><span class="star" style="width:0%"></span><!-- ☆☆☆☆☆ //--></p>
				<p class="score"><span class="total">0.0</span><span class="line">|</span><span class="count">0명 참여</span></p>
			</dd>
			<dd class="btnPoint">

				<a href="javascript:;" onClick="fn_Openmember();"><img src="/image/restaurant/onepage/btn_point.gif" alt="평가하기" /></a>

			</dd>
		</dl>

		<dl class="restTheme">
			<dt>테마</dt>
			<dd class="Theme">
		 */
		DataDAO dao=DataDAO.newInstance();
		try {
			// for(int i=0; i<category.length; i++) {
				//System.out.println("번호:"+(i+1));
				for(int p=1; p<=346; p++) {
				
				Document doc=Jsoup.connect(BASE_URL+"&page="+p).get();
				System.out.println("=================="+p+"page=======================");
				
				Elements link=doc.select("p.listName span.restName a");
				// System.out.println(link.toString());
				for(int j=0; j<link.size(); j++) {
					try {
					System.out.println(link.get(j).attr("href"));
					String url="https://www.menupan.com"+link.get(j).attr("href");
					Document doc2=Jsoup.connect(url).get();
					String name=doc2.selectFirst("div.areaBasic dl.restName dd.name").ownText().trim();
					System.out.println(name);
					
					Element type=doc2.selectFirst("div.areaBasic dl.restType dd.type");
					System.out.println(type.text());
					
					Element phone=doc2.selectFirst("div.areaBasic dl.restTel dd.tel1");
					System.out.println(phone.text());
					
					Element address=doc2.selectFirst("div.areaBasic dl.restAdd dd.add1");
					System.out.println(address.text());
					
					String strTheme="";
					try {
						Element theme=doc2.selectFirst("div.areaBasic dl.restAdd dd.theme");
						System.out.println(address.text());
						strTheme=theme.text();
					} catch (Exception e) {
						strTheme="없음";
					}
					
					
					Element score=doc2.selectFirst("div.areaBasic dl.restGrade dd.rate span.total");
					System.out.println(score.text());
					
					Element price=doc2.selectFirst("div.restPrice p.price");
					System.out.println(price.text());
					
					//time / content / reserve / parking
					// image
					Element time=doc2.selectFirst("div.tabInfo ul.tableTopA dd.txt2");
					System.out.println(time.text());
					
					Element content=doc2.selectFirst("div.tabInfo ul.tableBottom div#info_ps_f");
					System.out.println(content.text());
					
					Element reserve=doc2.select("div.tabInfo ul.tableLR dd").get(3);
					System.out.println(reserve.text());
					
					// 주차
					Elements tableLR=doc2.select("div.tabInfo ul.tableLR dt");
					Element parking=null;
					for(int k=0; k<tableLR.size(); k++) {
						String s=tableLR.get(k).text();
						if(s.equals("주차")) {
							parking=doc2.select("div.tabInfo ul.tableLR dd").get(k);
							
						}
					}
					System.out.println(parking.text());
					// System.out.println(parking.text());
					
					Element poster=doc2.selectFirst("div.areaThumbnail img#rest_bigimg");
					System.out.println(poster.attr("src"));
					
					Elements image=doc2.select("div#id_restphoto_slides img[src*=/restimg/]");
					String images="";
					for(int k=0; k<image.size(); k++) {
						images+=image.get(k).attr("src")+",";
					}
					images=images.substring(0, images.lastIndexOf(","));
					System.out.println(images);
					// * ^ $ 
					FoodVO vo=new FoodVO();
					vo.setCno(1);
					vo.setName(name);
					vo.setType(type.text());
					vo.setPhone(phone.text());
					vo.setAddress(address.text());
					vo.setTime(time.text());
					vo.setTheme(strTheme);
					vo.setPrice(price.text());
					vo.setParking(parking.text());
					vo.setReserve(reserve.text());
					vo.setContent(content.text());
					vo.setScore(Double.parseDouble(score.text().trim()));
					vo.setPoster(poster.attr("src"));
					vo.setImages(images);
					
					dao.foodInsert(vo);
					}catch(Exception e) {}
				} // for end
			} // page end
			//} // for end
			System.out.println("저장 완료!!");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
