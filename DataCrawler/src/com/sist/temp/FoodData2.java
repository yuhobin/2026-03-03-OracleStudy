package com.sist.temp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FoodData2 {
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
		FoodData2 fd=new FoodData2();
		try {
			Document doc=Jsoup.parse(fd.html, "UTF-8");
			Elements posts=doc.select("div.single-post");
			// System.out.println(post.toString());
			for(Element post:posts) {
				String title=post.select(".post-headline").text();
				String type=post.select(".post-author").text();
				String phone=post.select(".post-date").text();
				String img=post.select(".post-thumb img").attr("src");
				System.out.println(title);
				System.out.println(type);
				System.out.println(phone);
				System.out.println(img);
				
			}
		} catch (Exception e) {}
	}
}
