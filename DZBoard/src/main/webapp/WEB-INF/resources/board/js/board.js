const main = async () => {
	let loPage = 1;
	let hiPage = 10;
	let currentPage = 1;
	let total = 10;
	
	let pageButtonEventRefs = [];
	
	const row = document.querySelector("#postRow").cloneNode(true);
	const createTag = rowData => {
		const copy = row.cloneNode(true);
		copy.style.display = 'table-row';
		copy.querySelector(".id").textContent = rowData.id;
		copy.querySelector(".category").textContent = rowData.category;
		
		const aTag = copy.querySelector(".title > a");
		aTag.href = `/DZBoard/board/view?id=${rowData.id}`;
		aTag.textContent = rowData.parent == rowData.id
			? rowData.title
			: `[${rowData.parent}에 대한 답글]${rowData.title}`;
		
		copy.querySelector(".author").textContent = rowData.author;
		copy.querySelector(".viewcount").textContent = rowData.viewcount;
		copy.querySelector(".good").textContent = rowData.good;
		copy.querySelector(".bad").textContent = rowData.bad;
		copy.querySelector(".createdAt").textContent = rowData.createdAt;
		mainBoard.appendChild(copy);
	}

	const search = (pageNo, pageSize) => (async e => {
		let start = new Date();
		const response = await fetch(`/DZBoard/board/${searchType.value}`, {
			method: 'POST'
			, headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}
			, body: JSON.stringify({
				pageNo
				, pageSize
				, [searchType.value]: query.value
			})
		});
		const json = await response.json();
		mainBoard.replaceChildren();
		if (json.status) {
			json.data.forEach(rowData => createTag(rowData));
			currentPage = pageNo;
			total = json.total;
			
			loPage = Math.floor((currentPage-1)/10)*10+1;
			hiPage = Math.min(Math.ceil((currentPage)/10)*10, Math.ceil(total/10));
			let page = loPage;
			pageButtons.querySelectorAll("button").forEach(btn => {
				btn.dataset.page = page;
				btn.textContent = page;
				if (page <= hiPage) {
					btn.style.display = "inline-block";
					btn.removeEventListener("click", pageButtonEventRefs[page-loPage]);
					pageButtonEventRefs[page-loPage] = search(page, 10)
					btn.addEventListener("click", pageButtonEventRefs[page-loPage]);
				} else {
					btn.style.display = "none";
				}
				
				if (page == currentPage) {
					btn.style.color = 'red';
				} else {
					btn.style.color = 'black';
				}
				page++;
			});
		} else {
			alert('데이터가 존재하지 않습니다.');
		}
		let time = new Date() - start;
		console.log(`소요시간: ${time}`);
	});

	searchButton.addEventListener("click", search(1, 10));
	await search(1, 10)();
	
	firstPage.addEventListener("click", search(1, 10));
	lastPage.addEventListener("click", async e => {
			await search(Math.ceil(total/10), 10)();
		});
	prevSet.addEventListener("click", async e => {
		if (loPage > 10) {
	 		loPage -= 10;
			hiPage = loPage + 9;
			await search(hiPage, 10)();
		}
	});
	nextSet.addEventListener("click", async e => {
		if (hiPage < Math.ceil(total/10)) {
			loPage += 10;
			hiPage = Math.min(hiPage+10, Math.ceil(total/10));
			await search(loPage, 10)();
		}
	})
};
window.onload = main;