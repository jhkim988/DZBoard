const main = () => {
	const prevAnchor= document.querySelector("#prev");
	const nextAnchor = document.querySelector("#next");
	const tbody = document.querySelector("tbody");

	const tbodyFirstChildPostId = tbody.firstElementChild.querySelector(".id").textContent;
	const tbodyLastChildPostId = tbody.lastElementChild.querySelector(".id").textContent;

	const tbodyFirstChildCreatedAt = tbody.firstElementChild.querySelector(".createdAt").textContent;
	const tbodyLastChildCreatedAt = tbody.lastElementChild.querySelector(".createdAt").textContent;
	
	prevAnchor.href = `/DZBoard/board?id=${tbodyFirstChildPostId}&date=${tbodyFirstChildCreatedAt}&next=${false}`;
	nextAnchor.href = `/DZBoard/board?id=${tbodyLastChildPostId}&date=${tbodyLastChildCreatedAt}&next=${true}`;
};
window.onload = main;