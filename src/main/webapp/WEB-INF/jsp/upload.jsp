<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザアイコン変更</title>
</head>
<body>
	<form method="post" enctype="multipart/form-data">
		<input type="file" name="uploadImage" id="uploadImage"> <img
			id="img1" style="width: 100px; height: 100px;" /> <input
			type="submit" id="button" value="送信">
	</form>
	<form action="MainServlet" method="get">
		<input type="submit" value="キャンセル">
	</form>
	<script src="js/jquery-3.2.0.min.js"></script>
	<script src="js/upload.js"></script>
</body>
</html>