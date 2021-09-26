 const eventSource = new EventSource("http://localhost:8080/subscription");

 eventSource.onmessage = (e) => {
     let data = JSON.parse(e.data);
     console.log("Data ", data);
     console.log("Logged in "+loggedInUserId);
     if (data.userId == loggedInUserId) {
         console.log("matched");
         let notiBell = document.querySelector("#noti-bell");
         notiBell.style.position = "absolute";
         let notiCount = document.querySelector("#noti-count");
         if (notiCount) {
             notiCount.innerText = `${data.count}`;
         } else {
             let span = document.createElement("span");
             span.id = "noti-count";
             span.innerText = `${data.count}`;
             notiBell.parentNode.appendChild(span);
         }
     }

 }