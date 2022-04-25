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
<form>
	<div class="card-header">
		<h5 class="card-title" id="QuenMatKhauLabel">
			<span class="fas fa-question"></span> Quên mật khẩu
		</h5>
	</div>
	<div class="card-body">
		<div align="center">
			<small class="mt-3">Nhập email. Chúng tôi sẽ gửi bạn mật khẩu</small>
		</div>
		<div class="mt-3">
			<label>Email <span class="text-danger">*</span></label> <input
				type="email" class="form-control" name="emailQMK" required>
		</div>
	</div>
	<div class="card-footer">
		<button class="btn btn-secondary" type="reset">Huỷ bỏ</button>
		<button type="submit" class="btn btn-primary">Xác nhận</button>
	</div>
</form>