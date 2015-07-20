function showUnreadNews() {
	$(document).ready(function() {
		$.ajax({
			type : "GET",
			url : "../ipUtil",
			success : function(msg) {
				//alert(msg);
				document.getElementById('mainForm:ip').value = msg;
			}
		});
	});
}
showUnreadNews();