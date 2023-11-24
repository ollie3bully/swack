<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Swack - ログイン画面</title>
<link rel="shortcut icon" href="images/favicon.ico">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/update.css">
</head>
<body>
	<div class="container">
		<h1>Swack</h1>
		<h2>ユーザ情報変更</h2>
		<div class="card">
			<p class="error">${errorMsg}</p>
			<form action="UserUpdateServlet" method="post">
				<input type="text" name="userName" placeholder="ユーザー名"><br>
				<input type="password" name="password" placeholder="パスワード"><br>
				<input type="submit" value="変更">
			</form>
		</div>
		<a href="MainServlet">キャンセル</a>
		<div class ="link">
		<a href="UploadServlet">ユーザアイコン変更</a>
		</div>
		</div>
	<!-- container -->
	<script src="js/jquery-3.2.0.min.js"></script>
</body>
</html>