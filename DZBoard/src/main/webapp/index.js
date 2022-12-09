const main = () => {
	const logout = document.querySelector("#logout");
	if (logout != null) {
		logout.addEventListener("click", async e => {
			const response = await fetch("/DZBoard/member/logout");
			const json = await response.json();
			if (json.status) {
				alert(json.message);
				location.href = `/DZBoard`;
			}
		});
	}
}
window.onload = main;