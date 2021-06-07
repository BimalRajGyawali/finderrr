 const eventSource = new EventSource("http://localhost:8080/subscription");


 eventSource.onmessage = (e) => {
     let data = JSON.parse(e.data);
     if (data.userId == loggedInUserId) {
         let notiBell = document.querySelector("#noti-bell");
         notiBell.style.position = "absolute";
         let notiCount = document.querySelector("#noti-count");
         if (notiCount) {
             notiCount.innerText = `${data.count}`;
         } else {
             notiBell.innerHTML = ` <span id="noti-count"> ${data.count}</span>`;
         }
     }

 }