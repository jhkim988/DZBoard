const main = () => {
	const pwd = document.querySelector("#pwd");
	const name = document.querySelector("#name");
	const email = document.querySelector("#email");
	const phone = document.querySelector("#phone");
	const updateButton = document.querySelector("#updateButton");
	updateButton.addEventListener("click", async e => {
		e.preventDefault();
		const req = {
			pwd: pwd.value
			, name: name.value
			, email: email.value
			, phone: phone.value
		}
		const response = await fetch("/DZBoard/member/updateMember", {
			method: 'POST'
			, headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}
			, body: JSON.stringify(req)
		});
		const json = await response.json();
		if (json.status) {
			alert("수정되었습니다.");
			location.href = "/DZBoard";
		} else {
			alert("네트워크 오류");
		}
	});
}

window.onload = main;