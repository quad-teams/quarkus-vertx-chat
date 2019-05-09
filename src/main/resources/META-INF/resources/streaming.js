(function () {
  const messageTemplate = document.createElement('template');
  messageTemplate.innerHTML = `<div class="message"></div>`;

  const membersTemplate = document.createElement('template');
  messageTemplate.innerHTML = `
    <div class="message">
      <div class="members"></div>    
    </div>
  `;

  const messageList = document.getElementById('message-list');
  const chatInput = document.getElementById('chat-input');
  let eventSource;

  function tryExecutingCommand(message) {
    const parts = message.split(' ');

    switch (parts[0]) {
      case '/join': {
        executeJoin(parts.splice(1));
        break;
      }

      case '/leave': {
        executeLeave();
        break;
      }

      case '/members': {
        executeMembers();
        break;
      }

      default: {
        executeChat(message);
      }
    }
  }

  function executeJoin(parts) {
    localStorage.setItem('quad.chat.username', parts[0]);
    localStorage.setItem('quad.chat.alias', parts[1]);

    fetchMethod('/join', 'POST', {
      username: parts[0],
      alias: parts[1]
    })
      .then(response => {
        eventSource = new EventSource("/stream");
        eventSource.onmessage = event => {
          console.log(event);
          appendMessage(event.data);
        };

        chatInput.value = '';
      })
      .catch(error => {
        console.log(error);
      });
  }

  function executeLeave() {
    const username = localStorage.getItem('quad.chat.username');
    fetchMethod(`/leave?username=${username}`, 'PUT')
      .then(response => {
        localStorage.removeItem('quad.chat.username');
        localStorage.removeItem('quad.chat.alias');
        eventSource.close();
        chatInput.value = '';
      })
      .catch(error => {
        console.log(error);
      });
  }

  function executeMembers() {
    fetchMethod('/members', 'GET')
      .then(response => response.json())
      .then(members => {
        const message = messageTemplate.content.cloneNode(true);
        const div = message.querySelector('.members');
        members.forEach(member => {
          const elMember = document.createElement('div');
          elMember.appendChild(document.createTextNode(member));
          div.appendChild(elMember);
        });

        appendMessageElement(message);
        chatInput.value = '';
      });
  }

  function executeChat(message) {
    fetchMethod('/chat', 'POST', {
      username: localStorage.getItem('quad.chat.username'),
      message: message
    })
      .then(response => {
        chatInput.value = '';
      });
  }

  function appendMessage(value) {
    const message = messageTemplate.content.cloneNode(true);
    const div = message.querySelector('div');
    div.appendChild(document.createTextNode(value));
    appendMessageElement(message);
  }

  function appendMessageElement(message) {
    messageList.appendChild(message);
  }

  function fetchMethod(path, method, body) {
    const url = window.location.href.endsWith('/')
      ? window.location.href + path.substring(1)
      : window.location.href + path;

    return fetch(url, {
      method: method,
      body: JSON.stringify(body),
      headers: {
        'Content-Type': 'application/json'
      }
    });
  }

  chatInput.addEventListener('keyup', event => {
    event.code === 'Enter' && tryExecutingCommand(chatInput.value);
  });

  executeJoin([
    localStorage.getItem('quad.chat.username'),
    localStorage.getItem('quad.chat.alias')
  ]);
})();
