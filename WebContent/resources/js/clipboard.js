var clip = null;
function $(id) {
	return document.getElementById(id);
}
function init() {
	clip = new ZeroClipboard.Client();
	clip.setHandCursor(true);
}
function copy() {
	clip.setText($('mainForm:tabView:url').innerHTML);
	alert($('mainForm:tabView:url').innerHTML);
}
