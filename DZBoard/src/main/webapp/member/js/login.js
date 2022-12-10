const main = () => {
	const id = document.querySelector("#id");
	const pw = document.querySelector("#pwd");
	
	const getValue = () => ({
		id: id.value
		, pwd: pwd.value
	});
	
	const loginButton = document.querySelector("#loginButton");
	loginButton.addEventListener("click", async e => {
		e.preventDefault();
		const response = await fetch("/DZBoard/member/login", {
			method: 'POST'
			, headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}
			, body: JSON.stringify(getValue())
		});	
		const json = await response.json();
		if (json.status) {
			location.href = '/DZBoard/index';
		} else {
			alert(`로그인 실패: ${json.message}`);
		}
	})
	
}

window.onload = main;