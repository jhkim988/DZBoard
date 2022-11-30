const main = () => {
	const validInputObj = {
		id: text => text.match(/[^0-9A-Za-z]/g) == null
		, pwd: text => text.match(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/)
		, pwdchk: text => document.querySelector('#pwd').value === text
		, email: text => text
		
	}
	const inputs = document.querySelectorAll('input');
	inputs.forEach(input => input.addEventListener("blur", e => {
		const target = e.target;
		const id = target.getAttribute('id');
		const mark = document.querySelector(`#mark${id}`);
		if (!mark) return;
		
		validInput(target, target.value)
		? mark.style.display = 'none'
		: mark.style.display = 'block';
	}));
	const validInput = (target, text) => {
		if (text === "") return false;
		return validInputObj[target.getAttribute('id')](text);
	}
}

window.onload = main;
