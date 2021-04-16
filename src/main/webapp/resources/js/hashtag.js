let input = document.querySelector("#hashtags");
let container = document.querySelector("#hashtags-container");


let i = 0;

input.addEventListener('keypress', (event) => {
    if (event.which == 13) {
        event.preventDefault();
        addHashTag(input.value);
    }

});

function addHashTag(hashtag){
    
    if (hashtag.length > 0) {
        let p = document.createElement('p');
        p.classList.add('tag');
        p.id = "hashtag" + i;
        i += 1;
        p.innerText = hashtag;
        container.appendChild(p);
        input.value = '';

        p.addEventListener('click', (event) => {
            let hashtagToBeDeleted = document.querySelector("#" + event.target.id);
            container.removeChild(hashtagToBeDeleted);
        });
    }
}




