<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<jsp:include page="/WEB-INF/template/fragments/layout_head.jsp"></jsp:include>

<body>
	<jsp:include page="/WEB-INF/template/fragments/layout_header.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<div class="col-12">
				<div class="card">
					<div class="card-header">오류 메시지</div>
					<div class="card-body">
						<h5 class="card-title">${title}</h5>
						<p class="card-text">${errorMsg}</p>
						<a href="javascript:history.back()" class="btn btn-primary">이전</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<jsp:include page="/WEB-INF/template/fragments/layout_footer.jsp"></jsp:include>
</body>
</html>
