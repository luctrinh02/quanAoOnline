<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:if test="${!empty sessionScope.error }">
	<div class="alert alert-danger">${sessionScope.error }</div>
	<c:remove var="error" scope="session"></c:remove>
</c:if>
<c:if test="${!empty sessionScope.message }">
	<div class="alert alert-success">${sessionScope.message }</div>
	<c:remove var="message" scope="session"></c:remove>
</c:if>
<div class="container mt-2 mb-3">
	<form action="/PH17307_TrinhTienLuc_Asignment_SOF3011/admin/products/store"
		method="post" enctype="multipart/form-data">
		<div>
			<label>Tên sản phẩm</label> <input name="name" type="text"
				class="form-control" autocomplete="off">
		</div>
		<div class="row mt-2">
			<div class="col-6">
				<label>Category</label> <select class="form-select"
					name="categoryId">
					<c:forEach items="${listCategory }" var="obj">
						<option value="${obj.id}">${obj.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-6">
				<label>Tag</label> <select class="form-select" name="tag">
					<option value=1>Sale</option>
					<option value=2>New</option>
					<option value=3>Hot</option>
				</select>
			</div>
		</div>
		<div class="row mt-2">
			<div class="col-3">
				<label>Số lượng</label> <input name="amount" type="number"
					class="form-control" autocomplete="off" value=1>
			</div>
			<div class="col-3">
				<label>Đơn giá</label> <input name="price" type="number"
					class="form-control" value=0>
			</div>
			<div class="col-3">
				<label>Giảm giá</label> <input name="discount" type="number"
					class="form-control" value=0 min=0 max=100>
			</div>
			<div class="col-3">
			<label>Màu sắc</label> <input name="color" type="text"
				class="form-control" autocomplete="off">
		</div>
		</div>
		<div class="mt-2">
			<label>Kích thước</label>
			<div class="form-check-inline">
				<input type="radio" name="size" value=1 class="form-check-input"
					checked> <label class="form-checl-label">XS</label>
			</div>
			<div class="form-check-inline">
				<input type="radio" name="size" value=2 class="form-check-input">
				<label class="form-checl-label">S</label>
			</div>
			<div class="form-check-inline">
				<input type="radio" name="size" value=3 class="form-check-input">
				<label class="form-checl-label">M</label>
			</div>
			<div class="form-check-inline">
				<input type="radio" name="size" value=4 class="form-check-input">
				<label class="form-checl-label">L</label>
			</div>
			<div class="form-check-inline">
				<input type="radio" name="size" value=5 class="form-check-input">
				<label class="form-checl-label">XL</label>
			</div>
			<div class="form-check-inline">
				<input type="radio" name="size" value=6 class="form-check-input">
				<label class="form-checl-label">XXL</label>
			</div>
			<div class="form-check-inline">
				<input type="radio" name="size" value=7 class="form-check-input">
				<label class="form-checl-label">XXXL</label>
			</div>
		</div>
		<div class="mt-2">
			<label>Hình ảnh</label> <input name="img" type="file"
				class="form-control" accept=".jpg,.png">
		</div>
		<div class="mt-2">
			<label>Mô tả</label> <textarea rows="3" name="description" placeholder="Ghi mô tả vào đây" class="form-control"></textarea>
		</div>
		<div class="mt-2">
			<button class="btn btn-primary">Thêm mới</button>
			<button type="reset" class="btn btn-secondary">Xoá form</button>
		</div>
	</form>
</div>