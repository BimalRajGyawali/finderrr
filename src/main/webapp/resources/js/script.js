

function commentPost(event, commentContainer) {
	
	var parentNode = document.getElementById(commentContainer);
	if (event.keyCode === 13 && !event.shiftKey) {
		event.preventDefault();
		
		var commentText = event.currentTarget.innerHTML;
		

		if (commentText == "") {
			event.currentTarget.innerHTML = "";
			return;
		}


		event.preventDefault();
		var commentdiv = document.createElement('div');

		var commentdetailsdiv = document.createElement('div');
		commentdetailsdiv.className = "comment-details";

		var accountname = document.createElement('p');
		var commentdata = document.createElement('p');
		commentdata.className = "comment-data";
		commentdata.innerHTML = commentText;


		accountname.innerHTML = "Ram Adhikari";
		accountname.className = "account-name";
		commentdetailsdiv.appendChild(accountname);
		commentdetailsdiv.appendChild(commentdata);

		var commentprofilepic = document.createElement('img');
		commentprofilepic.src = "resources/images/pic.jpeg";
		commentprofilepic.alt = "Card image cap";
		commentprofilepic.className = "comment-profile-pic";
		commentdiv.appendChild(commentprofilepic);

		commentdiv.appendChild(commentdetailsdiv);

		commentdiv.className = 'comment';

		parentNode.insertBefore(commentdiv, parentNode.firstChild);
		event.currentTarget.innerHTML = "";
		event.currentTarget.blur();




	}
}

