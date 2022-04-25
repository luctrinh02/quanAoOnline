<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%><!-- nhúng thưu viện -->
<c:if test="${!empty sessionScope.error }">
	<div class="alert alert-danger">${sessionScope.error }</div>
	<c:remove var="error" scope="session"></c:remove>
</c:if>
<c:if test="${!empty sessionScope.message }">
	<div class="alert alert-success">${sessionScope.message }</div>
	<c:remove var="message" scope="session"></c:remove>
</c:if>
<form class="mb-3" method="post"
	action="/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/users/update?id=${user.id }">
	<div class="card-body">
		<div class="row mt-2">
			<label>Họ tên <span class="text-danger">*</span></label>
			<div>
				<input name="fullname" type="text" class="form-control" value="${user.fullname }" required>
			</div>
		</div>
		<div class="row mt-2">
			<label>Địa chỉ <span class="text-danger">*</span></label>
			<div>
				<input type="text" class="form-control" name="address" value="${user.address }">
			</div>
		</div>
		<div class="mt-2">
			<label for="gtDangKy">Giới tính</label>
			<div class="form-check form-check-inline">
				<input class="form-check-input" type="radio" name="gender"
					id="namDangKy" value="0" ${user.gender==0?'checked':'' }> <label
					class="form-check-label" for="nam">Nam</label>
			</div>
			<div class="form-check form-check-inline">
				<input class="form-check-input" type="radio" name="gender" value="1" ${user.gender==1?'checked':'' }>
				<label class="form-check-label" for="nu">Nu</label>
			</div>
		</div>
		<div class="row mt-2">
			<label>Email <span class="text-danger">*</span></label>
			<div>
				<input type="email" class="form-control" name="email" required value="${user.email }">
			</div>
		</div>
		<div class="mt-2">
			<label for="gtDangKy">role</label>
			<div class="form-check form-check-inline">
				<input class="form-check-input" type="radio" name="role"
					id="namDangKy" value="0" ${user.role==0?'checked':'' }> <label
					class="form-check-label" for="user">User</label>
			</div>
			<div class="form-check form-check-inline">
				<input class="form-check-input" type="radio" name="role" value="1" ${user.role==1?'checked':'' }>
				<label class="form-check-label" for="admin">Admin</label>
			</div>
		</div>
	</div>
	<div class="mt-2">
		<button type="reset" class="btn btn-secondary"
			>Reset</button>
		<button type="submit" class="btn btn-primary">Sửa</button>
	</div>
</form>