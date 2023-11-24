'use strict';
$('body').on('click', '.logout', function() {
	const result = window.confirm("本当にログアウトしますか？");
	if (result) {
		$('#logoutForm').submit();
	}
})