const main = () => {
	const updateUrlAuth = async (url) => {
		location.href = `/DZBoard/admin/updateUrlAuthForm?url=${url}`
	}

	const deleteUrlAuth = async (url) => {
		const response = await fetch(`/DZBoard/admin/deleteUrlAuth`, {
			method: 'POST'
			, headers: {
				'Content-Type': `application/json;charset=utf-8`
			}
			, body: JSON.stringify({ url })
		});
		const json = await response.json();
		alert(json.message);
	}
	
	const getUrlAuthList = async () => {
		const response = await fetch("/DZBoard/admin/viewUrlAuth");
		const json = await response.json();
		if (json.status) {
			const data = [];
			json.data
				.sort((a, b) => a.url.localeCompare(b.url))
				.forEach(row => data.push(makeTrTag(row)));
			content.innerHTML = data.join('');

			document.querySelectorAll(".updateButton").forEach(tag => {
				tag.addEventListener("click", () => updateUrlAuth(tag.dataset.url));				
			});
			document.querySelectorAll(".deleteButton").forEach(tag => {
				tag.addEventListener("click", () => deleteUrlAuth(tag.dataset.url))
			});
		}
	}
	const makeTrTag = row => `<tr>
	<td><a href='${row.url}'>${row.url}</a></td>
	<td>${row.note}</td>
	<td>${row.authority}</td>
	<td><button class="updateButton" data-url=${row.url}>수정</button></td>
	<td><button class="deleteButton" data-url=${row.url}>삭제</button></td>
</tr>`
	getUrlAuthList();

}
window.onload = main;