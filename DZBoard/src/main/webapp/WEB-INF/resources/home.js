const main = () => {
	const logout = document.querySelector("#logout");
	if (logout != null) {
		logout.addEventListener("click", async e => {
			const response = await fetch("/DZBoard/member/logout");
			const json = await response.json();
			if (json.status) {
				alert(json.message);
				location.href = `/DZBoard/index`;
			}
		});
	}
	let prevSearch = '';
	const searchFetch = async e => {
		if (e.target.value == '') {
			prevSearch = e.target.value;
			suggest.replaceChildren();
			return false;
		}
		if (prevSearch == e.target.value) return false;
		prevSearch = e.target.value;

		const response = await fetch('/DZBoard/board/searchMain', {
			method: 'POST'
			, headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}
			, body: JSON.stringify({
				keyword: prevSearch
			})
		});
		const json = await response.json();
		if (json.status) {
			suggest.replaceChildren();
			json.data.forEach(d => suggest.appendChild(makeSuggestLi(d)));
		}
	}
	search.addEventListener("keyup", searchFetch);
	search.addEventListener("blur", searchFetch);
	const suggestLiCopy = document.querySelector("#suggest .suggestLi").cloneNode(true);
	const makeSuggestLi = data => {
		const copy = suggestLiCopy.cloneNode(true);
		const aTag = copy.querySelector("a");
		aTag.href = `/DZBoard/board/view?id=${data.id}`;
		aTag.textContent = data.title;
		copy.style.display = "list-item";
		return copy;
	}
}
window.onload = main;