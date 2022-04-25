<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%><!-- nhúng thưu viện -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Layout</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="/PH17307_TrinhTienLuc_Asignment_SOF3011/css/myCss.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
	integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
	<nav
		class="navbar navbar-expand-lg navbar-light bg-white py-4 fixed-top">
		<div class="container">
			<a
				class="navbar-brand justify-content-between align-items-center order-lg-0"
				href="/PH17307_TrinhTienLuc_Asignment_SOF3011/home"> <img
				src="/PH17307_TrinhTienLuc_Asignment_SOF3011/images/shopping-bag.png"
				alt=""> <span class="text-uppercase fw-lighter ms-2">ph17307</span>
			</a>
			<div class="order-lg-2">
				<a href="/PH17307_TrinhTienLuc_Asignment_SOF3011/user/cartIndex"
					class="btn position-relative"> <i class="fa fa-shopping-cart"></i>
				</a>
				<button type="button" class="btn position-relative">
					<i class="fa fa-search"></i>
				</button>
			</div>
			<button class="navbar-toggler border-0" type="button"
				data-bs-toggle="collapse" data-bs-target="#navMenu">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse order-lg-1" id="navMenu">
				<ul class="navbar-nav mx-auto text-center">
					<li class="nav-item"><a
						href="/PH17307_TrinhTienLuc_Asignment_SOF3011/home"
						class="nav-link text-uppercase text-dark px-4 py-4">Trang chủ</a>
					</li>
					<li class="nav-item"><a
						href="/PH17307_TrinhTienLuc_Asignment_SOF3011/quanAo?tag=0"
						class="nav-link text-uppercase text-dark px-4 py-4">Quần áo</a></li>
					<li class="nav-item"><a
						href="/PH17307_TrinhTienLuc_Asignment_SOF3011/giayDep?tag=0"
						class="nav-link text-uppercase text-dark px-4 py-4">Giày dép</a></li>
					<li class="nav-item"><a
						href="/PH17307_TrinhTienLuc_Asignment_SOF3011/phuKien?tag=0"
						class="nav-link text-uppercase text-dark px-4 py-4 border-0">Phụ
							kiện</a></li>
					<li class="nav-item">
						<div class="dropdown">
							<a
								class="nav-link dropdown-toggle text-uppercase text-dark px-4 py-4"
								role="button" id="dropdownMenuLink" data-bs-toggle="dropdown"
								aria-expanded="false"> Tài khoản </a>

							<ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
								<!-- Chưa đăng nhập -->
								<c:if test="${sessionScope.user==null }">
									<li><a class="dropdown-item"
										href="/PH17307_TrinhTienLuc_Asignment_SOF3011/registryIndex">Đăng
											ký</a></li>
									<li><a class="dropdown-item"
										href="/PH17307_TrinhTienLuc_Asignment_SOF3011/loginIndex">Đăng
											nhập</a></li>
									<li><a class="dropdown-item"
										href="/PH17307_TrinhTienLuc_Asignment_SOF3011/forgetPasswordIndex">Quên
											mật khẩu</a></li>
								</c:if>
								<!-- Đã đăng nhập user -->
								<c:if test="${sessionScope.user!=null }">
									<li><a class="dropdown-item"
										href="/PH17307_TrinhTienLuc_Asignment_SOF3011/logout">Đăng
											xuất</a></li>
									<li><a class="dropdown-item" href="#">Đổi mật khẩu</a></li>
									<li><a class="dropdown-item" href="#">Thay đổi thông
											tin</a></li>
								</c:if>
								<c:if test="${sessionScope.role== 0}">
									<li><a class="dropdown-item" href="/PH17307_TrinhTienLuc_Asignment_SOF3011/user/history">Lịch sử</a></li>
								</c:if>
								<!-- Đã đăng nhập admin -->
								<c:if test="${sessionScope.role== 1}">
									<li><a class="dropdown-item"
										href="/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/products/index">Quản
											lý sản phẩm</a></li>
									<li><a class="dropdown-item"
										href="/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/users/index">Quản
											lý user</a></li>
									<li><a class="dropdown-item"
										href="/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/bills/index">Quản
											lý hoá đơn</a></li>
								</c:if>
							</ul>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- end nav-bar -->
	<!-- header -->
	<header id="header">
		<div class="container"></div>
	</header>
	<!-- end header -->
	<!-- article -->
	<div class="container">
		<article>
			<jsp:include page="${view }"></jsp:include>
		</article>
	</div>
	<!-- end article -->
	<!-- footer -->
	<footer class="bg-dark py-5">
		<div class="container">
			<div class="row text-white g-4">
				<div class="col-md-6 col-lg-3">
					<a href="/PH17307_TrinhTienLuc_Asignment_SOF3011/home"
						class="text-uppercase text-decoration-none brand text-white">Ph17307</a>
					<p class="text-white text-muted mt-3">PH17307 Shopping là một
						website mua sắm thời trang online lớn nhất hiện tại. Website được
						phát triển bởi Trịnh Tiến Lực. Hãy đến và trải nghiệm với thiên
						đường mua sắm PH17307 Shopping</p>
				</div>
				<div class="col-md-6 col-lg-3">
					<h5 class=" fw-light">Liên kết</h5>
					<ul class="list-unstyled">
						<li class="my-3"><a
							href="/PH17307_TrinhTienLuc_Asignment_SOF3011/home"
							class="text-white text-decoration-none text-muted mt-3"> <i
								class="fas fa-chevron-circle-right me-1"></i> Trang chủ
						</a></li>
						<li class="my-3"><a
							href="/PH17307_TrinhTienLuc_Asignment_SOF3011/quanAo"
							class="text-white text-decoration-none text-muted mt-3"> <i
								class="fas fa-chevron-circle-right me-1"></i> Quần áo
						</a></li>
						<li class="my-3"><a
							href="/PH17307_TrinhTienLuc_Asignment_SOF3011/giayDep"
							class="text-white text-decoration-none text-muted mt-3"> <i
								class="fas fa-chevron-circle-right me-1"></i> Giày dép
						</a></li>
						<li class="my-3"><a
							href="/PH17307_TrinhTienLuc_Asignment_SOF3011/phuKien"
							class="text-white text-decoration-none text-muted mt-3"> <i
								class="fas fa-chevron-circle-right me-1"></i> Phụ kiện
						</a></li>
					</ul>
				</div>
				<div class="col-md-6 col-lg-3">
					<h5 class=" fw-light">Liên hệ</h5>
					<div
						class="d-flex justify-content-start
                            align-items-start my-2 text-muted">
						<span class="me-3"> <i class="fas fa-map-marked-alt"></i>
						</span> <span class="fw-light"> PT16308 Cao đẳng FPT Polytechnic </span>
					</div>
					<div
						class="d-flex justify-content-start
                            align-items-start my-2 text-muted">
						<span class="me-3"> <i class="fas fa-envelope"></i>
						</span> <span class="fw-light"> lucttph17307@fpt.edu.vn <span>
					</div>
					<div
						class="d-flex justify-content-start
                            align-items-start my-2 text-muted">
						<span class="me-3"> <i class="fas fa-phone-alt"></i>
						</span> <span class="fw-light"> 0397573143 </span>
					</div>
				</div>
				<div class="col-md-6 col-lg-3">
					<h5 class=" fw-light">Theo dõi</h5>
					<ul class="list-unstyled d-flex">
						<li><a href="https://www.facebook.com/Trinhtienluc234/"
							target="_blank"
							class="text-white
                            text-decoration-none text-muted fs-4 me-4">
								<i class="fab fa-facebook-f"></i>
						</a></li>
						<li><a href="https://twitter.com/TrnhTinLc3" target="_blank"
							class="text-white
                            text-decoration-none text-muted fs-4 me-4">
								<i class="fab fa-twitter"></i>
						</a></li>
						<li><a href="https://www.instagram.com/trinhluc14/"
							target="_blank"
							class="text-white
                            text-decoration-none text-muted fs-4 me-4">
								<i class="fab fa-instagram"></i>
						</a>
						<li><a href="https://github.com/luctrinh02" target="_blank"
							class="text-white
                            text-decoration-none text-muted fs-4 me-4">
								<i class="fab fa-github"></i>
						</a></li>
					</ul>
				</div>
			</div>
		</div>
	</footer>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"
		integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"
		integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13"
		crossorigin="anonymous"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script type="text/javascript">
		function quanAo() {
			var amount = document.getElementsByClassName("quanAo").length;
			$.ajax({
				url : "/PH17307_TrinhTienLuc_Asignment_SOF3011/loadmoreQuanAo",
				type : "get",
				data : {
					exits : Math.ceil(amount / 4)
				},
				success : function(data) {
					var row = document.getElementById("contentQuanAo");
					row.innerHTML += data;
				},
				error : function(xhr) {
					//Do Something to handle error
				}
			});
		}
		function phuKien() {
			var amount = document.getElementsByClassName("phuKien").length;
			$.ajax({
				url : "/PH17307_TrinhTienLuc_Asignment_SOF3011/loadmorePhuKien",
				type : "get",
				data : {
					exits : Math.ceil(amount / 4)
				},
				success : function(data) {
					var row = document.getElementById("contentPhuKien");
					row.innerHTML += data;
				},
				error : function(xhr) {
					//Do Something to handle error
				}
			});
		}
		function giayDep() {
			var amount = document.getElementsByClassName("giayDep").length;
			$.ajax({
				url : "/PH17307_TrinhTienLuc_Asignment_SOF3011/loadmoreGiayDep",
				type : "get",
				data : {
					exits : Math.ceil(amount / 4)
				},
				success : function(data) {
					var row = document.getElementById("contentGiayDep");
					row.innerHTML += data;
				},
				error : function(xhr) {
					//Do Something to handle error
				}
			});
		}
		function  cong() {
			var amount = document.getElementById("amount");
			amount.value=Number(amount.value)+1;
		}
		function  tru() {
			var amount = document.getElementById("amount");
			if(amount.value>1){
				amount.value=Number(amount.value)-1;
			}
		}
	</script>

</body>
</html>