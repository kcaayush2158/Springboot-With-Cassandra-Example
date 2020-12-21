$(document).ready(function () {
    onlineUsersfunctions();

    var limit = 8;
    var start = 0;
    var action = 'inactive';

    if (action === 'inactive') {
        action = 'active';
        loadUserProfile(limit, start);
    }


    $(window).scroll(function () {
        if ($(window).scrollTop() + $(window).height() > $(".users").height() && action === 'inactive') {
            action = 'active';
          

            setTimeout(function () {
                loadUserProfile(limit, start);

            }, 1000);
        }
    });


    function loadUserProfile(limit, start) {

        jQuery.ajax({
            url: '/api/users/profile',
            dataType: 'text',
            method: 'get',
            cache: false,
            data: {
                limit: limit,
                start: start
            },
            beforeSend: function () {
                $('.ajax-loader').css("visibility", "visible");
                $('#shimmer-photo').show();
            },
            success: function (data) {
                var skeleton = "";
                $('#shimmer-photo').hide();
                $('.ajax-loader').css("visibility", "hidden");
                $('.users').append(data);

                if (data === '') {
                    $('#load_data_message').html("<div class='container mt-5 mb-5 text-center ' > <h5>No Data Found</h5><div>");
                    action = 'active';
                } else {
                    skeleton = `<div class="row">
   <div class="card bg-light mb-3 mr-5  ml-5 mt-2 "  style="width: 16rem;">
      <photo class="shine card-img-top" id="shimmer-photo" style="height:50px;"></photo>
      <div class="card-body " >
         <h5 class="card-title mb-4">
            <photo class="shine" id="shimmer-photo" style="width:50%;background-color:#afafaf; " ></photo>
         </h5>
         <p class="card-text">
            <photo class="shine" id="shimmer-photo"  ></photo>
            <photo class="shine" id="shimmer-photo"></photo>
            <photo class="shine" id="shimmer-photo" ></photo>
         </p>
      </div>
   </div>
   <div class="card bg-light mb-3 mr-5  ml-5 mt-2 "  style="width: 16rem;">
      <photo class="shine card-img-top" id="shimmer-photo" style="height:50px;border-radius:0px;"></photo>
      <div class="card-body " >
         <h5 class="card-title mb-4">
            <photo class="shine" id="shimmer-photo" style="width:50%;background-color:#afafaf; " ></photo>
         </h5>
         <p class="card-text">
            <photo class="shine" id="shimmer-photo"  ></photo>
            <photo class="shine" id="shimmer-photo"></photo>
            <photo class="shine" id="shimmer-photo" ></photo>
         </p>
      </div>
   </div>
   <div class="card bg-light mb-3 mr-5  ml-5 mt-2 "  style="width: 16rem;">
      <photo class="shine card-img-top" id="shimmer-photo" style="height:50px;border-radius:0px;"></photo>
      <div class="card-body " >
         <h5 class="card-title mb-4">
            <photo class="shine" id="shimmer-photo" style="width:50%;background-color:#afafaf; " ></photo>
         </h5>
         <p class="card-text">
            <photo class="shine" id="shimmer-photo"  ></photo>
            <photo class="shine" id="shimmer-photo"></photo>
            <photo class="shine" id="shimmer-photo" ></photo>
         </p>
      </div>
   </div>
   <div class="card bg-light mb-3 mr-5  ml-5 mt-2 "  style="width: 16rem;">
      <photo class="shine card-img-top" id="shimmer-photo" style="height:50px;border-radius:0px;"></photo>
      <div class="card-body " >
         <h5 class="card-title mb-4">
            <photo class="shine" id="shimmer-photo" style="width:50%;background-color:#afafaf; " ></photo>
         </h5>
         <p class="card-text">
            <photo class="shine" id="shimmer-photo"  ></photo>
            <photo class="shine" id="shimmer-photo"></photo>
            <photo class="shine" id="shimmer-photo" ></photo>
         </p>
      </div>
   </div>
</div> `
                    $('#load_data_message').html(skeleton);
                    action = 'inactive';
                }

            },
            complete: function (data) {
                $('.ajax-loader').css("visibility", "hidden");
            }
        });
    };

});


//count the online users
var onlineUsersfunctions = function () {
    if (isActive) {
        window.setTimeout(function () {
            $.ajax({
                url: '/api/count/users/online',
                method: 'get',
                type: 'text',
                success: function (data) {
                    $('#online-users').text(data);
                }, complete: onlineUsersfunctions(),
                error: function () {
                    onlineUsersfunctions();
                }
            });
        }, 1000);
    }
}
//count the notification  users

setInterval(function () {
    jQuery.ajax({
        url: '/api/count/notification',
        method: 'get',
        type: 'text',
        success: function (data) {
            if (data === 0) {
                $('#notification-number').text('');
            } else {
                var notificationNumber = $('#notification-number');

                notificationNumber.text(' ' + data);
                setTimeout(function () {
                    notificationNumber.text(' ' + data);
                }, 50000)
                $('#notification-button').removeClass('btn-light').addClass('btn-danger');

            }
        },
        error: function (error) {
            console.log(error);
        }
    });
}, 2000);
var isActive = true;
$.ajax({
    url: 'http://localhost:8080/api/u/online',
    method: 'GET',
    success: function (data) {
        console.log('online users success ');
        var obj = JSON.parse(JSON.stringify(data));
        for (var i = 0; i < obj.length; i++) {
            console.log('online users ');
            $.ajax({
                url: 'http://localhost:8080/api/u/' + data[i].username + '/online/get',
                method: 'GET',
                type:'JSON',
                success: function (dataResponse) {
                    console.log(dataResponse);
                    var usersObject = JSON.parse(JSON.stringify(dataResponse));
                    var responses = '';
                    for (var j = 0; j <=usersObject.length; j++) {
                        console.log('online users fetched');
                        var results = `      
                         <div class="card bg-light mb-3 mr-5  ml-5 mt-2 "  id="custom-card-design" style="width: 16rem;">
                          <img class="card-img-top" src="` + dataResponse[j].profilePhoto + `" alt="Card image cap">
                           <div class="card-body">
                              <h5 class="card-title mb-4">` + dataResponse[j].username + `</h5>
                              <h5 class="card-title mb-4">` + dataResponse[j].aboutMe.bio + `</h5>
                            </div>
                          </div>`;
                        responses += results;
                        console.log(responses);
                    }
                    $('#online-users-profile').html(responses);
                }

            });
        }
    }
});
// $(window).scroll(function () {
//     let start = 0;
//     let limit = 2;
//     onlineUsers(limit, start);
//     if ($(window).scrollTop() + $(window).height() > $("#user-online").height() && action === 'inactive') {
//         action = 'active';
//
//         start = start + limit;
//         setTimeout(function () {
//             onlineUsers(limit, start);
//
//         }, 1000);
//     }
// });
