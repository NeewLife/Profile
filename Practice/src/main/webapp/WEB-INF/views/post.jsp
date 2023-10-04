<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 페이지</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<script type="text/javascript">
	var userRank = '${user.userRank}';
</script>
<script src="${path}/resources/js/post.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<link href="${path}/resources/css/post.css" rel="stylesheet">
	
</head>
<body>
<div class="container">
	<div class="screen">
		<header>
			<div class="greet" style="width: 820px;">
				${user.userName}(${user.userRankKR})
				<c:if test="${proxy != null }"> - ${proxy.proxyName}(${proxy.proxyRankKR})</c:if>님 환영합니다.
				<button type="button" class="logoutBtn" onclick="location.href='/'">로그아웃</button>
				<c:if test="${proxy != null }">
					<div>(대리결재일 : ${proxy.stDate } ~ ${proxy.endDate })</div>
				</c:if>
			</div>
				<button class="writeBtn" onclick="location.href = '/write';">글쓰기</button>
				<c:if test="${user.userRank eq 'DH' or user.userRank eq 'EXA'}">
					<button type="button" class="proxyBtn" data-bs-toggle="modal" data-bs-target="#proxyModal">
						대리결재
					</button>
					<!-- Button trigger modal -->
					
					<!-- Modal -->
					<div class="modal fade" id="proxyModal" tabindex="-1" aria-labelledby="proxyModalLabel" aria-hidden="true">
					    <div class="modal-dialog modal-dialog-centered">
					    	<div class="modal-content">
					        	<div class="modal-header">
						        	<h1 class="modal-title fs-5" id="proxyModalLabel">대리결재 권한부여</h1>
						        	<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					      		</div>
					      		<form id="giveProxyForm" action="/giveProxy" method="post">
							      	<div class="modal-body">
								        <div class="input-group mb-2">
										    <label class="input-group-text" for="recipName">대리결재자</label>
										    <select class="form-select" id="recipName">
										    	
										    </select>
										</div>
									    <div class="input-group mb-2">
										    <span class="input-group-text" id="basic-addon3">직급 : </span>
										    <input type="text" class="form-control" id="recipRankKR" name="recipRankKR" aria-describedby="basic-addon3 basic-addon4" readonly>
										    <input type="hidden" id="recipId" name="recipId">
										    <input type="hidden" id="recipRank" name="recipRank">
									    </div>
									    <div class="input-group mb-2">
										    <span class="input-group-text" id="basic-addon4">대리자 : </span>
										    <input type="text" class="form-control" id="superior" name="superior" aria-describedby="basic-addon3 basic-addon4" value="${user.userName}" readonly>
									    </div>
							        </div>
							        <div class="modal-footer">
							      	  	<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
							        	<button type="button" class="btn btn-primary" onclick="giveProxy()">권한 부여</button>
							      	</div>
						      	</form>
				    		</div>
					  	</div>
					</div>
				</c:if>
		</header>
		
		<form action="/post" method="get" id="search">
			<select id="searchType" name="searchType">
				<option value="">선택</option>
				<option value="writer">작성자</option>
				<option value="authPeople">결재자</option>
				<option value="titleContent">제목+내용</option>
			</select>
			<input id="keyword" name="keyword" type="text" placeholder="검색어를 입력하세요">
			<select id="authType" name="authType">
				<option value="">결재상태</option>
				<option value="TEM">임시저장</option>
				<option value="WAIT">결재대기</option>
				<option value="ING">결재중</option>
				<option value="FIN">결재완료</option>
				<option value="REJ">반려</option>
			</select>
			<br>
			<input id="startDate" name="startDate" type="date">   ~   <input id="endDate" name="endDate" type="date">
			<button id="searchBtn" type="submit">검색</button>
		</form>
		
		
		<table class="table">
			<thead class="table-light">
				<tr>
					<th>번호</th>
					<th>작성자</th>
					<th>제목</th>
					<th>작성일</th>
					<th>결재일</th>
					<th>결재자</th>
					<th>결재상태</th>
				</tr>
			</thead>
			<tbody id="posts">
				<c:forEach items="${list}" var="list">
					<tr onclick="location.href='/view?postId=${list.postId}'" style="cursor:pointer">
						<td>${list.postId}</td>
						<td>${list.writer}</td>
						<td>${list.title}</td>
						<td>${list.writeDate}</td>
						<td>${list.confirmDate}</td>
						<td>${list.confirmPerson}<c:if test="${not empty list.proxyConfirmPerson}">(${list.proxyConfirmPerson})</c:if></td>

 						<!-- 결재상태 DB 값에 따라 변경해서 결재상태 출력 -->
						<td>
								<c:choose>
									<c:when test="${list.confirmStatus eq 'TEM' }" >임시저장</c:when>
									<c:when test="${list.confirmStatus eq 'WAIT' }" >결재대기</c:when>
									<c:when test="${list.confirmStatus eq 'ING' }" >결재중</c:when>
									<c:when test="${list.confirmStatus eq 'FIN' }" >결재완료</c:when>
									<c:when test="${list.confirmStatus eq 'REJ' }" >반려</c:when>
								</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${empty list}">
            <div class="listIsEmpty">
                검색된 게시글이 없습니다
            </div>
        </c:if>
	</div>
</div>
<%-- 	<div class="greet"> ${sessionScope.session.userId}님 환영합니다</div> --%>
</body>
</html>