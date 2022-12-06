const main = () => {
	let currentPage = 1;
	const tbody = document.querySelector("tbody");
	const firstQuery = document.querySelector("#first");
	const secondQuery = document.querySelector("#second");
	const searchType = document.querySelector("#searchType");
	const footer = document.querySelector("footer");
	const useCursors = document.querySelectorAll(".useCursor");
	
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
	const searchEventCallback = (getReq, hook = () => {}) => async e => {
		e.preventDefault();
		const response = await fetch("/DZBoard/admin/memberSearch", {
			method: 'POST'
			, headers: {
				'Content-Type': 'application/json;utf-8'
			}
			, body: JSON.stringify(getReq())
		});
		if (!response.ok) {
			alert("네트워크 오류");
			return false;
		}
		const data = [];
		const json = await response.json();
		if (json.status) {
			console.log(json.data);
			json.data.forEach(x => data.push(makeTRTag(x)));
			tbody.innerHTML = data.join('');			
		}
		hook();
	};
	
	const firstReq = () => ({
			type: searchType.value
			, first: firstQuery.value
			, second: secondQuery.value
			, page: 0
			, limit: 10
			, asc: true
			, hasLast: false
		});
	
	const searchButton = document.querySelector("#searchButton");
	searchButton.addEventListener("click", searchEventCallback(firstReq));
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
	
	const step = [-2, -1, 0, 1, 2]
	useCursors.forEach((button, idx) => {
		const req = () => ({
			type: searchType.value
			, first: firstQuery.value
			, second: secondQuery.value
			, page: step[idx]
			, limit: 10
			, asc: step[idx] > 0
			, hasLast: true
			, last: step[idx] > 0 ? tbody.firstChild.querySelector("td").textContent : tbody.lastChild.querySelector("td").textContent
		})
		button.addEventListener('click', searchEventCallback(req, () => {
			button.value = currentPage + step[idx];
			console.log("hook");
		}));
	});
}

window.onload = main;