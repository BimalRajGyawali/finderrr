                function follow(event) {
                    event.preventDefault();
                    let hashtag = event.target.id;
                    let followError = document.getElementById("follow-error");
                    if (!hashtag) {
                        displayError(followError);

                    } else {
                        if (event.target.innerText == "Follow") {
                            postAjax('/follow', { "hashtag": hashtag })
                                .then(data => {
                                    if (data === true) {
                                        event.target.innerText = "Unfollow";
                                        event.target.style.border = "1px solid green";
                                    } else {
                                        displayError(followError);
                                    }
                                })
                                .catch(err => displayError(followError));


                        } else {
                            postAjax('/unfollow', { "hashtag": hashtag })
                                .then(data => {
                                    if (data === true) {
                                        event.target.innerText = "Follow";
                                        event.target.style.border = "1px solid blue";
                                    } else {
                                        displayError(followError);
                                    }
                                })
                                .catch(err => displayError(followError));
                        }

                    }



                }


