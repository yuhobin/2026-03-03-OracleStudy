package com.sist.temp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class FoodData {
	private String html="""
					<div class="col-12 col-md-6 col-lg-4">
                    <div class="single-post wow fadeInUp" data-wow-delay="0.1s">
                        <!-- Post Thumb -->
                        <div class="post-thumb">
                        <a href="../food/detail_before.do?no=1">
                            <img src="http://menupan.com/restaurant/restimg/002/zzmenuimg/d20034222_z.jpg" alt="">
                         </a>
                        </div>
                        <!-- Post Content -->
                        <div class="post-content">
                            <div class="post-meta d-flex">
                                <div class="post-author-date-area d-flex">
                                    <!-- Post Author -->
                                    <div class="post-author">
                                        <a href="#">카페/주점-카페</a>
                                    </div>
                                    <!-- Post Date -->
                                    <div class="post-date">
                                        <a href="#">(070) 8872-4418</a>
                                    </div>
                                </div>
                                <!-- Post Comment & Share Area -->
                                <div class="post-comment-share-area d-flex">
                                    <!-- Post Favourite -->
                                    <div class="post-favourite">
                                        <a href="#"><i class="fa fa-heart-o" aria-hidden="true"></i> 0</a>
                                    </div>
                                    <!-- Post Comments -->
                                    <div class="post-comments">
                                        <a href="#"><i class="fa fa-comment-o" aria-hidden="true"></i> 0</a>
                                    </div>
                                    <!-- Post Share -->
                                    <div class="post-share">
                                        <a href="#"><i class="fa fa-share-alt" aria-hidden="true"></i></a>
                                    </div>
                                </div>
                            </div>
                            <a href="../food/detail_before.do?no=1">
                                <h4 class="post-headline">아름드리카페</h4>
                            </a>
                        </div>
                    </div>
                </div>
						"""; // python
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FoodData fd=new FoodData();
		// System.out.println(fd.html);
		// Jsoup => Html 파서기
		try {
			// 파일 / 문자열 => parse / web => connection
			Document doc=Jsoup.parse(fd.html,"UTF-8");
			// System.out.println(doc);
			// 1. 데이터가 한개 / 같은 종류가 여러개
			// => Element Elements
			Element img=doc.selectFirst("div.post-thumb img");
			String src=img.attr("src"); // 속성값, img / a
			// attr("href")
			// System.out.println(src);
			Element type=doc.selectFirst("div.post-author a");
			String val=type.text();
			System.out.println(val);
			
			Element phone=doc.selectFirst("div.post-date");
			String val1=phone.text();
			System.out.println(val1);
			
			Element name=doc.selectFirst("h4");
			String val2=name.text();
			System.out.println(val2);
			
			Element data=doc.select("div.post-author-date-area a").get(1);
			System.out.println(data.text());
			
		} catch (Exception e) {}
	}

}
