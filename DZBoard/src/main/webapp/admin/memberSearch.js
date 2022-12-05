const main = () => {
	const tbody = document.querySelector("tbody");
	const memberTableClear = () => {
		let rows = tbody.querySelectorAll('tr');
		if (rows != null) rows.forEach(child => tbody.removeChild(child));
	}
	const firstQuery = document.querySelector("#first");
	const secondQuery = document.querySelector("#second");
	const searchType = document.querySelector("#searchType");
	searchType.addEventListener("change", e => {
		let target = e.target;
		target = target.querySelector(`option[value=${target.value}]`);
		if (target.getAttribute('class') == 'str') {
			firstQuery.type ="text";
			secondQuery.type = "hidden";
			secondQuery.value = null;
		} else if (target.getAttribute('class') == 'date') {
			firstQuery.type = "date";
			secondQuery.type = "date";
		} else {
			alert("유효하지 않은 값입니다");
			e.preventDefault();
		}
	});
	const searchButton = document.querySelector("#searchButton");
	searchButton.addEventListener("click", async e => {
		e.preventDefault();
		let req = {
			type: searchType.value
			, first: firstQuery.value
			, second: secondQuery.value
		};
		const response = await fetch("/DZBoard/admin/memberList", {
			method: 'POST'
			, headers: {
				'Content-Type': 'application/json;utf-8'
			}
			, body: JSON.stringify(req)
		});
		if (response.ok) {
			const json = await response.json();
			const status = json.status;
			const data = json.data;
			memberTableClear();
			let innerHTML = tbody.innerHTML;
			data.forEach(el => innerHTML += makeTRTag(el));
			tbody.innerHTML = innerHTML;
		} else {
			alert("네트워크 오류");
		}
	});
	const makeTRTag = (member) => `<tr>
			<td>${member.id}</td>
			<td>${member.pwd}</td>
			<td>${member.name}</td>
			<td>${member.email}</td>
			<td>${member.phone}</td>
			<td>${member.createdAt}</td>
			<td>${member.updatedAt}</td>
			<td>${member.authority}</td>
			<td><a href='/DZBoard/admin/updateMember?id=${member.id}'>수정</a></td>
			<td><a href='/DZBoard/admin/deleteMember?id=${member.id}'>삭제</a></td>
		</tr>`;
}

window.onload = main;