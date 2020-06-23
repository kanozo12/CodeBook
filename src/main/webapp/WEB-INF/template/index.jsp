<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="/WEB-INF/template/fragments/layout_head.jsp"></jsp:include>

<body>
	<jsp:include page="/WEB-INF/template/fragments/layout_header.jsp"></jsp:include>

	<div class="container">
		<div class="jumbotron">
			<h1 class="display-4">양영디지털고 CodeBook!</h1>
			<p class="lead">코드북은 우리가 일상생활에서 코드를 작성하면서 부딛혔던 문제들과 해결책을 공유하기 위해 올리는 공간입니다.</p>
			<hr class="my-4">
			<p>내가 고생하며 해결했던 일은 언젠가 다른 프로그래머에게도 고통으로 다가옵니다. 이를 도와줄 수 있는 사이트! Code Book입니다.</p>
			<a class="btn btn-primary btn-lg" href="/board/list" role="button">보러가기</a>
		</div>
	</div>
<jsp:include page="/WEB-INF/template/fragments/layout_footer.jsp"></jsp:include>
</body>
</html>
