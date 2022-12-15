const main = () => {
	const pwd = document.querySelector("#pwd");
	const name = document.querySelector("#name");
	const email = document.querySelector("#email");
	const phone = document.querySelector("#phone");
	const updateButton = document.querySelector("#updateButton");
	
	const originValue = {
		id: id.textContent
		, name: name.value
		, email: email.value
		, phone: phone.value
	}
	
	const dupCheck = async (type, value) => {
		if (value == originValue[type]) return ({
			status: true
			, message: "사용 가능"
		});
		const response = await fetch(`/DZBoard/member/dupMemberCheck`, ({
			method: 'POST'
			, headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}
			, body: JSON.stringify({type, value})
		}));
		return await response.json();
	}
	
	email.addEventListener("blur", async e => {
		const json = await dupCheck("email", email.value);
		if (json.status) return;
		alert(json.message);
	});
	phone.addEventListener("blur", async e => {
		const json = await dupCheck("phone", phone.value);
		if (json.status) return;
		alert(json.message);
	});
	
	updateButton.addEventListener("click", async e => {
		e.preventDefault();
		if (pwd.value != pwdchk.value) {
			alert("비밀번호를 확인해주세요");
			return false;
		}
		if (!dupCheck("email", email.value)) {
			alert("중복 이메일 입니다.");
			return false;
		}
		if (!dupCheck("phone", phone.value)) {
			alert("중복 연락처 입니다.");
			return false;
		}
		const response = await fetch("/DZBoard/member/updateMember", {
			method: 'POST'
			, headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}
			, body: JSON.stringify({
				pwd: pwd.value
				, name: name.value
				, email: email.value
				, phone: phone.value
			})
		});
		const json = await response.json();
		if (json.status) {
			alert("수정되었습니다.");
			location.href = "/DZBoard/index";
		} else {
			alert("네트워크 오류");
		}
	});
}

window.onload = main;