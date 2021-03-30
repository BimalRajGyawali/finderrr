

function commentPost(event) {
	
	
	if (event.keyCode === 13 && !event.shiftKey) {
		
		var form_input = document.getElementById("form_input");
		
		event.preventDefault();
		
		
		var commentText = event.currentTarget.innerHTML;
		if (commentText == "") {
			event.currentTarget.innerHTML = "";
			return;
		}
		
		form_input.value=commentText;
        document.getElementById('submit_button').click();



	}
}

