function showUnreadNews() {
	$(document).ready(function() {
		$.ajax({
			type : "GET",
			url : "../../sendMail",
			datatype : 'json',
			success : function(msg) {
				//alert(msg);
				var obj = JSON.parse(msg);
				//alert(obj.summary);
//				PF('g').show([ {
//					summary : "here1",
//					detail : "here2",
//					severity : "info"
//				} ]);
//				PF('g').show([ {
//					summary : obj.summary,
//					detail : obj.detail,
//					severity : obj.severity
//				} ]);
				 $.each(obj, function(idx, item) {
				 //alert(item);
				 PF('g').show([ {
				 summary : item.summary,
				 detail : item.detail,
				 severity : 'info'
				 } ]);
				 });
			}
		});
	});
}
setInterval('showUnreadNews()', 5000);