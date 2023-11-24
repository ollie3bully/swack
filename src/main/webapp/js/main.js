/**
 *チャットログのスクロール
 */
let before;
$(window).on('load', function() {
	let chatLogId = getParam('chatLogId');
	if (chatLogId == null) {
		$('#logArea').scrollTop($('#logArea')[0].scrollHeight);
		$('#message').focus();
	} else {
		// 変更要素を取得
		let element = document.getElementById(chatLogId);
		// 画面中央に来るようにスクロール
		element.scrollIntoView({ block: 'center' });
		// ピカッと光る
		element.classList.add('pika-box');
		element.classList.add('highlight');
		// 1秒後に外す
		setTimeout(function() {
			element.classList.remove('highlight');
		}, 1000);
	}
	before = $('#lastChatLogId').val();
	setInterval(reload, 15000);

});
function reload() {
	$.get(document.URL).done(function(data) {
		const doc = new DOMParser().parseFromString(data, 'text/html');
		$('#logArea').html(doc.querySelector('#logArea').innerHTML);
		let after = $('#lastChatLogId').val();
		if (before < after) {
			$('#' + before).after('<span class="new">ここから新着メッセージ</span>');
		}
		$('#logArea').scrollTop($('#logArea')[0].scrollHeight);
	});
}

function logout() {
	localStorage.removeItem('loginData');
	$('#logoutForm').submit();
}
/**
 * Get the URL parameter value
 *
 * @param  name {string} パラメータのキー文字列
 * @return  url {url} 対象のURL文字列（任意）
 */
function getParam(name, url) {
	if (!url) url = window.location.href;
	name = name.replace(/[\[\]]/g, '\\$&');
	var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
		results = regex.exec(url);
	if (!results) return null;
	if (!results[2]) return '';
	return decodeURIComponent(results[2].replace(/\+/g, ' '));
}