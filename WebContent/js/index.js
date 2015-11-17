$(function () {
    // 清除输入框内容
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
    // 监测登录状态模块
    /*如果登录了进行后续操作，如果没有登录，跳转到登陆界面进行登录*/
    //$.cookie('currentUser','2013211729',{expires:7})// TODO 测试数据，记得删除
    if($.cookie('currentUser')){
        $('#current_center').html($.cookie('currentUser')+'<span class="caret"></span>');
        getCourseList();
    }else if($('#current_center').length>0){
        $('#current_center').html('登录');
        alert('即将跳转到登陆页面，请选择进行登录!');
        window.location.href='index.html';
    }
    //注销登录
    /*点击注销按钮，清除cookies*/
    $('#logout').click(function(){
        $.removeCookie('currentUser');
        window.location.href='index.html';
    });
    //课程列表获取模块
    /*接口注释:
     * url:课程点击后的链接
     * courseID:获取课程图片(存储在本地)
     * courseName:课程名
     * courseDescription:课程描述
     */
    function getCourseList(){
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