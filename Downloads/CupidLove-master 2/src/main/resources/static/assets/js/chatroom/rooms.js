
//create the public and private rooms
var form = $('#form-room');
form.submit(function (e) {
    e.preventDefault();
    $.ajax({
        url: form.attr('action'),
        type: form.attr('method'),
        data: form.serialize(),
        method: 'post',
        success: function () {
            var response = `
                      <div class="alert alert-danger" role="alert"  data-dismiss="alert">
                         <strong>Success</strong> <i class="fa fa-times-circle"></i> <i class="fa fa-check-circle"></i> Room has been created Successfully
                      </div>
                  `;
            $("#response-message").html(response);
        },
        error: function (data) {
            var response = `
                      <div class="alert alert-danger" role="alert"  data-dismiss="alert">
                         <strong>Failed</strong> <i class="fa fa-times-circle"></i> Room is already existed
                      </div>
                  `;
            $("#response-message").html(response);
        }
    });
});


$(document).ready(function () {
    pollPublicRoooms();
})
    var isActive=true;
    var roomId = $('#roomId').val();

    //used to poll the public rooms
    var pollPublicRoooms = function(){
    setTimeout(function () {
        if(isActive){
            window.setTimeout(function () {
                $.ajax({
                    url: '/api/chatroom/public/'+roomId+'/all',
                    dataType: 'json',
                    type: 'json',
                    method: 'get',

                    success: function(data){
                        var obj = JSON.parse(JSON.stringify(data));
                        var response = '';

                        for(var i =0 ;i <= obj.length;i++ ){
                            var  result = `
                        <div class="messaging-section mb-2 mt-2">
                             <div class="row">
                                  <div class="message-header" >
                                       <img src="`+data[i].sender.profilePhoto+`"  style="width:50px;height:50px;object-fit: cover;">
                                    </div>
                              <div class="message-body ml-2" id="message-body-receiver">
                                    <p> `+data[i].sender.firstName +' : '+data[i].message+`</p>
                           </div>
                             <!--<div th:style="|background:url('\`+data[i].sender.profilePhoto+\`');height:50px;" > </div>  <p> \`+data[i].message+\`</p>-->
                            </div>
                         </div>
                        `;
                            response += result;
                            $('.messaging-body').html(response);
                        }
                    },complete:pollPublicRoooms,
                    error:function () {
                        pollPublicRoooms();
                    }
                });
            });
        }


    });
}



const formMessage = $('#form-message');
formMessage.submit(function (e) {
    e.preventDefault();
    $.ajax({
        url:formMessage.attr('action'),
        type: formMessage.attr('method'),
        data: formMessage.serialize(),
        beforeSend:function(){
                $('#message-response').text("sending..").css('color','purple').fadeIn(100);
        }, success:function () {
            $('#messages').val('');

                $('#message-response').text("message sent..").css('color','green').fadeOut(2000);
        },done:function () {
            $('#message-response').text('')
        },fail:function () {
            $('#message-response').text("message unable to send..").css('color','red').fadeOut(2000);
        }
    });

    //
    // setInterval(function () {
    //     $.ajax({
    //         url:'/api/chatroom/public/'+roomId,
    //         dataType: 'json',
    //         type: 'get',
    //         success:function (data) {
    //             console.log(data);
    //             var obj = JSON.stringify(JSON.parse(data));
    //             for(var i= 1;i<=obj.length;i++){
    //                 $('#messaging-body').html("<h6>"+data[i].user.username+"</h6> : "+data[i].messages);
    //             }
    //             alert("hello");
    //         }
    //     })
    // },1000)

});

