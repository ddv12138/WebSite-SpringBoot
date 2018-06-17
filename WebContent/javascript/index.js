window.onload = function(){
	$("#login").on("click",submitData);
	$("#username").on("input",syncData);
	$("#passwd").on("input",syncData);
	$("#signup").on("click",tosignup);
}
var submitData = function(){
	$.ajax({
		url:"./AccountManage",
		type:"post",
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		data: $('#loginform').serialize(),
		success:loginValidate
	});
}
var loginValidate = function(arg){
	console.log(arg);
}
var syncData = function(arg){
	$("#realusername").val($("#username").val());
	$("#realpasswd").val($("#passwd").val());
}
var tosignup = function(){
	window.location.href="./signup/signup.html";
}