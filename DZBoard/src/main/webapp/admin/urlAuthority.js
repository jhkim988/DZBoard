const main = () => {
	const getUrlAuthList = async () => {
		const response = await fetch("/DZBoard/admin/viewUrlAuthority");
		const json = await response.json();
		if (json.status) {
			const data = [];
			json.data
				.sort((a, b) => a.url.localeCompare(b.url))
				.forEach(row => data.push(makeTrTag(row)));
			content.innerHTML = data.join('');
		}
	}
	const makeTrTag = row => `<tr>
	<td>${row.url}</td>
	<td>${row.authority}</td>
	<td><a href="/DZBoard/admin/updateUrlAuthority?url=${row.url}">수정</a></td>
	<td><a href="/DZBoard/admin/deleteUrlAuthority?url=${row.url}">삭제</a></td>
</tr>`
	getUrlAuthList();
}
window.onload = main;