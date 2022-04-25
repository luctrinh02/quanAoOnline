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
	<p class="alert alert-warning">Bạn chưa có đơn hàng nào</p>
</c:if>
<a href="/PH17307_TrinhTienLuc_Asignment_SOF3011/back"
	class="btn btn-primary"> <span class="fas fa-backward"></span> Trở
	lại
</a>
<c:if test="${!empty list }">
	<div class="card mt-2 mb-3">
		<h5 class="card-header">
			Ngày mua:
			<fmt:formatDate pattern="dd/MM/yyyy" value="${date }" />
		</h5>
		<div class="card-body">
			<table class="table table-hover">
				<thead>
					<th>Tên sản phẩm</th>
					<th>Màu sắc</th>
					<th>Kích thước</th>
					<th>Số lượng</th>
					<th>Đơn giá</th>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="obj">
						<tr class="align-middle">
							<td>${obj.product.name }</td>
							<td>${obj.product.color }</td>
							<td><c:choose>
									<c:when test="${obj.product.size==1}">XS</c:when>
									<c:when test="${obj.product.size==2}">S</c:when>
									<c:when test="${obj.product.size==3}">M</c:when>
									<c:when test="${obj.product.size==4}">L</c:when>
									<c:when test="${obj.product.size==5}">XL</c:when>
									<c:when test="${obj.product.size==6}">XXL</c:when>
									<c:when test="${obj.product.size==7}">XXXL</c:when>
								</c:choose></td>
							<td>${obj.amount }</td>
							<td><fmt:formatNumber pattern="###,###,###">${obj.price }</fmt:formatNumber>
								VNĐ</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="5">Trạng thái : <c:choose>
								<c:when test="${trangThai==0 }">
									<span class="text-success">Đang xử lý</span>
								</c:when>
								<c:when test="${trangThai==1 }">
									<span class="text-primary">Đã chấp nhận</span>
								</c:when>
								<c:when test="${trangThai==2 }">
									<span class="text-danger">Đã bị huỷ</span>
								</c:when>
							</c:choose></td>
					</tr>
				</tfoot>
			</table>
		</div>
		<div class="card-footer">
			Tổng tiền:
			<fmt:formatNumber pattern="###,###,###">${tongTien }</fmt:formatNumber>
			VNĐ
		</div>
	</div>
</c:if>