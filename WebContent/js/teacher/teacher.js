$(function () {
    if (isLogin()) {
        init()
        addPlan();
        addStudent();
        addHomework();
    }else{
        $('#current_center').html('登录');
        alert('你未登录，即将跳转到登录页面进行登录！')
        window.location.href='index.html';
    }
    /*作业布置截至时间选框格式设定*/
    $('#homeworkStopTime').datetimepicker({
        format: 'yyyy-mm-dd',
        minView: 2,
        autoClose: true,
        language: 'zh-CN'
    });s
});

//初始化老师信息
function init(){
    $('#current_center').html($.cookie('current_teacher') + '<span class="caret"></span>');
    $('#current_mange_course').text(decodeURI($.cookie('courseName')));
}