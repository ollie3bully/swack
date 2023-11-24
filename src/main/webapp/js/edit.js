'use strict';
let message = '';
$('body').on('click', '.edit', function(e) {
	$('#editChatLogId').val($(this).attr('id').slice(4));
	message = $(this).parent().prev().text();
	const textarea = '<textarea id="edit-form" class="form-control">' + message + '</textarea>';
	const cancelButton = '<button class="btn btn-secondary btm-sm edit-cancel">キャンセル</button>';
	const editButton = '<button class="btn btn-primary btm-sm edit-comp" id="button"  >編集</button>';

	$(this).parent().prev().html(textarea + cancelButton + editButton);
	$('.msg > textarea').focus();

	// 他の編集・削除ボタンのイベントを削除
	$('body').off('click', '.edit');
	$('body').off('click', '.delete');
	$('body').on('click', '.edit-comp', function() {
		$('#editMessage').val($('#edit-form').val());
		$('#editChatForm').submit();
	})

	$('body').on('click', '.edit-cancel', function() {
		let roomId = $('#roomId').val();
		location.href = 'MainServlet?roomId=' + roomId;
	});

	$("#edit-form").on("keydown keyup keypress change", function() {
		if ($(this).val().length < 1) {
			$("#button").prop('disabled', true);
		} else {
			$("#button").prop('disabled', false);
		}
	});


});