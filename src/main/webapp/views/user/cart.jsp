<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%><!-- nhúng thưu viện -->
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<c:if test="${!empty sessionScope.error }">
	<div class="alert alert-danger">${sessionScope.error }</div>
	<c:remove var="error" scope="session"></c:remove>
</c:if>
<c:if test="${!empty sessionScope.message }">
	<div class="alert alert-success">${sessionScope.message }</div>
	<c:remove var="message" scope="session"></c:remove>
</c:if>
<c:if test="${empty list }">
	<!-- điều kiện -->
	<p class="alert alert-warning">Giỏ hàng trống</p>
</c:if>
<c:if test="${!empty list }">
	<form action="/PH17307_TrinhTienLuc_Asignment_SOF3011/user/datHang"
		method="post">
		<div class="card">
			<h5 class="card-header">${sessionScope.user.fullname }</h5>
			<div class="card-body">
				<table class="table table-hover">
					<thead>
						<th colspan="2">Tên sản phẩm</th>
						<th>Hình ảnh</th>
						<th>Đơn giá</th>
						<th>Số lượng</th>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="obj" varStatus="loop">
							<tr class="align-middle">
								<td><input type="checkbox" value="${obj.product.id }" name="product"></td>
								<td>${obj.product.name }</td>
								<td><img class="imgIcon"
									src="/PH17307_TrinhTienLuc_Asignment_SOF3011/imgUpload/${obj.product.image }">
								</td>
								<td><fmt:formatNumber pattern="###,###,###">${obj.price} </fmt:formatNumber>
									VNĐ</td>
								<td><input type="number" name="amount${obj.product.id }" class="cartAmount"
									min="1" max="${obj.product.amount }" value=${obj.amount }>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="card-footer">
				<button class="btn btn-primary">Đặt hàng</button>
			</div>
		</div>
	</form>
</c:if>