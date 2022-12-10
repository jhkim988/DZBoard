const main = () => {
	updateButton.addEventListener("click", async e => {
		const response = await fetch(`/DZBoard/admin/updateUrlAuth`, {
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
			location.href = '/DZBoard/admin/urlAuthority.html';
		}
	});
}
window.onload = main;