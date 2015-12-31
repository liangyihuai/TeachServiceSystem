$(function () {
    getInformation();
});

//判断当前用户登录类型，得到用户信息
function getInformation(){
    if($.cookie('current_teacher')){
        $.ajax({
            type:'GET',
            url:'../teacher?operate=info',
            success:function(data,statusText){
                var jsonData= $.parseJSON(data)
                var html='<div class="account_info">用户名：<span class="account_info_item" id="accout-teacherNumber">'+jsonData.username+'</span></div><div class="account_info">性别：<span class="account_info_item" id="accout-teacherSex">'+jsonData.sex+'</span></div> <div class="account_info">学校：<span class="account_info_item" id="accout-teacherSchool">'+jsonData.school+'</span></div> <div class="account_info">学院：<span class="account_info_item" id="accout-teacherAcademy">'+jsonData.college+'</span></div>';
                $('#information_box').append(html);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('发生错误，错误码：' + jqXHR.status + ",参考错误：" + errorThrown);
            }
        });
        changePass('../teacher?operate=changePass');
    }else if($.cookie('current_student')){
        $.ajax({
            type:'GET',
            url:'../student?operate=info',
            success:function(data,statusText){
                var jsonData= $.parseJSON(data)
                var html='<div class="account_info">学号：<span class="account_info_item" id="accout-studentNumber">'+jsonData.studentNO+'</span> </div> <div class="account_info">姓名：<span class="account_info_item" id="accout-studentName">'+jsonData.username+'</span></div> <div class="account_info">性别：<span class="account_info_item" id="accout-studentSex">'+jsonData.sex+'</span></div><div class="account_info">学院：<span class="account_info_item" id="accout-studentdepartment">'+jsonData.college+'</span></div><div class="account_info">班级：<span class="account_info_item" id="accout-clazz">'+jsonData.clazz+'</span></div>'
                $('#information_box').append(html);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('发生错误，错误码：' + jqXHR.status + ",参考错误：" + errorThrown);
            }
        });
        changePass('../student?operate=changePass');
    }
}

//修改密码
function changePass(url){
    $('.changePassBtn').click(function () {
        $(this).next().slideToggle(300, "easeInOutBack");
    });
    $('.tip form').validate({
        submitHandler: function (form) {
            var oldPass = $(form).find('input').eq(0).val();
            var newPass = $(form).find('input').eq(1).val();
            $(form).ajaxSubmit({
                type: 'POST',
                url: url,
                data:{
                    oldPass:oldPass,
                    newPass:newPass
                },
                success: function (data, statusText) {
                    if (data == 1) {
                        alert('修改成功，请重新登陆！');
                        logout();
                    } else {
                        alert('修改失败，请稍后重试！');
                    }
                },
                error: function (jqXHR,textStatus,errorThrown) {
                    alert('发生错误，错误码：'+jqXHR.status+",参考错误："+errorThrown);
                }
            });
        },
        rules: {
            oldPass: {
                required: true,
            },
            newPass: {
                required: true,
            },
            newPass2:{
                required: true,
                equalTo:"#newPass"
            }
        },
        messages: {
            oldPass: {
                required: '',
            },
            newPass: {
                required: '',
            },
            newPass2:{
                required: '',
                equalTo:''
            }
        },
        highlight: function (element, errorClass) {
            $(element).css('border', '1px solid red');
        },
        unhighlight: function (element, errorClass) {
            $(element).css('border', '1px solid green');
        }
    });
}