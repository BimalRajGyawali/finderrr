function searchHashtag(event) {
    if (event.keyCode == 13) {
        let hashtagInput = document.querySelector("#hashtagInput");
        let input = hashtagInput.value;
        if (input == "") {
            hashtagInput.placeholder = "Hashtag empty ";
            return false;
        }
        let hashtagParts = input.split("#");
        if (hashtagParts.length == 0) {
            hashtagInput.value = "";
            hashtagInput.placeholder = "Don't inclue #  ";
            return false;
        }
        let hashtag = hashtagParts[hashtagParts.length - 1];
        if (!hashtag) {
            hashtagInput.value = "";
            hashtagInput.placeholder = "Don't inclue #  ";
            return false;
        }
        window.location = "/posts/hashtag/" + hashtag;

    }

}