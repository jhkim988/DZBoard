const main = () => {
	const fileUploads = document.querySelector("#fileUploads");
	const fileInput = document.querySelector("#file");
	const fileInputCancel = document.querySelector("#fileInputCancel");
		
	const fileChangeEventCallback = e => {
		console.log(e.target.value);
		if (e.target.value == '') {
			fileInputCancel.style.display = 'none';
			if (fileUploads.childElementCount > 1) {
				fileUploads.removeChild(e.target);				
			}
		} else {
			fileInputCancel.style.display = 'inline-block';	
			fileUploads.innerHTML += `
<label>
	<input type="file" name="file onchange='fileChangeEventCallback(this)'"/>
	<button onclick='fileUploadCancelEventCallback(this)'>X</button>			
</label>
`;
		}
	}
	
	const fileUploadCancelEventCallback = e => {
		e.preventDefault();
		fileInput.value = '';
		e.target.style.display = 'none';
	}
	
	fileInput.addEventListener("change", fileChangeEventCallback);
	fileInputCancel.addEventListener("click", fileUploadCancelEventCallback);

	
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