const main = () => {
	const getPostId = () => new URLSearchParams(location.search).get('id');
	const goodBadFetch = async (req) => {
		const response = await fetch("/DZBoard/board/goodBad", {
			method: "POST"
			, headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}
			, body: JSON.stringify(req)
		});
		return await response.json();
	}
	goodButton.addEventListener("click", async e => {
		const json = await goodBadFetch({
			type: "good"
			, postid: getPostId()
		});
		alert(json.message);
		location.reload();
	});
	badButton.addEventListener("click", async e => {
		const json = await goodBadFetch({
			type: "bad"
			, postid: getPostId()
		});
		alert(json.message);
		location.reload();
	});
}
window.onload = main;