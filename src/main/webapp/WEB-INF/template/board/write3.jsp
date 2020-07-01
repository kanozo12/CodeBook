<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<jsp:include page="/WEB-INF/template/fragments/layout_head.jsp"></jsp:include>

<body>
	<jsp:include page="/WEB-INF/template/fragments/layout_header.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<div class="col-10 offset-1">
				<c:if test="${empty boardVO.id}">
					<h2>글 작성</h2>
				</c:if>

				<c:if test="${not empty boardVO.id}">
					<h2>글 수정</h2>
				</c:if>

				<form method="post" action="/board/write3"
					enctype="multipart/form-data">
					<div class="form-group">
						<label for="title">글 제목</label> <input type="text" 	class="form-control" name="title" id="title" placeholder="글 제목을 입력하세요">
						<div class="invalid-feedback">글 제목에 오류가 있을 때 보여줄 메시지</div>
					</div>

					<div class="form-group">
						<textarea class="form-control" id="content" name="content" placeholder="글 내용을 입력하세요"></textarea>
						<div class="invalid-feedback">글 내용 관련 오류가 있을 때 보여줄 메시지</div>
					</div>

					<button type="submit" class="btn btn-primary">글작성</button>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/template/fragments/layout_footer.jsp"></jsp:include>
	<script src="/webjars/tinymce/4.8.5/tinymce.min.js"></script>
	<script src="/app/js/app.js"></script>
	<jsp:include page="/WEB-INF/template/fragments/layout_footer.jsp"></jsp:include>
</body>
</html>
