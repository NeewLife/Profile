<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
	<link href="${path}/resources/css/write.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.7.0.js" ></script>
</head>
<body>
	<div class="container">
		<div class="screen">
	 
			<a href="/post">목록으로</a>
		
			<div class="chkBoxTable">
				<table class="table">
					<thead>
						<tr>
							<th>결재요청</th>
							<th>과장</th>
							<th>부장</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			
			<div class="formBox">
				<c:if test="${post == null }">
					<form action="/save" method="post">
				</c:if>
				<c:if test="${post != null }">
					<form action="/update" method="post">
				</c:if>
					<fieldset>
						<label for="seq">번호 : </label>
						<c:choose>
							<c:when test="${post != null }">
								<input type="text" id="postId" name="postId" value="${post.postId }" readonly="readonly">
								<br>
							</c:when>
							<c:otherwise>
								<input type="text" id="postId" name="postId" value="${lastSeq }" readonly="readonly">
								<br>
							</c:otherwise>
						</c:choose>
						<label for="writer">작성자 : </label>
						<input type="text" id="writer" name="writer" value="${sessionScope.session.userName }" readonly="readonly">
						<br>
						<label for="title">제목 :</label>
						<input type="text" id="title" name="title" value="${post.title }">
						<br>
						<label for="content">내용 : </label>
						<textarea placeholder="내용" id="content" name="content" >${post.content }</textarea>
						<input type="hidden" id="confirmStatus" name="confirmStatus">
					</fieldset>
				</form>
				<div id="submitButton">
					<button type="button" onclick="save('TEM')" class="btn btn-secondary">임시저장</button>
					<button type="button" onclick="save('WAIT')" class="btn btn-primary">결재요청</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
    function save(status){
    	document.getElementById('confirmStatus').value = status;
    	let form = document.querySelector("form");
    	form.submit();
    }
</script>
</html>