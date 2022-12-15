const main = () => {
	addUrlAuth.addEventListener("click", async e => {
		e.preventDefault();
		if (url.value == '' || note == '' || authority == '') {
			alert(`값을 입력해주세요`);
			return false;
		}
		const response = await fetch(`/DZBoard/admin/createUrlAuth`, {
			method: `POST`
			, headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}
			, body: JSON.stringify({
				url: url.value
				, note: note.value
				, authority: authority.value
			})
		});
		const json = await response.json();
		alert(json.message);
		if (json.status) {
			location.href = `/DZBoard/resources/admin/urlAuthority.html`;
		}
	});
}
window.onload = main;