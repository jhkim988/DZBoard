const main = () => {
	send.addEventListener("click", async e => {
		const response = await fetch(`/DZBoard/board/update`, ({
			method: 'POST'
			, headers: {
				'Content-Type': `application/json;charset=utf-8`
			}
			, body: JSON.stringify({
				id: id.value
				, title: title.value
				, content: content.value
				, category: category.value
			})
		}));
		const json = await response.json();
		alert(json.message);
		if (json.status) {
			location.href = `/DZBoard/board`;
		}
	});
}
window.onload = main;