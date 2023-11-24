'use strict';
//ローディング非表示処理
function endLoading() {
  $('.js-loading div').fadeOut(3000, function(){
    $('.js-loading').fadeOut(100);
  })
}

$(function () {
	var oldData = localStorage.getItem('user');
	if(oldData != null){
		localStorage.removeItem('user');
		endLoading();
  		setTimeout('endLoading()', 5000);
	}else{
		$('.loading').css('display', 'none');
	}
	
});