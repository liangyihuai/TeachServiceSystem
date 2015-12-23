/**
 * Created by dust on 2015/12/19.
 */
$(function () {
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
    //老师登录模块
    /*登录之后将账户名记录进cookie
    * */
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
});