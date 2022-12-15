const main = () => {
	const pwd = document.querySelector("#pwd");
	const name = document.querySelector("#name");
	const email = document.querySelector("#email");
	const phone = document.querySelector("#phone");
	const authority = document.querySelector("#authority");
	const getValues = () => ({
		id: new URLSearchParams(location.search).get('id')
		, pwd: pwd.value
		, name: name.value
		, email: email.value
		, phone: phone.value
		, authority: authority.value
	});
	const updateButton = document.querySelector("#updateButton");
	updateButton.addEventListener("click", async e => {
		const response = await fetch("/DZBoard/admin/updateMember", {
			method: 'POST'
			, headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}
			, body: JSON.stringify(getValues())
		});
		const json = await response.json();
		if (json.status) {
			location.href = '/DZBoard/resources/admin/memberListView.jsp';
		} else {
			alert(`네트워크 오류: ${json.message}`);
		}
	});
}

window.onload = main;