const main = () => {
	const findIdMethod = document.querySelectorAll(".findIdMethod");
	const forms = {
		findIdByPhone: document.querySelector("#findIdByPhone")
		, findIdByEmail: document.querySelector("#findIdByEmail")
		, findIdByPhoneCerti: document.querySelector("#findIdByPhoneCerti")
		, findIdByIpinCerti: document.querySelector("#findIdByIpinCerti")
	}
		
	findIdMethod.forEach(item => {
		item.addEventListener('click', (e) => {
			const target = e.target;
			findIdMethod.forEach(i => {
				i.checked = false;
				const form = forms[i.dataset.mapping];
				form.style.display = 'none';
				form.querySelectorAll('input:not([type=submit])').forEach(x => x.value='');
			});
			target.checked = true;
			forms[target.dataset.mapping].style.display = 'block';
		});
	});
	
	const submits = document.querySelectorAll('input[type=submit]');
	submits.forEach(item => {
		item.addEventListener('click', async e => {
			e.preventDefault();
			const parent = e.target.parentNode;
			const req = {
				type: parent.id
				, first: parent.querySelector(".first").value
				, second: parent.querySelector(".second").value
			}
			const response = await fetch(`/DZBoard/member/findId`, {
				method: 'POST'
				, headers: {
					'Content-Type': 'application/json;utf-8'
				}
				, body: JSON.stringify(req)
			});
			const json = await response.json();
			if (json.status) {
				alert(`아이디는 ${json.id} 입니다.`);
			} else {
				alert(json.message);
			}
		});
	});
	
	const unsupporteds = document.querySelectorAll(".unsupported");
	unsupporteds.forEach(item => {
		item.addEventListener('click', e => {
			e.preventDefault();
			alert("아직 지원하지 않는 기능입니다.");	
		});
	});
}

window.onload = main;