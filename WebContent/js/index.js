$(function () {
    // 清除输入框内容
    $('.modal').on('hidden.bs.modal', function () {
        $(this).find('form').get(0).reset();
    });
    //学生登录模块
    $('#student_login .confirm').click(function () {
        $.ajax({
            type: "POST",
            url: "",
            data: {
                student_num: $('#student_num').val(),
                student_pass: $('#student_pass').val()
            },
            success: function (data, statusText) {
                alert(data);
            }
        });
    });
    //老师登录模块
    $('#teacher_login_form .confirm').click(function () {
        $.ajax({
            type: "POST",
            url: "../login?operate=teacherLogin",
            data: {
                username: $('#teacher_num').val(),
                password: $('#teacher_pass').val()
            },
            success: function (data, statusText) {
                var jsondata = $.parseJSON(data);
                if (jsondata.status == 1) {
                    //localStorage.currentUser=$('#teacher_num').val();
                    $.cookie('currentUser', $('#teacher_num').val(), {expires: 7})
                    window.location.href = "course-list.html";
                } else {
                    alert('登录失败，请重试');
                }
            },
        });
    });
    // 监测登录状态模块
    /*如果登录了进行后续操作，如果没有登录，跳转到登陆界面进行登录*/
    $.cookie('currentUser', '2013211729', {expires: 7})// TODO 测试数据，记得删除
    if ($.cookie('currentUser')) {
        $('#current_center').html($.cookie('currentUser') + '<span class="caret"></span>');
        addPlan();//TODO 考虑是否在这里执行
    } else if ($('#current_center').length > 0) {
        $('#current_center').html('登录');
        alert('即将跳转到登陆页面，请选择进行登录!');
        window.location.href = 'index.html';
    }
    //注销登录
    /*点击注销按钮，清除cookies*/
    $('#logout').click(function () {
        $.removeCookie('currentUser');
        window.location.href = 'index.html';
    });
    //添加课程进度模块
    /*字段合法性监测，通过点击确定允许提交*/
    function addPlan() {
        $('#addPlan .confirm').click(function () {
            alert('click')
            $('#addPlan').find('form').submit();
        });
        $('#addPlan').find('form').validate({
            submitHandler: function (form) {
                $(form).ajaxSubmit({
                    type: 'POST',
                    url: '123.php',
                    data: {
                        courseID: '1'//TODO 模拟值，待修改
                    },
                    success: function (data, statusText) {
                        if (data == true) {
                            $(this).resetForm();
                            alert('添加成功');
                        }
                        alert(statusText);
                    }
                });
            },
            rules: {
                plan_time: {
                    required: true,
                },
                plan_text: {
                    required: true,
                }
            },
            messages: {
                plan_time: {
                    required: '亲，你没有填写时间哟！',
                },
                plan_text: {
                    required: '亲，你没有填写详细进度描述哟！',
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



    /*作业布置截至时间选框*/
    $('#homeworkStopTime').datetimepicker({
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        language: 'zh-CN'
    });
    //教学计划发布模块
    $('#addPlan .confirm').click(function () {
        $.ajax({
            type: "post",
            url: "",
            dataType: 'json',
            data: {
                stopTime: $('#plan_time').val(),
                planText: $('#plan_text').val()
            }
        });
    });

});