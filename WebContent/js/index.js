$(function () {
    //取消清除输入框内容
    $('.modal .cancel').click(function () {
        $(this).parents('.modal').find('form').get(0).reset();
    });
    //学生登录模块
    $('#student_login .confirm').click(function () {
        $.ajax({
            type: "post",
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
            type: "post",
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
    //课程列表获取模块
    /*接口注释:
     * url:课程点击后的链接
     * courseID:获取课程图片(存储在本地)
     * courseName:课程名
     * courseDescription:课程描述
     */
    $('#course_link').click(function () {
        $.ajax({
            type: "post",
            url: "../course?operate=getCourses",
            data: {
                //teacherID:localStorage.currentUser
                teacherID: $.cookie('currentUser')
            },
            success: function (data, statusText) {
                var html = '';
                var jsonData = $.parseJSON(data).courses;
                $.each(jsonData, function (index, value) {
                    console.log(jsonData[index].courseID + '-' + jsonData[index].courseName + '-' + jsonData[index].courseDescription);
                    html += "<li class='col-md-4 col-sm-6 col-xs-12'><div class='course-item'><div class='course-item-cover'><a href='#'><img src='../images/" + jsonData[index].courseID + ".png' class='img-responsive' /></a></div><div class='course-item-desc'><div class='course-item-desc-title'><a href='#'><h3>" + jsonData[index].courseName + "</h3></a></div><div class='course-item-desc-text'><p>" + jsonData[index].courseDescription + "</p></div></div></div></li>";
                })
                $('.course .row').empty().append(html);
            }
        });
    });
    $('#course_link').trigger('click');
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