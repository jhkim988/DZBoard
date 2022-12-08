const main = () => {
	const pwd = document.querySelector("#pwd");

	const phonefirst = document.querySelector("#phonefirst");
	const phonemid = document.querySelector("#phonemid");
	const phonelast = document.querySelector("#phonelast");

	const phoneObserver = {
		list: [phonefirst, phonemid, phonelast]
		, update: async (style, attr) => phoneObserver.list.forEach(x => x.style[attr] = style[attr])
	}
	
	const email = document.querySelector("#email");
	const emailHost = document.querySelector("#emailHost");
	const emailSelect = document.querySelector("#emailSelect");
	const emailObserver = {
		list: [email, emailHost, emailSelect]
		, update: async (style, attr) => emailObserver.list.forEach(x => x.style[attr] = style[attr])
	}

	const phoneNumberMaker = () => `${phonefirst.value}${phonemid.value}${phonelast.value}`;
	const emailMaker = () => `${email.value}@${emailHost.value}`
	
	const idDupCheck = async text => {
		if (text.match(/[^0-9A-Za-z]/g) != null) {
			return ({
				status: false
				, message: '아이디는 영문, 숫자만 사용하실 수 있습니다.'
			});
		}
		const requestJSON = {
			type: "id"
			, value: text
		}
		const response = await fetch(`/DZBoard/member/dupMemberCheck`, {
			method: 'POST'
			, headers: {
				'Content-Type': "application/json;charset=utf-8"
			}
			, body: JSON.stringify(requestJSON)
		});
		const json = await response.json();
		return json;
	}
	
	const phoneDupCheck = async text => {
		if (phonefirst.value == '휴대폰 번호 선택' || phonemid.value == "" || phonelast.value == "") {
			return ({status : false, message: '입력해주세요'});
		}
		const requestJSON = {
			type: "phone"
			, value: phoneNumberMaker()
		}
		const response = await fetch(`/DZBoard/member/dupMemberCheck`, {
			method: 'POST'
			, headers: {
				'Content-Type': "application/json;charset=utf-8"
			}
			, body: JSON.stringify(requestJSON)
		});
		const json = await response.json();
		return json;
	}
	
	const emailDupCheck = async text => {
		if (email.value == '' || emailHost.value == '') {
			return ({status: false, message: '입력해주세요'});
		}
		const requestJSON = {
			type: "email"
			, value: emailMaker()
		}
		const response = await fetch(`/DZBoard/member/dupMemberCheck`, {
			method: 'POST'
			, headers: {
				'Content-Type': "application/json;charset=utf-8"
			}
			, body: JSON.stringify(requestJSON)
		});
		const json = await response.json();
		return json;
	}

	const validInput = (target, text) => {
		if (text === "") return ({
			status: false
			, message: '입력해주세요'
		});
		return validInputObj[target.getAttribute('id')](text);
	}

	const validInputObj = {
		id: idDupCheck
		, pwd: text => {
			const check = text.match(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/)
			if (check) {
				return ({ status: true, message: ""})
			} else {
				return ( { status: false, message: "안전한 사용을 위해 영문, 숫자, 특수문자 조합 8~15자를 사용해 주세요."})
			}
		}
		, pwdchk: text => {
			if (pwd.value === text) {
				return ({ status: true, message: ""});
			} else {
				return ({ status: false, message: "비밀번호를 재입력해주세요."});
			}
			
		}
		, phonefirst: phoneDupCheck
		, phonemid: phoneDupCheck
		, phonelast: phoneDupCheck
		, email: emailDupCheck
		, emailHost: emailDupCheck
		, emailSelect: emailDupCheck
	}
	
	const valueChangeEventCallback = async e => {
		const target = e.target;
		const markId = target.dataset.mapping;
		const mark = document.querySelector(`#${markId}`);
		if (!mark) return;
		const json = await validInput(target, target.value);
		json.status
		? correctInputStyle(target, mark)
		: wrongInputStyle(target, mark);
		if (json.message) mark.textContent = json.message;
		
		if (markId == 'markphone') {
			phoneObserver.update(target.style, 'border');
			phoneObserver.update(target.style, 'background');
		} else if (markId == "markemail") {
			emailObserver.update(target.style, 'border');
			emailObserver.update(target.style, 'background');
		}
	}
	
	const inputs = document.querySelectorAll('input');
	inputs.forEach(input => input.addEventListener("blur", valueChangeEventCallback));
	const selects = document.querySelectorAll('select');
	selects.forEach(select => select.addEventListener('change', valueChangeEventCallback));
	const wrongInputStyle = (input, mark) => {
		mark.style.display = 'block';
		mark.style.color = '#ff424c';
		input.style.border = '1px solid #ff424c';
    	input.style.background = '#fff2f3';
	}
	
	const correctInputStyle = (input, mark) => {
		mark.style.display = 'block';
		mark.style.color = '#44d2d6';
	    input.style.border = '1px solid #00c2c7';
    	input.style.background = '#f2ffff';
    	input.style.color = '#333';
	}
	
	const phoneNumber = document.querySelectorAll(".phone");
	phoneNumber.forEach(p => {
		p.addEventListener("keyup", e => {
			let value = e.target.value;
			if (value.length > 4) {
				alert("휴대폰 번호는 4자리까지만 입력 가능합니다.");
				e.target.value = e.target.value.slice(0, 4);
				e.defaultPrevented();
			}
		});
	});
	
	const termAllSelect = document.querySelector('#termAll');
	const terms = document.querySelectorAll('.term');
	termAllSelect.addEventListener('click', e => {
		terms.forEach(x => x.checked = e.target.checked);
	});
	
	emailSelect.addEventListener('change', e => {
		valueChangeEventCallback(e);
		const value = e.target.value;
		if (value == "emailManual") {
			emailHost.type = "text";
			emailHost.value = "";
		} else {
			emailHost.type = "hidden";
			emailHost.value = value;
		}
	});
	
	const registerButton = document.querySelector("#register");
	registerButton.addEventListener('click', e => {
		if (!Array.prototype.slice.call(inputs).every(input => validInput(input))) {
			alert("입력 양식을 확인해주세요");
			e.preventDefault();
		}
	});
}

window.onload = main;
