package com.sist.temp;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class FoodData3 {
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
        
        <div class="col-12 col-md-6 col-lg-4">
                    <div class="single-post wow fadeInUp" data-wow-delay="0.1s">
                        <!-- Post Thumb -->
                        <div class="post-thumb">
                        <a href="../food/detail_before.do?no=3">
                            <img src="http://menupan.com/restaurant/restimg/007/zzmenuimg/h5024574_z.jpg" alt="">
                         </a>
                        </div>
                        <!-- Post Content -->
                        <div class="post-content">
                            <div class="post-meta d-flex">
                                <div class="post-author-date-area d-flex">
                                    <!-- Post Author -->
                                    <div class="post-author">
                                        <a href="#">한식</a>
                                    </div>
                                    <!-- Post Date -->
                                    <div class="post-date">
                                        <a href="#">(063) 284-2224</a>
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
                            <a href="../food/detail_before.do?no=3">
                                <h4 class="post-headline">한국집</h4>
                            </a>
                        </div>
                    </div>
                </div>
                
                
                <div class="col-12 col-md-6 col-lg-4">
                    <div class="single-post wow fadeInUp" data-wow-delay="0.1s">
                        <!-- Post Thumb -->
                        <div class="post-thumb">
                        <a href="../food/detail_before.do?no=4">
                            <img src="http://menupan.com/restaurant/restimg/009/zzmenuimg/h4934596_z.jpg" alt="">
                         </a>
                        </div>
                        <!-- Post Content -->
                        <div class="post-content">
                            <div class="post-meta d-flex">
                                <div class="post-author-date-area d-flex">
                                    <!-- Post Author -->
                                    <div class="post-author">
                                        <a href="#">한식-일반한식</a>
                                    </div>
                                    <!-- Post Date -->
                                    <div class="post-date">
                                        <a href="#">(031) 205-4777</a>
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
                            <a href="../food/detail_before.do?no=4">
                                <h4 class="post-headline">청마루뼈다귀감자탕</h4>
                            </a>
                        </div>
                    </div>
                </div>
				"""; // python
	public static void main(String[] args) {
		FoodData3 fd=new FoodData3();
		// Jsoup / playwright => 태그 선택 => CSS Selector
		// CSS / DOM (바닐라 JS, Jquery)
		// try-with-resource : 자동으로 종료가 되면 playwright
		// 객체 생성 부분
		try(Playwright playwright=Playwright.create()) {
			// 브라우저 크롬 선택
			// launch => 브라우저를 띄운다 
			Browser browser=playwright.chromium().launch(
				new BrowserType.LaunchOptions().setHeadless(true)
				// 옵션 설정 객체 => Ajax, HTML , Vue
				// setHeadless(true) / setHeadless(false)
				// 브라우저 없이 수행 		브라우저 띄운다
			);
			// 크롬 브라우저 실행
			BrowserContext context=browser.newContext();
			Page page=context.newPage();
			
			// HTML 문자열 / file
				page.setContent(fd.html);
				// page.navigate(null) => 실제 web에서
				//
				page.waitForSelector("div.single-post");
				
				var items=page.querySelectorAll("div.single-post");
				// List items=page.querySelectorAll("div.single-post");
				// Java 10+ var
				// 자동 지정 변수 
				// var name="홍길동";
				for (ElementHandle item:items) {
					String title=getText(item, ".post-headline");
					System.out.println(title);
					
					String img=getAttr(item, "div.post-thumb img","src");
					System.out.println(img);
				}
				
		} catch (Exception e) {}
		
	}
	public static String getText(ElementHandle el, String selector) {
		ElementHandle tag=el.querySelector(selector);
		return tag!=null ? tag.innerHTML().trim():"";
	}
	public static String getAttr(ElementHandle el, String selector, String attr) {
		ElementHandle tag=el.querySelector(selector);
		return tag!=null ? tag.getAttribute(attr):"";
	}
}
