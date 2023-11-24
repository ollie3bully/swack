'use strict';
function check(){
  $("#overflow").show();
}
function next(){
  $("#overflow").hide();// 確認ボックスを消す
}
function tai(ele){
  $("#overflow").hide();// 退出ボタン押した処理
	var id = ele.getAttribute("id");
	console.log(id); 
  if(id == "R0000") {
	alert("everyoneチャンネルは退出出来ません");
  }else{
	const result = window.confirm("このルームから退出しますか？");
	if (result) {
		$('#taiForm').submit();
	}
}
}