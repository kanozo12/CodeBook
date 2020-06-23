<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
<link rel="stylesheet"
	href="/webjars/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="/webjars/jquery/3.4.1/jquery.min.js"></script>
<script src="/webjars/popper.js/1.15.0/umd/popper.min.js"></script>
<script src="/webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<script>
	$(function() {
		if (document.body.clientHeight > window.innerHeight - 24) {
			$("footer").removeClass("fixed-bottom");
			$("footer").addClass("mt-3");
		}
	});
</script>
</head>