function searchHashtag(event) {
    if (event.keyCode == 13) {
        let hashtagInput = document.querySelector("#hashtagInput");

        if (hashtagInput.value == "") {
            hashtagInput.placeholder = "Please type hashtag ";
            return false;
        }
        window.location = "/posts/hashtag/" + hashtagInput.value;
    }
}