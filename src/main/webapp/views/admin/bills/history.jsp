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
	<p class="alert alert-warning">Người này chưa mua đơn hàng nào</p>
</c:if>
<a href="/PH17307_TrinhTienLuc_Asignment_SOF3011/backToUser"
		class="btn btn-primary"> <span class="fas fa-backward"></span> Trở
		lại
	</a>
<c:if test="${!empty list }">
	<div class="card mt-2 mb-3">
		<h5 class="card-header">${fullname }</h5>
		<div class="card-body">
			<table class="table table-hover">
				<thead>
					<th>Ngày mua</th>
					<th>Tổng tiền</th>
					<th>Trạng thái</th>
					<th colspan="3"></th>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="obj">
						<tr class="align-middle">
							<td><fmt:formatDate value="${obj.date }"
									pattern="dd/MM/yyyy" /></td>
							<td><fmt:formatNumber pattern="###,###,###">${obj.totalMoney }</fmt:formatNumber>
								VNĐ</td>
							<td><c:choose>
									<c:when test="${obj.status==0 }">
										<span class="text-success">Đang xử lý</span>
									</c:when>
									<c:when test="${obj.status==1 }">
										<span class="text-primary">Đã chấp nhận</span>
									</c:when>
									<c:when test="${obj.status==2 }">
										<span class="text-danger">Đã bị huỷ</span>
									</c:when>
								</c:choose></td>
							<td><a
								href="/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/bills/detail?id=${obj.id }"
								class="btn btn-primary">Chi tiết</a></td>
							<td><c:if test="${obj.status==0 }">
									<button type="button" class="btn btn-danger"
										data-bs-toggle="modal" data-bs-target="#checkXoa${obj.id }">Huỷ
										đơn</button>
								</c:if> 
								<!-- Modal -->
								<div class="modal fade" id="checkXoa${obj.id }" tabindex="-1"
									aria-labelledby="exampleModalLabel" aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<h5 class="modal-title" id="exampleModalLabel">Warning</h5>
												<button type="button" class="btn-close"
													data-bs-dismiss="modal" aria-label="Close"></button>
											</div>
											<div class="modal-body">Bạn chắc chắn muốn huỷ đơn này?</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-secondary"
													data-bs-dismiss="modal">Huỷ</button>
												<a
													href="/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/bills/cancel?idBill=${obj.id }"
													class="btn btn-danger">Đồng ý</a>
											</div>
										</div>
									</div>
								</div> <!-- end modal --></td>
								<td><c:if test="${obj.status==0 }">
									<button type="button" class="btn btn-success"
										data-bs-toggle="modal" data-bs-target="#checkHt${obj.id }">Chấp nhận</button>
								</c:if> 
								<!-- Modal -->
								<div class="modal fade" id="checkHt${obj.id }" tabindex="-1"
									aria-labelledby="exampleModalLabel" aria-hidden="true">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header">
												<h5 class="modal-title" id="exampleModalLabel">Warning</h5>
												<button type="button" class="btn-close"
													data-bs-dismiss="modal" aria-label="Close"></button>
											</div>
											<div class="modal-body">Bạn chắc chắn muốn thực hiện đơn này?</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-secondary"
													data-bs-dismiss="modal">Huỷ</button>
												<a
													href="/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/bills/success?idBill=${obj.id }"
													class="btn btn-primary">Đồng ý</a>
											</div>
										</div>
									</div>
								</div> <!-- end modal --></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</c:if>