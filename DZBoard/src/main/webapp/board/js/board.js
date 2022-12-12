const main = () => {
	const prevAnchor = document.querySelector("#prev");
	const nextAnchor = document.querySelector("#next");
	const mainBoard = document.querySelector("#mainBoard");

	const getFirstLastChild = () => {
		const ret = {
			firstPostId: mainBoard.firstElementChild.querySelector(".id").textContent
			, lastPostId: mainBoard.lastElementChild.querySelector(".id").textContent
			, firstCreatedAt: mainBoard.firstElementChild.querySelector(".createdAt").textContent
			, lastCreatedAt: mainBoard.lastElementChild.querySelector(".createdAt").textContent
		}
		for (let key in ret) {
			ret[key] = encodeURIComponent(ret[key]);
		}
		return ret;
	};

	const findAllPosts = () => {
		const childs = getFirstLastChild();
		prevAnchor.href = `/DZBoard/board?id=${childs.firstPostId}&date=${childs.firstCreatedAt}&next=${false}`;
		nextAnchor.href = `/DZBoard/board?id=${childs.lastPostId}&date=${childs.lastCreatedAt}&next=${true}`;
	}

	const findOnePostByPostId = () => {
		prevAnchor.href = '#';
		nextAnchor.href = '#';
	}

	const findPostsByCategory = () => {
		const childs = getFirstLastChild();
		const queryValue = encodeURIComponent(new URLSearchParams(location.search).get("query"));
		prevAnchor.href = `/DZBoard/board/category?query=${queryValue}&id=${childs.firstPostId}&date=${childs.firstCreatedAt}&next=${false}`;
		nextAnchor.href = `/DZBoard/board/category?query=${queryValue}&id=${childs.lastPostId}&date=${childs.lastCreatedAt}&next=${true}`;
	}

	const findPostsByTitle = () => {
		const childs = getFirstLastChild();
		const queryValue = encodingURIComponent(new URLSearchParams(location.search).get("query"));
		prevAnchor.href = `/DZBoard/board/title?query=${queryValue}&id=${childs.firstPostId}&date=${childs.firstCreatedAt}&next=${false}`;
		nextAnchor.href = `/DZBoard/board/title?query=${queryValue}&id=${childs.lastPostId}&date=${childs.lastCreatedAt}&next=${true}`;
	}

	const findPostsByAuthor = () => {
		const childs = getFirstLastChild();
		const queryValue = encodeURIComponent(new URLSearchParams(location.search).get("query"));
		prevAnchor.href = `/DZBoard/board/author?query=${queryValue}&id=${childs.firstPostId}&date=${childs.firstCreatedAt}&next=${false}`;
		nextAnchor.href = `/DZBoard/board/author?query=${queryValue}&id=${childs.lastPostId}&date=${childs.lastCreatedAt}&next=${true}`;
	}

	const findPostsByContent = () => {
		const childs = getFirstLastChild();
		const queryValue = encodeURIComponent(new URLSearchParams(location.search).get("query"));
		prevAnchor.href = `/DZBoard/board/content?query=${queryValue}&id=${childs.firstPostId}&date=${childs.firstCreatedAt}&next=${false}`;
		nextAnchor.href = `/DZBoard/board/content?query=${queryValue}&id=${childs.lastPostId}&date=${childs.lastCreatedAt}&next=${true}`;
	}

	const findPostsByGood = () => {
		const childs = getFirstLastChild();
		const queryValue = encodeURIComponent(new URLSearchParams(location.search).get("query"));
		prevAnchor.href = `/DZBoard/board/good?query=${queryValue}&id=${childs.firstPostId}&date=${childs.firstCreatedAt}&next=${false}`;
		nextAnchor.href = `/DZBoard/board/good?query=${queryValue}&id=${childs.lastPostId}&date=${childs.lastCreatedAt}&next=${true}`;
	}

	const setPageButton = {
		all: findAllPosts
		, postId: findOnePostByPostId
		, category: findPostsByCategory
		, title: findPostsByTitle
		, author: findPostsByAuthor
		, content: findPostsByContent
		, good: findPostsByGood
	}

	const searchMethod = location.pathname.split("/")[3];
	searchMethod
		? setPageButton[searchMethod]()
		: findAllPosts();

	searchButton.addEventListener("click", e => {
		e.preventDefault();
		if (searchType.value == 'all') {
			location.href = `/DZBoard/board`;
		} else {
			location.href = `/DZBoard/board/${searchType.value}?query=${encodeURIComponent(query.value)}`
			window.onload = main;
		}
	});
};
window.onload = main;