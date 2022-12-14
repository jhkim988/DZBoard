const main = () => {
	createPostForm.addEventListener("submit", async e => {
		e.preventDefault();
		const editor = document.querySelector("body > form > div > div.ck.ck-editor__main > div");
		const formData = new FormData(document.querySelector("#createPostForm"));
		formData.set("content", editor.innerHTML);
		
		const response = await fetch("/DZBoard/board/createPost", {
			method: 'POST'
			, cache: 'no-cache'
			, body: formData
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