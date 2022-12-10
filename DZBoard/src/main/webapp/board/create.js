const main = () => {
	send.addEventListener("click", async e => {
		e.preventDefault();
		const editor = document.querySelector("body > form > div > div.ck.ck-editor__main > div");
		const response = await fetch("/DZBoard/board/createPost", {
			method: 'POST'
			, headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}
			, body: JSON.stringify({
				title: title.value
				, category: category.value
				, content: editor.innerHTML
		})
		});
		const json = await response.json();
		if (json.status) {
			alert("글이 등록됐습니다.");
			location.href = "/DZBoard/board"
		} else {
			alert("글 등록에 실패했습니다.");
		}
	});
}

window.onload = main;