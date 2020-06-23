<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/template/fragments/layout_head.jsp"></jsp:include>
<body>
	<jsp:include page="/WEB-INF/template/fragments/layout_header.jsp"></jsp:include>
	<link rel="stylesheet" href="/app/css/prism.css" />

	<script src="/app/js/prism.js"></script>
	<div class="container">
		<div class="row">
			<div class="col-10 offset-1">
				<div class="row d-flex mb-2">
					<!--   이미지 박스 -->
					<div class="ml-2" style="width: 128px; height: 128px;">
						<img src="/user/profile/${board.img}}" alt="img"
							style="width: 128px; height: 128px;" />
					</div>
					<div class="col">
						<h4 class="card-title">${board.title}</h4>
						<p class="card-text">
							<span class="badge badge-primary">${board.name}(${board.writer})</span>
							<span class="badge badge-secondary">LV.[${board.u_level}]</span> 
							<span class="badge badge-warning">작성일: ${board.writeDate}</span>
						</p>
					</div>
				</div>
				<div class="card">
					<div class="card-body">${board.content}</div>
				</div>
			</div>
		</div>
<%-- 		<div class="row mt-3 mb-5">
			<div class="col-10 offset-1 text-right">
				<th:block
					th:if="${session.user != null and session.user.userid == board.writer}">
					<a th:href="@{'/board/write/' + ${board.id} }"
						class="btn btn-success">수정</a>
					<a th:href="@{'/board/delete/' + ${board.id} }"
						class="btn btn-danger">삭제</a>
				</th:block>
				<a
					th:href="@{ '/board/list' + ${criteria.getQuery(criteria.page)} }"
					class="btn btn-primary">목록보기</a>
			</div>
		</div>

		<div class="row mt-3" th:each="list : ${list}">
			<div class="col-10 offset-1">
				<div class="row d-flex mb-2">
					<!--   이미지 박스 -->
					<div class="ml-2" style="width: 64px; height: 64px;">
						<img th:src="@{ '/user/profile/' + ${board.img}}" alt=""
							style="width: 64px; height: 64px;" />
					</div>
					<div class="col">
						<h5 class="card-title" th:text="${list.comContent}"></h5>
						<p class="card-text">
							<span class="badge badge-primary"
								th:text="@{${board.name} + '(' + ${board.writer} + ')'}"></span>
							<span class="badge badge-secondary"
								th:text="@{ 'LV.[' + ${board.level} +']'}"></span> <span
								class="badge badge-warning"
								th:text="@{ '작성일: ' + ${#dates.format(board.writeDate, 'yyyy-MM-dd') }}"></span>
						</p>
					</div>
				</div>
			</div>
		</div>
		<th:block th:if="${session.user != null}">
			<div class="row mt-3">
				<div class="col-10 offset-1">
					<div class="row d-flex mb-2">
						<!--   이미지 박스 -->
						<div class="ml-2" style="width: 64px; height: 64px;">
							<img th:src="@{ '/user/profile/' + ${board.img}}" alt=""
								style="width: 64px; height: 64px;" />
						</div>
						<div class="ml-3 form-group">
							<form method="post" th:action="@{/board/replyInsert}"
								name="commentInsertForm">

								<input type="hidden" name="bno" th:value="${board.id}" /> <input
									type="hidden" name="comWriter" th:value="${user.userid}" /> <input
									type="hidden" name="userName" th:value="${user.name}" />
								<textarea class="form-control" rows="3" cols="10"
									name="comContent" id="comContent"></textarea>
								<button class="mt-3 btn btn-primary" type="submit"
									name="commentInsertBtn">등록</button>

							</form>
						</div>
					</div>
				</div>

			</div>
		</th:block> --%>
	</div>

	<script src="/app/js/comment.js"></script>
	<footer th:replace="/fragments/layout :: footer"></footer>
</body>
</html>
