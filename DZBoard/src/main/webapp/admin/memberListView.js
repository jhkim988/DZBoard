const main = () => {
	const tbody = document.querySelector("tbody");
	const firstQuery = document.querySelector("#first");
	const secondQuery = document.querySelector("#second");
	const searchType = document.querySelector("#searchType");
	const more = document.querySelector("button[id=more]");
	
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
			return false;
		}
	});
	
	const searchCallback = async e => {
		e.preventDefault();
		const searchRequestJSON = {
			allMember: { }
			, id: {
				id: firstQuery.value
			}
			, name: {
				name: firstQuery.value
			}
			, email: {
				email: firstQuery.value
			}
			, phone: {
				phone: firstQuery.value
			}
			, createdAt: {
				from: `${firstQuery.value} 00:00:00.0`
				, to: `${secondQuery.value} 00:00:00.0`
			}
			, updatedAt: {
				from: `${firstQuery.value} 00:00:00.0`
				,to : `${secondQuery.value} 00:00:00.0`
			}
			, authority: {
				level: firstQuery.value
			}
		};
		const response = await fetch(`/DZBoard/admin/memberSearch/${searchType.value}?${
			new URLSearchParams(searchRequestJSON[searchType.value])
		}`);
		if (!response.ok) {
			alert("네트워크 오류");
			return false;
		}
		
		// draw:
		const json = await response.json();
		if (json.status) {
			const data = [];
			json.data.forEach(x => data.push(makeTRTag(x)));
			tbody.innerHTML = data.join('');			
			more.dataset.request = json.more;
			console.log(json.more);
		} else {
			more.dataset.request = '';
		}
	}
	
	const searchButton = document.querySelector("#searchButton");
	searchButton.addEventListener("click", searchCallback);
	
	more.addEventListener("click", async e => {
		if (more.dataset.request == '') return;
		const response = await fetch(`/DZBoard/admin/memberSearch/${searchType.value}?${more.dataset.request}`);
		const json = await response.json();
		console.log(json);
		if (json.status) {
			const data = [];
			json.data.forEach(x => data.push(makeTRTag(x)));
			tbody.innerHTML += data.join('');
			more.dataset.request = json.more;
		} else {
			more.dataset.request = '';
		}
	});
	
	const makeTRTag = (member) => `<tr>
			<td class='id'>${member.id}</td>
			<td class='pwd'>${member.pwd}</td>
			<td class='name'>${member.name}</td>
			<td class='email'>${member.email}</td>
			<td class='phone'>${member.phone}</td>
			<td class='createdAt'>${member.createdAt}</td>
			<td class='updatedAt'>${member.updatedAt}</td>
			<td class='authority'>${member.authority}</td>
			<td><a href='/DZBoard/admin/updateMember?id=${member.id}'>수정</a></td>
			<td><a href='/DZBoard/admin/deleteMember?id=${member.id}'>삭제</a></td>
		</tr>`;
}

window.onload = main;