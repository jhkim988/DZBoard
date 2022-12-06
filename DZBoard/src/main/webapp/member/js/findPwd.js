const main = () => {
	let certiId = 1;
	const req = {
		type: ''
		, value: ''
	};
	const id = document.querySelector("#id");
	const checkId = document.querySelector("#checkId");
	checkId.addEventListener('click', async e => {
		req.type = 'idCheck'
		req.value = id.value;
		const response = await fetch("/DZBoard/findPwd", {
			method: 'POST'
			, headers: {
				'Content-Type': 'application/json;utf-8'
			}
			, body: JSON.stringify(req)
		});
		
		const json = await response.json();
		if (json.status) {
			certiId = json.id;			
		} else {
			alert(json.message);
		}
	});
	
	
	
}

window.onload = main;