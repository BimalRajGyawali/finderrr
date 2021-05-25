function handleSubmit(event){
         
    event.preventDefault();
    let hashtagInput = document.querySelector("#hashtagInput");
  
    if(hashtagInput.value == ""){
        hashtagInput.placeholder = "Please type hashtag ";
        return false;
    }
    window.location = "/posts/hashtag/"+hashtagInput.value;
  }