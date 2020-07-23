<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>
	<!-- Main Slide Section -->
	<section id="slide">
		<div class="swiper-container">
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<img src="/app/img/bg1.jpg">
				</div>
				<div class="swiper-slide">
					<img src="/app/img/bg2.jpg">
				</div>
				<div class="swiper-slide">
					<img src="/app/img/bg3.jpg">
				</div>
			</div>

			<div class="swiper-pagination"></div>
		</div>

		<div class="main_title">
			<div class="title">
				<h2>Let's CodeBook</h2>
			</div>
		</div>

		<div class="card">
			<div class="swiper-container2">
				<div class="swiper-wrapper">
					<div class="swiper-slide">
						<div class="slide-item">
							<div class="img">
								<img src="img/sample1.jpg">
							</div>
							<div class="content"></div>
						</div>
					</div>
					<div class="swiper-slide">
						<div class="slide-item">
							<div class="img">
								<img src="img/sample2.jpg">
							</div>
							<div class="content"></div>
						</div>
					</div>
					<div class="swiper-slide">
						<div class="slide-item">
							<div class="img">
								<img src="img/sample3.jpg">
							</div>
							<div class="content"></div>
						</div>
					</div>
					<div class="swiper-slide">
						<div class="slide-item">
							<div class="img">
								<img src="img/sample4.jpg">
							</div>
							<div class="content"></div>
						</div>
					</div>
					<div class="swiper-slide">
						<div class="slide-item">
							<div class="img">
								<img src="img/sample5.jpg">
							</div>
							<div class="content"></div>
						</div>
					</div>
					<div class="swiper-slide">
						<div class="slide-item">
							<div class="img">
								<img src="img/sample6.jpg">
							</div>
							<div class="content"></div>
						</div>
					</div>
					<div class="swiper-slide">
						<div class="slide-item">
							<div class="img">
								<img src="img/sample7.jpg">
							</div>
							<div class="content"></div>
						</div>
					</div>
					<div class="swiper-slide">
						<div class="slide-item">
							<div class="img">
								<img src="img/sample8.jpg">
							</div>
							<div class="content"></div>
						</div>
					</div>
					<div class="swiper-slide">
						<div class="slide-item">
							<div class="img">
								<img src="img/sample9.jpg">
							</div>
							<div class="content"></div>
						</div>
					</div>
					<div class="swiper-slide">
						<div class="slide-item">
							<div class="img">
								<img src="img/sample10.jpg">
							</div>
							<div class="content"></div>
						</div>
					</div>
				</div>
			</div>

		</div>

	</section>
	<!-- Main Slide Section End-->

	<div class="margin_area"></div>

	<!-- Content Section -->

	<section id="main_content">
		<div class="container">
			<div class="mini_board">
				<div class="item">
					<div class="mini_board_title">
						<p>Notice</p>
					</div>

					<div class="mini_board_content">
						<c:forEach var="i" begin="0" end="3">
							${list[i].content}
						</c:forEach>
					</div>

					<a href="/notice/noticeList" class="item_btn gray_btn">more Notice</a>
				</div>
				<div class="item">
					<div class="mini_board_title">
						<p class="title">FAQ</p>
					</div>
				<div class="mini_board_content">
						<c:forEach var="i" begin="0" end="3">
							${free[i].content}
						</c:forEach>
					</div>
					<a href="/board/list2" class="item_btn gray_btn">more FAQ</a>
				</div>
			</div>
		</div>
	</section>

	<!-- Content Section End -->
</body>