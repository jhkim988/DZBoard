const main = () => {
	const validInputObj = {
		id: text => text.match(/[^0-9A-Za-z]/g) == null
		, pwd: text => text.match(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/)
		, pwdchk: text => document.querySelector('#pwd').value === text
		, email: text => text
	}
	const inputs = document.querySelectorAll('input.required');
	inputs.forEach(input => input.addEventListener("blur", e => {
		const target = e.target;
		const id = target.getAttribute('id');
		const mark = document.querySelector(`#mark${id}`);
		if (!mark) return;
		
		validInput(target, target.value)
		? correctInputStyle(target, mark)
		: worngInputStyle(target, mark);
	}));
	const validInput = (target, text) => {
		if (text === "") return false;
		return validInputObj[target.getAttribute('id')](text);
	}
	const worngInputStyle = (input, mark) => {
		mark.style.display = 'block';
		input.style.border = '1px solid #ff424c';
    	input.style.background = '#fff2f3';
	}
	const correctInputStyle = (input, mark) => {
		mark.style.display = 'none';
    	input.style.background = 'none';
		input.style['border-top'] = '1px solid #999';
		input.style['border-right'] = '1px solid #e2e2e2';
		input.style['border-bottom'] = '1px solid #ddd';
		input.style['border-left'] = '1px solid #aaa';
	}
	
	const termAllSelect = document.querySelector('#termAll');
	const terms = document.querySelectorAll('.term');
	termAllSelect.addEventListener('click', e => {
		terms.forEach(x => x.checked = e.target.checked);
	});
	
	const emailHost = document.querySelector("#emailHost");
	const emailSelect = document.querySelector("#emailSelect");
	emailSelect.addEventListener('change', e => {
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
