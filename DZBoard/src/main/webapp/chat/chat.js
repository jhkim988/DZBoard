const main = async () => {
	let webSocket = null;

	const createTrTag = chatInfo =>
		`<tr>
	<td>${chatInfo.name}</td>
	<td>${chatInfo.size}</td>
	<td>
		<button class='entranceButton' data-chatroomname='${chatInfo.name}' >
				입장
		</button>
	</td>
</tr>`
	
	const chatRoomListView = async () => {
		const response = await fetch(`/DZBoard/chat/chatRoomList`);
		const json = await response.json();
		if (json.status) {
			const data = [];
			json.data
				.map(chatInfo => createTrTag(chatInfo))
				.forEach(tag => data.push(tag));
			chatRoomList.innerHTML = data.join('');
			document.querySelectorAll('.entranceButton')
				.forEach(btn => btn.addEventListener('click',
					e => {
						entranceChatRoom(e, e.target.dataset.chatroomname)
					}));
		} else {
			alert("네트워크 오류");
		}
	}

	const entranceChatRoom = (e, chatRoomName) => {
		e.preventDefault();
		if (webSocket != null) {
			webSocket.close();
		}
		chatHistory.innerHTML = '';
		webSocket = new WebSocket(`ws://localhost:8080/DZBoard/chat/webSocket/${chatRoomName}`);
		webSocket.onerror = message => {
			alert(message.data);
		}
		webSocket.onmessage = message => {
			chatRoomListView();
			chatHistory.innerHTML += `<div class='other'>${message.data}</div>`
		}
		webSocket.onopen = chatRoomListView;
		webSocket.onclose = () => {
			chatRoomListView;
			chatHistory.innerHTML = '';
		}
	}

	connect.addEventListener("click", e => entranceChatRoom(e, chatRoomName.value));

	send.addEventListener("click", e => {
		e.preventDefault();
		if (message.value == '') return;
		if (webSocket != null) {
			chatHistory.innerHTML += `<div class='me'>${message.value}</div>`
			const messageObj = { sender: member.value, message: message.value };
			webSocket.send(JSON.stringify(messageObj));
			message.value = '';
		} else {
			alert("입장해주세요");
		}
	});

	disconnect.addEventListener("click", e => {
		e.preventDefault();
		if (webSocket != null) {
			webSocket.close();
			webSocket = null;
		}
	});
	chatRoomListViewReload.addEventListener("click", chatRoomListView);
	chatRoomListView();
}

window.onload = main;