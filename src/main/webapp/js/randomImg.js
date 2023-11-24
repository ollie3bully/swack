'use strict';

$(function() {
	$('#ran').attr('src', hoge());
});

function hoge() {
	var rdimg = new Array();
	rdimg[0] = 'loading.gif';
	rdimg[1] = 'loading2.gif';
	rdimg[2] = 'loading3.gif';

	var selectimg = Math.floor(Math.random() * rdimg.length);
	var display = 'images/' + rdimg[selectimg];
	console.log(display);
	return display;
}