(function() {
	var urlValue = document.getElementById('mainForm:tabView:url').value;
	var titleValue = document.getElementById('mainForm:tabView:title').innerHTML;
	//alert(urlValue);
	var summaryValue = document.getElementById('mainForm:tabView:summary').innerHTML;
	//alert(value);
	var p = {
		url : urlValue,
		showcount : '0',/*是否显示分享总数,显示：'1'，不显示：'0' */
		desc : 'NP Pillar - Professional Newspaper Publisher Product',/*默认分享理由(可选)*/
		summary : summaryValue,/*分享摘要(可选)*/
		title : titleValue,/*分享标题(可选)*/
		site : '',/*分享来源 如：腾讯网(可选)*/
		pics : '', /*分享图片的路径(可选)*/
		style:'201',
		width:113,
		height:39
	};
	var s = [];
	for ( var i in p) {
		s.push(i + '=' + encodeURIComponent(p[i] || ''));
	}
	//alert(['http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?',s.join('&')].join(''));
	document.getElementById('mainForm:tabView:shareLink').value = ['http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?',
	                                           					s.join('&')].join('');

//	document
//			.write([
//					'<a version="1.0" class="qzOpenerDiv" href="http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?',
//					s.join('&'), '" target="_blank">分享</a>' ].join(''));
})();