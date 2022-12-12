const main = async () => {
	let webSocket = null;

	const createTrTag = chatInfo =>
		`<tr>
	<td class="chatRoomName" >${chatInfo.name}</td>
	<td class="chatRoomSize" >${chatInfo.size}</td>
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
			chatRoomListBody.innerHTML = data.join('');
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
		if (member.value == '') {
			alert("이름을 입력해주세요");
			return;
		}
		chatHistory.innerHTML = '';
		webSocket = new WebSocket(`ws://localhost:8880/DZBoard/chat/webSocket/${chatRoomName}`);
		webSocket.onerror = message => {
		}
		webSocket.onmessage = message => {
			chatRoomListView();
			chatHistory.innerHTML += `<div class='other'>${message.data}</div>`
		}
		webSocket.onopen = () => {
			webSocket.send(JSON.stringify({sender: member.value, message: `${member.value} 님이 입장하셨습니다.`}));
			chatRoomListView();
		}
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
			webSocket.send(JSON.stringify({sender: member.value, message: `${member.value} 님이 퇴장하셨습니다.`}));
			webSocket.close();
			webSocket = null;
		}
	});
	chatRoomListViewReload.addEventListener("click", chatRoomListView);
	chatRoomListView();
}

window.onload = main;