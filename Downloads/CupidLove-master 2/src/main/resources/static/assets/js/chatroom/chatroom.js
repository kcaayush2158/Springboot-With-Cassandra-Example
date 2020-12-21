$(document).ready(function () {
    $("#form-group-password").hide();
    $('#roomType').on('change', function () {
        if (this.value === 'PRIVATE') {
            $("#form-group-password").show();
        }
        if (this.value === 'PUBLIC') {
            $('#password').val("");
            $("#form-group-password").hide();
        }
    });
});

$(document).ready(function () {
    var createRoom = $('#form-room');

    createRoom.submit(function (e) {
        e.preventDefault();
        $.ajax({
            url: createRoom.attr('action'),
            data: createRoom.serialize(),
            method: 'POST',
            success: function (data) {
                $('#alert').html(`   <div class="alert alert-success" role="alert">  Room is created Successfully </div> `);
            },
            error: function (error) {
                $('#alert').html(`<div class="alert alert-danger" role="alert">Room is already existed</div> `);
            }
        });
    });

    setInterval(function () {
        $.ajax({
            url: '/api/chatroom/my/count',
            dataType: 'text',
            method: 'get',
            success: function (response) {
                $('#my-chatroom').text(response);
            },
        });

        //deletes the room
        const deleteRoom = $('#form-delete');
        deleteRoom.submit(function (e) {
            e.preventDefault();
            $.ajax({
                url: deleteRoom.attr('action'),
                type: deleteRoom.attr('method'),
                data: deleteRoom.serialize(),
                success: function (data) {
                    $('#alert').html(`<div class="alert alert-success" role="alert"> <strong>Success </strong>Room is  deleted</div>`).fadeOut(3000);
                    $('#custom-card-design').hide();
                },
                error: function () {
                    $('#alert').html(`<div class="alert alert-danger" role="alert"> <strong>Error!!!</strong>Unable to delete the room </div> `).fadeOut(3000);
                }
            });
        });
    }, 3000)


    var isActive = true;

    $(document).ready(function () {
        pollAllChatRooms();
        pollShoutOut();
    })
    var pollAllChatRooms = function () {
        if (isActive) {
            window.setTimeout(function () {
                jQuery.ajax({
                    url: '/api/chatrooms/all',
                    dataType: 'json',
                    type: 'get',
                    beforeSend: function () {
                        $('.results').html('<img src="https://thumbs.gfycat.com/AcceptablePerfumedIraniangroundjay-small.gif" height="100" style="margin-left: 50%; margin-top:30%;margin-bottom: 50%" >');
                    },
                    success: function (data) {
                        $('.ajax-loader').css("visibility", "hidden");
                        var obj = JSON.parse(JSON.stringify(data));
                        var responses = "";

                        for (var i = 0; i <= obj.length; i++) {

                            var result = `
                            <div class="col-md-3">
                               <div class="card mb-2 mr-3  ml-2 mt-5 " id="custom-card-design" >
                                    <div class="card-body">
                                         <h5 class="card-title text-center text-uppercase mb-2">` + obj[i].chatRoomName + `</h5>
                                        <p class="mt-4 mb-5" id="chatDescription">` + obj[i].chatRoomDescription + `</p>
                              <!--        <button class="btn btn-light" id="btn-show-info-` + i + `">Show info</button>
                                          <button class="btn btn-light" id="btn-hide-info-` + i + `">Hide info</button
                                        <div class="room-info">
                                            <h6 ><strong>Created By : </strong>` + obj[i].createdBy.username + `</h6>
                                             <h6> <strong>Created Date : </strong>` + obj[i].createdTime + `</h6>
                                             <h6 ><strong>Room Type : </strong>` + obj[i].type + `</h6>
                                        </div>
> -->
                                           `
                            var res;
                            if (obj[i].type === "PUBLIC") {
                                res = `
                                 <form action="/chatroom/public/` + obj[i].chatRoomId + `" method="GET" >
                                      <button class='btn  btn-block mt-4 mb-2' id="btn-join-room-public" type='submit' >
                                       <b> <i class="fa fa-plus-circle"></i> Join Room</b>
                                      </button>
                                  </form>
                            </div>
                          </div>
                            </div>
     `
                            } else {
                                res = `
<button type="button mt-4" class="btn btn-block  mt-3 mb-2"  id="btn-join-room-private" data-toggle="modal" data-target="#private-room" >
<b><i class="fa fa-plus-circle"></i> Join Room</b>
</button>
<!-- Modal -->
<div class="modal fade" id="private-room" tabindex="-1" role="dialog" aria-labelledby="private-roomTitle" aria-hidden="true">
   <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
         <div class="modal-header">
            <h5 class="modal-title" id="private-roomTitle">Private Room</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
            </button>
         </div>
         <div class="modal-body">
            <form action="/chatroom/private/` + obj[i].chatRoomId + `" method="post">
               <div class="form-group">
                  <label for="password-chatroom">Password :</label>
                  <input type="password" class="form-control" id="password-chatroom" name="password-chatroom"/>
                  <button type="submit" class="btn btn-block btn-secondary mt-5" >Save changes</button>
               </div>
            </form>
         </div>
      </div>
   </div>
</div>
</div>
</div>
</div>
                                      `
                            }
                            ``;


                            responses += result.concat(res);
                            $('.results').html(responses);
                            $('#broadcast').text(data.length);
                        }
                    },
                    complete: pollAllChatRooms,
                    error: function () {
                        pollAllChatRooms();
                    }
                });
            }, 2500);

        }
    };


    // used for the searching the chatroom
    $('#search_box').keyup(function () {
        var search_term = $('#search_box').val();

        $.ajax({
            url: '/api/chatroom/search/' + search_term,
            dataType: 'text',
            type: 'get',
            async: false,
            beforeSend: function () {
                $('.results').html("<img src=\"https://miro.medium.com/max/1600/0*ptDX0HfJCYpo9Pcs.gif\"  class=\"ml-5 mt-5 mb-5 offset-md-5\" height=\"150\" alt=\"loading....\" class='offset-md-6 mt-5' />");
            },
            success: function (response) {
                if (search_term.length > 0) {
                    for (var i = 1; i <= search_term.length; i++) {
                        $('.results').html(response);
                    }
                } else {
                    pollAllChatRooms();
                }
            },
            error: function () {
                $('.results').html("<H1 class='text-center mt-5 offset-md-5'>Rooms Not Available</H1>")
            }
        });

    });

    //shoutout text
    var shoutOutForm = $('#form-shout-out');

    shoutOutForm.submit(function (e) {
        e.preventDefault();
        //gets the tez
        $.ajax({
            url: shoutOutForm.attr("action"),
            type: shoutOutForm.attr("method"),
            data: shoutOutForm.serialize(),
            success: function (data) {
                console.log("success")
            },
            fail: function (error) {
                console.log('fail')
            }
        });

    })


    var pollShoutOut = function () {
        if (isActive) {
            window.setTimeout(function () {
                $.ajax({
                    url: '/api/chatrooms/shoutout/all',
                    type: 'get',
                    data_type: 'json',
                    success: function (data) {
                        var obj = JSON.parse(JSON.stringify(data));
                        var results = '';
                        for (var i = 0; i <= obj.length; i++) {
                            template = '<li> <div class="message"> <div class="row"> <img style="object-fit: cover; height: 50px ;width: 50px; " src="' + data[i].user.profilePhoto + ' "> <b ><h6 style="font-size: 16px;color: #004a95;" class="ml-2">' + data[i].user.username + ' </h6><p class="ml-2" style="width: 20px;">  ' + data[i].message + ' </p></b></div></div></li>'
                            results += template;
                            $('#shoutout-message').html(results);

                        }
                    },
                    complete: pollShoutOut,
                    error: function () {
                        pollShoutOut();
                    }
                })
            }, 2500);
        }


    }
    // }, 2000);


    const message = $('#message');
    if (message.val().length >= 10) {
        message.addClass('is-valid');
        $('#shoutout-message-response').text('maximum  character limited');
    }


});
