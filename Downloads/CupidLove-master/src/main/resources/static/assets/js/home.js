$(document).ready(function() {

    var limit = 8;
    var start = 0;
    var action = 'inactive';

    if (action === 'inactive') {
        action = 'active';
        loadUserProfile(limit, start);
    }


    $(window).scroll(function() {
        if ($(window).scrollTop() + $(window).height() > $(".users").height() && action === 'inactive') {
            action = 'active';
            start = start + limit;
            setTimeout(function() {
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
            beforeSend: function(data) {

                $('.ajax-loader').css("visibility", "visible");

                $('#shimmer-photo').show();
            },
            success: function(data) {
                var skeleton = "";
                $('#shimmer-photo').hide();
                $('.ajax-loader').css("visibility", "hidden");
                $('.users').append(data);

                if (data === '') {
                    $('#load_data_message').html("<div class='container mt-5 mb-5 text-center ' > <h5>No Data Found</h5><div>");
                    action = 'active';
                } else {
                    skeleton = `<div class="row">
   <div class="card bg-light mb-3 mr-5  ml-5 mt-2" data-aos="zoom-in" style="width: 16rem;">
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
   <div class="card bg-light mb-3 mr-5  ml-5 mt-2 " data-aos="zoom-in" style="width: 16rem;">
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
   <div class="card bg-light mb-3 mr-5  ml-5 mt-2 " data-aos="zoom-in" style="width: 16rem;">
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
   <div class="card bg-light mb-3 mr-5  ml-5 mt-2 " data-aos="zoom-in" style="width: 16rem;">
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
            complete: function(data) {
                $('.ajax-loader').css("visibility", "hidden");
            },
            fail:function (error) {
                console.log('no internet ');
            }
        });
    };

});


//count the online users
setInterval(function() {
    $.ajax({
        url: '/api/count/users/online',
        method: 'get',
        type: 'text',
        success: function(data) {
            $('#online-users').text(data);
        }
    });
}, 10000);


//count the notification  users

setInterval(function() {
    jQuery.ajax({
        url: '/api/count/notification',
        method: 'get',
        type: 'text',
        success: function(data) {
            if (data === 0) {
                $('#notification-number').text('');
            } else {
                var notificationNumber = $('#notification-number');

                notificationNumber.text(' ' + data);
                setTimeout(function() {
                    notificationNumber.text(' ' + data);
                }, 50000)
                $('#notification-button').removeClass('btn-light').addClass('btn-danger');

            }
        },
        error: function(error) {
            console.log(error);
        }
    });
}, 2000);