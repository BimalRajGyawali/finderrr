<nav class="navbar navbar-light bg-light justify-content-between">
    <a  href="/" class="navbar-brand"> <strong>Finder</strong> </a>
    <form class="form-inline" method="POST" action="#" onsubmit="handleSubmit(event);" >
      <input class="form-control mr-sm-2" type="search" placeholder="Search Hashtags" aria-label="Search" id="hashtagInput">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
  </nav>

  <script>
      function handleSubmit(event){
         
        event.preventDefault();
        let hashtagInput = document.querySelector("#hashtagInput");
      
        if(hashtagInput.value == ""){
            hashtagInput.placeholder = "Please type hashtag ";
            return false;
        }
        window.location = "/posts/hashtag/"+hashtagInput.value;
      }
  </script>