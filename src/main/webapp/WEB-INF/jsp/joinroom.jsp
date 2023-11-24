<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Swack - ルーム参加画面</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link href="css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<link href="css/bootstrap-toggle.min.css" rel="stylesheet">
<link href="css/bootstrap-select.min.css" rel="stylesheet">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/joinmember.css">

</head>

<body>
	<div class="container form-container">
		<div class="row">
			<div class="col-md-12 member-form">
				<h3>ルームを選ぶ</h3>
				<form action="JoinRoomServlet" method="post">
					<div class="form-group">
						<label class="control-label">参加可能なルーム:</label> <select id="rooms"
							class="form-control selectpicker" name=roomid
							data-live-search="true" data-selected-text-format="count > 1"
							>

							<!-- <c:forEach items="${}"> -->
						<!-- <option value="${room.roomId}">${room.roomName}</option> -->	
							<!-- </c:forEach> -->

							<c:forEach var="room" items="${roomNameList}">
								<option value="${room.roomId}">${room.roomName}</option>
							</c:forEach>


						</select> <span class="users-note">参加したいルームを選んでください。</span>
					</div>
					<div class="member-form-btn">
						<button class="btn btn-default" onclick="history.back()" type="button">キャンセル</button>
						<button id="send" class="btn btn-default" >参加する</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- container -->

	<script src="js/jquery-3.2.0.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/bootstrap-toggle.min.js"></script>
	<script type="text/javascript" src="js/bootstrap-select.min.js"></script>

	<script src="js/joinroom.js"></script>
</body>
</html>
