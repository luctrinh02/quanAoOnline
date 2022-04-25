<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%><!-- nhúng thưu viện -->
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<div class="d-flex flex-wrap justify-content-center mt-5 mb-5">
	<a href="/PH17307_TrinhTienLuc_Asignment_SOF3011/giayDep?tag=0" ${tag==0?'hidden':'' } class="btn btn-primary m-2" >Tất cả</a>
	<a href="/PH17307_TrinhTienLuc_Asignment_SOF3011/giayDep?tag=1" ${tag==1?'hidden':'' } class="btn btn-primary m-2" >Sale</a>
	<a href="/PH17307_TrinhTienLuc_Asignment_SOF3011/giayDep?tag=2" ${tag==2?'hidden':'' } class="btn btn-primary m-2" ">New</a>
	<a href="/PH17307_TrinhTienLuc_Asignment_SOF3011/giayDep?tag=3" ${tag==3?'hidden':'' } class="btn btn-primary m-2" >Hot</a>
</div>
<div class="row gx-0 gy-3" id="contentQuanAo">
	<c:forEach items="${list }" var="obj">
		<div class="quanAo col-md-6 col-lg-4 col-xl-3 mt-4 p-2">
			<div class="">
				<div class="img position-relative">
					<a
						href="/PH17307_TrinhTienLuc_Asignment_SOF3011/product?id=${obj.id }"><img
						src="/PH17307_TrinhTienLuc_Asignment_SOF3011/imgUpload/${obj.image }"
						class="w-100 productImg"></a> <span
						class="position-absolute text-white d-flex align-items-center justify-content-center">
						<c:choose>
							<c:when test="${obj.tag ==1}">Sale</c:when>
							<c:when test="${obj.tag ==2}">New</c:when>
							<c:when test="${obj.tag ==3}">Hot</c:when>
						</c:choose>
					</span>
				</div>
				<div class="text-center">
					<div class="rating mt-3">
						<span class="text-warning"> <i class="fas fa-star"></i>
						</span> <span class="text-warning"> <i class="fas fa-star"></i>
						</span> <span class="text-warning"> <i class="fas fa-star"></i>
						</span> <span class="text-warning"> <i class="fas fa-star"></i>
						</span> <span class="text-warning"> <i class="fas fa-star"></i>
						</span>
					</div>
					<p class="text-capitalize my-1">${obj.name }</p>
					<p class="fw-bold">
						<fmt:formatNumber pattern="###,###,###">${obj.price*(100-obj.discount)/100 }</fmt:formatNumber>
						₫
					</p>
				</div>
			</div>
		</div>
	</c:forEach>
</div>