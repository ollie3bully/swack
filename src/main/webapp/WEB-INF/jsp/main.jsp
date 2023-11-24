
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Swack - メイン画面</title>
<link rel="shortcut icon" href="images/favicon.ico">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/main.css">
</head>
<body>
	<div class="container">

		<div id="overflow">
			<div class="conf">
				<h>${room.roomName}のメンバー</h>
				<p>
				<p>
					<c:forEach var="user" items="${userList}">
						<a>・${user.userName}</a>
						<br>
					</c:forEach>
				</p>
				<a href="JoinMemberServlet?roomId=${room.roomId}">メンバーを新たに追加する</a>
				<div class="btns">
					<input type="button" value="ルーム退出" onClick="tai(this);"
						id="${room.roomId}"> <input type="button" value="閉じる"
						class="ok" onClick="next();">
				</div>
			</div>
		</div>
		<header class="header">
			<div>${user.userName}</div>
			<button type="button" class="logout pointer"onclick="logout();">ログアウト</button>
			<form action="LoginServlet" id="logoutForm" method="get">
			
			</form>

			<form action="UserUpdateServlet" method="get">
				<input type="submit" value="ユーザ情報変更">
			</form>

			<c:if test="${user.userId == 'U0000'}">
				<div class="make-riddance-button">
					<a href="MakeRiddanceServlet?roomId=${room.roomId}"><button>強制退会</button></a>

				</div>
			</c:if>
		</header>
		<section class="main">
			<div class="left">
				<h2>Swack</h2>
				<hr>
				<details open>
					<summary>
						ルーム <a href="CreateRoomServlet"><button>＋</button></a><br>
						${errorMsg}
					</summary>
					<c:forEach var="room" items="${roomList}">
						<a class="list-name" href="MainServlet?roomId=${room.roomId}">#${room.roomName}</a>
						<br>
					</c:forEach>
					<a href="JoinRoomServlet"><button>＋</button></a>
					<a class="sanka"> ルームを追加する</a>
					<br> ${errorMsg}
				</details>
				<details open>
					<summary>
						ダイレクト<a href="CreateDirectRoomServlet"><button>＋</button></a><br>
						${errorMsg}
					</summary>
					<c:forEach var="direct" items="${directList}">
						<a class="list-name" href="MainServlet?roomId=${direct.roomId}">#
							${direct.roomName}</a>
						<br>
					</c:forEach>
				</details>
			</div>
			<!--left -->
			<div class="contents">
				<c:if test="${!room.directed}">
					<div class="join-member-button">
						<a href="JoinMemberServlet?roomId=${room.roomId}"><button>＋</button></a>
					</div>
				</c:if>

				<h2>${room.roomName}(${room.memberCount - 1})
					<!-- <c:if test="${!room.directed}">
						<input type="button" value="▽" onClick="check();">
					</c:if>-->
				</h2>

				<hr>
				<div id="logArea" class="contents-main">
					<c:forEach var="chatLog" items="${chatLogList}" varStatus="status">
						<c:if test="${status.last}">
							<input type="hidden" id="lastChatLogId"
								value="${chatLog.chatLogId}">
						</c:if>


						<div class="log-area" id="${chatLog.chatLogId}">
							<div class="log-icon">
								<img src="images/${chatLog.userId}.png"
									onerror="this.src='images/profile.png;'">
							</div>
							<div class="log-box" id="box">
								<p class="log-name">
									${chatLog.userName} <span class="log-time">[${chatLog.createdAt}]</span>
								</p>
								<p>${chatLog.message}</p>
								<p>
									<c:if
										test="${user.userId == 'U0000' or chatLog.userId == user.userId}">
										<img src="images/pencil.svg" class="edit pointer"
											id="edit${chatLog.chatLogId}" />
										<img src="images/trash.svg" class="delete pointer"
											id="delete${chatLog.chatLogId}" />
									</c:if>
								</p>
							</div>
						</div>
					</c:forEach>
				</div>
				<div class="contents-footer" >
					<form action="MainServlet" method="post">
						<input type="hidden" name="roomId" value="${room.roomId}">
						<div class="form-wrap">
							<input type="text" name="message" id="text">
							<button type="submit" id="sender">送信</button>
						</div>
					</form>
					<!-- メッセージ編集 -->
					<form action="EditChatServlet" method="post" id="editChatForm">
						<input type="hidden" name="roomId" id="roomId"
							value="${room.roomId}"> <input type="hidden"
							name="editChatLogId" id="editChatLogId" value=""> <input
							type="hidden" name="editMessage" id="editMessage" value="">
					</form>
					<!-- メッセージ削除 -->
					<form action="DeleteChatServlet" method="post" id="deleteChatForm">
						<input type="hidden" name="deleteChatLogId" id="deleteChatLogId"
							value=""> <input type="hidden" name="roomId"
							value="${room.roomId}">
					</form>
				</div>
			</div>

			<!--contents -->
		</section>

		<!--main -->
	</div>


	<!-- ローディングサイン-->
	<div class="loading js-loading">
		<div>
			<span><img id="ran" alt="ここに表示されます。" src=""></span>
		</div>
	</div>

	<form action="LeavingServlet" method="get" id="taiForm">
		<input type="hidden" name="roomId2" value="${room.roomId}">
	</form>

	<!-- container -->
	<script src="js/jquery-3.2.0.min.js"></script>
	<script src="js/main.js"></script>
	<script src="js/edit.js"></script>
	<script src="js/delete.js"></script>
	<script src="js/randomImg.js"></script>
	<script src="js/load.js"></script>
	<script src="js/omitted.js"></script>
	<script src="js/ota.js"></script>
	<script src="js/sendButton.js"></script>
</body>
</html>