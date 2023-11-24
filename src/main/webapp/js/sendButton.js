'use strict';

if ($("#text").val().length == 0) {
	$("#sender").css("color", "rgba(44,45,48,.75)");
	$("#sender").css("background", "#e8e8e8");
	$("#sender").prop("disabled", true);
}


$("#text").on("keydown keyup keypress change", function() {
	if ($(this).val().length < 1) {
		$("#sender").prop('disabled', true);
	} else {
		$("#sender").prop('disabled', false);
	}
});
