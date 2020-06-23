<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="net.kanozo.domain.Criteria" %>
<!DOCTYPE html>
<jsp:include page="/WEB-INF/template/fragments/layout_head.jsp"></jsp:include>


<body>
	<jsp:include page="/WEB-INF/template/fragments/layout_header.jsp"></jsp:include>
	<div class="container">
		<div class="row">
			<div class="col-10 offset-1">
				<h2>코드북 게시판</h2>
				<div class="row">
					<div class="col-12 text-right">
						<form class="form form-inline justify-content-end" id="searchForm">
							<input type="text" class="form-control mb-2 mr-sm-2" id="keyword"
								placeholder="검색어를 입력하세요" />
							<button type="button" id="btnSearch" class="btn btn-primary mb-2">검색</button>
						</form>
					</div>
				</div>
				<table class="table table-striped">
					<tr>
						<th>글번호</th>
						<th width="50%">글제목</th>
						<th>작성자</th>
						<th>작성일</th>
					</tr>

					<c:forEach var="board" items="${list}">
						<tr>
							<td>${board.id}</td>
							<c:set var="criteria" value="${Criteria.getQuery(criteria.page)}" />
							<td><a href="/board/view/"<%-- ${board.id}${criteria} --%>>${board.title}</a></td>
							<td>${board.writer}</td>
							<td>${board.writeDate}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<%-- <div class="row mt-3">
			<div class="col-10 offset-1 text-right">
				<a href="/board/write" class="btn btn-primary">글작성</a>
			</div> 
		</div>
		
		<nav>
			<ul class="pagination justify-content-center">
				<li class="page-item" th:if="${criteria.prev}">
					<a th:href="@{ '/board/list' + ${criteria.getQuery(criteria.start - 1)} }" class="page-link" aria-label="Previous">
						<span aria-hidden="true">&laquo;</span>
					</a>
				</li>
				
				<th:block th:each="i : ${ #numbers.sequence( criteria.start, criteria.end )}">
					<li class="page-item">
						<a th:href="@{ '/board/list' + ${criteria.getQuery(i)} }" th:text="${i}" class="page-link">인덱스 번호</a>
					</li>
				</th:block>
				
				<li class="page-item" th:if="${criteria.next}">
					<a th:href="@{'/board/list' + ${criteria.getQuery(criteria.end + 1)} }" class="page-link" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</ul>
		</nav> --%>

	</div>
	<jsp:include page="/WEB-INF/template/fragments/layout_footer.jsp"></jsp:include>
	<script>
		$(function() {
			$("#btnSearch").on("click", function(e) {
				let text = $("#keyword").val();
				location.href = '/board/list?keyword=' + text;
			});
			$("#searchForm").on("submit", function(e) {
				$("#btnSearch").click();
				return false;
			});
		});
	</script>
</body>
</html>
