$(function () {
    register();
    login();
    $('#logout').click(logout);
});

//老师注册模块
/*注册之后将自动登陆
 * */
function register(){
    $('#teacher_reg .register_button').click(function () {
        $('#teacher_reg form').submit();
    });
    $('#teacher_reg form').validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                type: "POST",
                url: "../login?operate=signIn",
                success: function (data, statusText) {
                    var jsondata = $.parseJSON(data);
                    if (jsondata.status == 1) {
                        alert('注册成功！');
                    } else {
                        alert('注册失败，请重试！');
                    }
                },
                error: function (jqXHR,textStatus,errorThrown) {
                    alert('发生错误，错误码：'+jqXHR.status+",参考错误："+errorThrown);
                }
            });
        },
        rules: {
            username: {
                required: true,
            },
            password: {
                required: true,
            },
            school:{
                required: true,
            },
            college:{
                required: true,
            }
        },
        messages: {
            username: {
                required: '请填写你的用户名！',
            },
            password: {
                required: '密码不得为空！',
            },
            school:{
                required: '请填写你的学校',
            },
            college:{
                required: '请填写你所属的学院',
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

//老师登录模块
/*登录之后将账户名记录进cookie
 * */
function login(){
    $('#teacher_login .confirm').click(function () {
        $('#teacher_login form').submit();
    });
    $('#teacher_login form').validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                type: "POST",
                url: "../login?operate=teacherLogin",
                success: function (data, statusText) {
                    var jsondata = $.parseJSON(data);
                    if (jsondata.status == 1) {
                        $.cookie('current_teacher', $('#teacher_num').val(), {expires: 7})
                        window.location.href = "courseList_teacher.html";
                    } else {
                        alert('登录失败，请重试');
                    }
                },
                error: function (jqXHR,textStatus,errorThrown) {
                    alert('发生错误，错误码：'+jqXHR.status+",参考错误："+errorThrown);
                }
            });
        },
        rules: {
            username: {
                required: true,
            },
            password: {
                required: true,
            }
        },
        messages: {
            username: {
                required: '请填写你的用户名！',
            },
            password: {
                required: '密码不得为空！',
            }
        },
        highlight: function (element, errorClass) {
            $(element).css('border', '1px solid red');
        },
        unhighlight: function (element, errorClass) {
            $(element).css('border', '1px solid #ccc');
        }
    });

    //学生登录模块
    $('#student_login .confirm').click(function () {
        $('#student_login form').submit();
    });
    $('#student_login form').validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                type: "POST",
                url: "../login?operate=studentLogin",
                success: function (data, statusText) {
                    var jsondata = $.parseJSON(data);
                    if (jsondata.status == 1) {
                        $.cookie('current_student', $('#student_num').val(), {expires: 7})
                        window.location.href = "courseList_student.html";
                    } else {
                        alert('登录失败，请重试');
                    }
                },
                error: function (jqXHR,textStatus,errorThrown) {
                    alert('发生错误，错误码：'+jqXHR.status+",参考错误："+errorThrown);
                }
            });
        },
        rules: {
            username: {
                required: true,
                number: true,
            },
            password: {
                required: true,
            }
        },
        messages: {
            username: {
                required: '请填写你的学号！',
            },
            password: {
                required: '密码不能为空！',
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

//注销登录
/*点击注销按钮，清除cookies*/
function logout(){
    $.cookie('current_teacher')?$.removeCookie('current_teacher'):$.removeCookie('current_student');
    //TODO 清除其他cookies
    window.location.href = '../login?operate=logout';
}

//登陆状态检测
/*检测参数cookie：current_teacher|current_student
* 返回：{登陆：true,未登陆：false}*/
function isLogin(){
    if($.cookie('current_teacher')||$.cookie('current_student')){
        return true;
    }else{
        return false;
    }
}