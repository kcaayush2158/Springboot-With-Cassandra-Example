$(document).ready(function () {
    $.ajax({
        url: '/api/v1/likes/all',
        method: 'GET',
        type: 'json',
        success: function (data) {
            var responses;
            var obj = JSON.parse(JSON.stringify(data));
            for (var i = 0; i <= obj.length; i++) {
                var results = `
             <div class="row" style="float: left;">   
                <a href= "/user/profile/` + data[i].receivedUser.username + `" id="card-href " style="list-style-type:none;text-decoration:none;" >  
                  <div class="card bg-light  mb-3 mr-5  ml-5 mt-2 "  id="custom-card-design"> 
                     <img class="card-img-top image-thumbnail"   id="card-img" src="` + data[i].receivedUser.profilePhoto + `"  alt="Card image cap"> 
                     <div class="card-body" >
                        <h5 class="card-title "  >` + data[i].receivedUser.firstName + ' ' + data[i].receivedUser.lastName + ' ,' + data[i].receivedUser.aboutMe.age + ` </h5>
                        <p class="card-text" id="card-bio" >` + data[i].receivedUser.aboutMe.bio + `</p>
                    </div>
                    </div>
                </a>
             </div>            
            `;
            }
            responses += results;
            $('#container-users-likes').html(responses);
        }
    });
    $('#').click(function () {
        var formData = $('#')
        $.ajax({
            url: '/api/v1/likes/save',
            method: 'GET',
            type: 'json',
            success: function (data) {
                $('#').html('<div class="alert alert-success" role="alert"><strong>Success<i class="fa fa-check-circle">Y</i> </strong>You liked the profile</div>');
                $.ajax({
                    url: '',
                    method: 'POST',
                    type: 'json',
                    success:function () {

                    }
                });


            }
        });
    });

});
