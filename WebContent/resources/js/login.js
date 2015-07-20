function $$(id) {
	return document.getElementById(id);
}

function checkUserName(id, outputId) {
	var re = /^[0-9a-zA-Z]{1}[0-9a-zA-Z_]{5,19}$/;
	if ($$(id).value == "" || $$(id).value == "User Name") {
		$$(outputId).style.display = "block";
		$$(outputId).innerHTML = "User name cannot be empty";
		return false;
	} else {
		if (!re.test($$(id).value)) {
			$$(outputId).style.display = "block";
			$$(outputId).innerHTML = "user name is illegal";
			return false;
		} else {
			$$(outputId).style.display = "none";
			return true;
		}
	}
}

function checkUserPass(id, outputId) {
	//未检测密码是否正确
	if ($$(id).value == "" || $$(id).value == "Password") {
		$$(outputId).style.display = "block";
		$$(outputId).innerHTML = "Password cannot be empty！";
		return false;
	} else {
		$$(outputId).style.display = "none";
		return true;
	}
}

function dealInputDefaultValue(id, defaultValue) {
	$$(id).onfocus = function() {
		this.style.color = "#333";
		//alert(this.type);
		if (this.name == "userpass") {
			this.type = "password";
		}
		if (this.type == "password") {
			checkUserName("uname", "checkInfo");
		}
		if (this.value == defaultValue || this.value == " ") {
			this.value = "";
		}
	};

	$$(id).onblur = function() {
		if (this.value == defaultValue || this.value == " " || this.value == "") {
			this.value = defaultValue;
			if (this.type == "password") {
				this.type = "text";
			}
			this.style.color = "#999";
		} else {
			this.value = this.value;
			this.style.color = "#333";
		}
	};
}

function toggleCheckInfo(vle) {
	$$("checkInfo").style.display = vle;
}

function loginMethod(id, methodId1, methodId2) {
	var intNum = 0;
	var m1 = $$(methodId1);
	var m2 = $$(methodId2);
	$$(id).onclick = function() {
		intNum++;
		switch (intNum) {
		case 1:
			m1.style.display = "none";
			m2.style.display = "block";
			this.title = "return";
			this.style.height = "50px";
			toggleCheckInfo("none");
			break;
		case 2:
			m1.style.display = "block";
			m2.style.display = "none";
			this.title = "Scan with your phone to login";
			this.style.height = "73px";
			intNum = 0;
			break;
		}
	};
}

window.onload = function() {
	dealInputDefaultValue("username", "User Name");
	dealInputDefaultValue("password", "Password");
	$$("lgnForm").onsubmit = function() {
		return checkUserPass("password", "checkInfo")
				&& checkUserName("username", "checkInfo");
	};
};