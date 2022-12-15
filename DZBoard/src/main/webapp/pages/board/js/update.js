const main = () => {
	send.addEventListener("click", async e => {
		e.preventDefault();
		const editorData = editor.getData();

		const response = await fetch(`/DZBoard/board/updatePost`, ({
			method: 'POST'
			, headers: {
				'Content-Type': `application/json;charset=utf-8`
			}
			, body: JSON.stringify({
				id: id.value
				, title: title.value
				, content: editorData
				, category: category.value
			})
		}));
		const json = await response.json();
		alert(json.message);
		if (json.status) {
			location.href = json.url;
		}
	});
}
window.onload = main;