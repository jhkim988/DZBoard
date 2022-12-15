const main = () => {
	const fileUploads = document.querySelector("#fileUploads");
	const fileInput = document.querySelector("#file");
	const fileInputCancel = document.querySelector("#fileInputCancel");
		
	const fileChangeEventCallback = e => {
		const fileInputCancel = e.target.parentElement.querySelector(".fileInputCancel");
		const prev = e.target.dataset.prev;
		console.log(prev);
		if (e.target.value == '') {
			fileInputCancel.style.display = 'none';
			if (fileUploads.childElementCount > 1) {
				fileUploads.removeChild(e.target);				
			}
		} else if (prev == '') {
			fileInputCancel.style.display = 'inline-block';	
			e.target.dataset.prev = e.target.value;
			fileUploads.appendChild(createFileInput());
		} else {
			e.target.dataset.prev = e.target.value;
		}
	}
	
	const fileUploadCancelEventCallback = e => {
		e.preventDefault();
		const fileInput = e.target.parentElement.querySelector(".file")
		e.target.parentElement.remove(fileInput);
		e.target.parentElement.remove(e.target);	
	}
	
	// TODO: Use clone node
	const createFileInput = () => {
		const label = document.createElement("label");
		
		const input = document.createElement("input");
		input.type = "file";
		input.name = "file";
		input.setAttribute("class", "file");
		input.addEventListener("change", fileChangeEventCallback);
		input.dataset.prev = '';
		label.appendChild(input);
		
		const button = document.createElement("button");
		button.textContent = "X";
		button.setAttribute("class", "fileInputCancel");
		button.style.display = "none";
		button.addEventListener("click", fileUploadCancelEventCallback);
		label.appendChild(button);
		
		return label;
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