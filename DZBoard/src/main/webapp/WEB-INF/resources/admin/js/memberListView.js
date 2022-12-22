const main = () => {
	const tbody = document.querySelector("tbody");
	const firstQuery = document.querySelector("#first");
	const secondQuery = document.querySelector("#second");
	const searchType = document.querySelector("#searchType");
	const more = document.querySelector("button[id=more]");
	const row = tbody.querySelector("tr").cloneNode(true);
	
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
			new URLSearchParams(searchRequestJSON[searchType.value]).toString()
		}`);
		if (!response.ok) {
			alert("네트워크 오류");
			return false;
		}
		
		// draw:
		const json = await response.json();
		if (json.status) {
			tbody.replaceChildren();
			json.data.forEach(x => tbody.appendChild(makeTRTag(x)));
			more.dataset.request = json.more;
		} else {
			more.dataset.request = '';
		}
	}
	
	const searchButton = document.querySelector("#searchButton");
	searchButton.addEventListener("click", searchCallback);
	
	more.addEventListener("click", async e => {
		if (more.dataset.request === '') return;
		const response = await fetch(`/DZBoard/admin/memberSearch/${searchType.value}?${more.dataset.request}`);
		const json = await response.json();
		if (json.status) {
			json.data.forEach(x => tbody.appendChild(makeTRTag(x)));
			more.dataset.request = json.more;
		} else {
			more.dataset.request = '';
		}
	});
	
	const deleteMember = async e => {
		e.preventDefault();
		alert(e.target.dataset.id);
		const response = await fetch(`/DZBoard/admin/deleteMember`, {
			method: 'POST'
			, headers: {
				'Content-Type': `application/json;charset=utf-8`
			}
			, body: JSON.stringify({
				id: e.target.dataset.id
			})
		});
		const json = await response.json();
		if (json.status) {
			alert(json.message);
			tbody.removeChild(e.target.parentNode.parentNode);
		}
	}
	
	const makeTRTag = member => {
		const copy = row.cloneNode(true);
		copy.querySelector('.id').textContent = member.id;
		copy.querySelector('.pwd').textContent = member.pwd;
		copy.querySelector('.name').textContent = member.name;
		copy.querySelector('.email').textContent = member.email;
		copy.querySelector('.phone').textContent = member.phone;
		copy.querySelector('.createdAt').textContent = member.createdAt;
		copy.querySelector('.updatedAt').textContent = member.updatedAt;
		copy.querySelector('.authority').textContent = member.authority;
		copy.querySelector('.updateFormButton > a').href = `/DZBoard/admin/updateMemberForm?id=${member.id}`;
		const deleteATag = copy.querySelector('.deleteButton > a');
		deleteATag.dataset.id = member.id;
		deleteATag.addEventListener("click", deleteMember);
		copy.style.display = "table-row";
		return copy;
	}
}

window.onload = main;