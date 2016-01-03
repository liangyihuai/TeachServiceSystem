$(function () {
    getInformation();
    updateInfo();
});

//判断当前用户登录类型，得到用户信息
function getInformation(){
    if($.cookie('current_teacher')){
        var teacherNumber=$('#teacherNumber');
        var teacherSex_option=$('#teacherSex option');
        var teacherSchool=$('#teacherSchool');
        var teacherAcademy=$('#teacherAcademy');
        $.ajax({
            type:'GET',
            url:'../teacher?operate=info',
            success:function(data,statusText){
                var jsonData= $.parseJSON(data);
                teacherNumber.val(jsonData.username);
                teacherSchool.val(jsonData.school);
                teacherAcademy.val(jsonData.college);
                if(jsonData.sex.toString() == '男'){
                    teacherSex_option[0].selected=true;
                }else{
                    teacherSex_option[1].selected=true;
                }
                $('.updateInfoBtn').css('display','inline-block');
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
                $('#information_box').empty().append(html);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert('发生错误，错误码：' + jqXHR.status + ",参考错误：" + errorThrown);
            }
        });
        changePass('../student?operate=changePass');
    }
}

//更新个人信息
function updateInfo(){
    var $updateInfoBtn=$('.updateInfoBtn');
    var $infoForm=$('#information_box form');
    $updateInfoBtn.click(function(){
        $infoForm.submit();
    })
    $infoForm.validate({
        submitHandler:function(form){
            $(form).ajaxSubmit({
                type: 'POST',
                url: '../teacher?operate=changeInfo',
                success: function (data, statusText) {
                    if (data == 1) {
                        alert('更新个人信息成功');
                        getInformation()
                    } else {
                        alert(statusText);
                    }
                }
            });
        },
        rules:{
            teacherNumber:{
                required:true,
                number:true
            },
            teacherSchool:{
                required:true,
            },
            teacherAcademy:{
                required:true,
            }
        },
        messages:{
            teacherNumber:{
                required:'用户名不得为空',
                number:'用户名只能为数字'
            },
            teacherSchool:{
                required:'学校不得为空',
            },
            teacherAcademy:{
                required:'学院不得为空',
            }
        },
        highlight: function (element, errorClass) {
            $(element).css('border', '1px solid red');
        },
        unhighlight: function (element, errorClass) {
            $(element).css('border', '1px solid #ccc');
        }
    });
}

//修改密码
function changePass(url){
    $('.changePassBtn').click(function () {
        $('.tip').slideToggle(300, "easeInOutBack");
    });
    $('.tip form').validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                type: 'POST',
                url: url,
                success: function (data, statusText) {
                    if (data == 1) {
                        alert('修改成功，请重新登陆！');
                        logout();
                    } else if(data==0) {
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