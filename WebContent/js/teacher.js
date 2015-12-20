$(function () {
    //注销登录
    /*点击注销按钮，清除cookies*/
    $('#logout').click(function () {
        $.removeCookie('currentUser');
        window.location.href = '../login?operate=logout';
    });
    initCurrentPage();//初始化留言板
    /*作业布置截至时间选框*/
    $('#homeworkStopTime').datetimepicker({
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        language: 'zh-CN'
    });
    // 监测登录状态模块
    /*如果登录了进行后续操作，如果没有登录，跳转到登陆界面进行登录*/
    if ($.cookie('current_teacher')) {
        $('#current_center').html($.cookie('current_teacher') + '<span class="caret"></span>');
        $('#current_mange_course').text(decodeURI($.cookie('courseName')));
        addPlan();
        addStudent();
        addHomework();
    } else if ($('#current_center').length > 0) {
        $('#current_center').html('登录');
    }
});

//添加课程进度模块
/*字段合法性监测，通过点击确定允许提交*/
function addPlan() {
    $('#addPlan .confirm').click(function () {
        $('#addPlan').find('form').submit();
    });
    $('#addPlan').find('form').validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                type: 'POST',
                url: '../schedule?operate=addSchedule',
                success: function (data, statusText) {
                    if (data == 1) {
                        $(form).resetForm();
                        alert('添加成功');
                        $('#addPlan').modal('hide');
                        getCourseProcess();
                    } else {
                        alert(statusText);
                    }
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

//添加学生模块
/*字段合法性监测，通过点击确定允许提交*/
function addStudent() {
    $('#addStudent .confirm').click(function () {
        $('#addStudent').find('form').submit();
    });
    $('#addStudent').find('form').validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                type: 'POST',
                url: '../student?operate=add',
                clearForm: true,
                success: function (data, statusText) {
                    if (data == 1) {
                        alert('添加学生成功');
                        $('#addStudent').modal('hide');
                        getStudentList();
                    } else if (data == 2) {
                        alert('学生已存在，请刷新浏览器进行查看！');
                    } else {
                        alert('发生错误，请重试');
                    }
                }
            });
        },
        rules: {
            stuNum: {
                required: true,
                digits: true,
            },
            stuName: {
                required: true,
            },
            classNum: {
                required: true,
                digits: true,
            },
            department: {
                required: true,
            }
        },
        messages: {
            stuNum: {
                required: '请填写学生的学号！',
                digits: '学号应为纯数字组成！',
            },
            stuName: {
                required: '请填写学生姓名',
            },
            classNum: {
                required: '请填写学生所属班级',
            },
            department: {
                required: '请填写学生所属学院',
            },
        },
        highlight: function (element, errorClass) {
            $(element).css('border', '1px solid red');
        },
        unhighlight: function (element, errorClass) {
            $(element).css('border', '1px solid #ccc');
        }
    });
}
//布置作业模块
/*点击布置作业，合法性检测*/
function addHomework() {
    $('#addHomework .confirm').click(function () {
        $('#addHomework').find('form').submit();
    })
    $('#addHomework').find('form').validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                type: 'POST',
                url: '../homework?operate=addHomework',
                success: function (data, statusText) {
                    if (data == 1) {
                        alert('添加成功！');
                        $('#addHomework').modal('hide');
                        getHomeworkList();
                    } else if (data == 0) {
                        alert('发生错误，请重试！')
                    }
                }
            });
        },
        rules: {
            homeworkStopTime: {
                required: true,
            },
            homeworkTitle: {
                required: true,
            }
        },
        messages: {
            homeworkStopTime: {
                required: '请选择作业截至日期！',
            },
            homeworkTitle: {
                required: '请填写作业题目',
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

/*点击了留言板之后将cookie的currentPage设置为1*/
function initCurrentPage() {
    var messageWallLink = $('.message-wall');
    messageWallLink.click(function () {
        $.cookie('currentPage', 1);
    });
}