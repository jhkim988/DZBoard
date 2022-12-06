const main = () => {
	const pwd = document.querySelector("#pwd");
	const deleteButton = document.querySelector("#deleteButton");
	deleteButton.addEventListener("click", async e => {
		e.preventDefault();
		const req = {
			pwd: pwd.value
		}
		const response = await fetch("/DZBoard/member/deleteMember", {
			method: 'POST'
			, headers: {
				'Content-Type': 'application/json;utf-8'
			}
			, body: JSON.stringify(req)
		});
		const json = await response.json();
		if (json.status) {
			alert("탈퇴 성공");
			location.href = "/DZBoard"
		} else {
			alert(json.message);
		}
	});
}

window.onload = main;