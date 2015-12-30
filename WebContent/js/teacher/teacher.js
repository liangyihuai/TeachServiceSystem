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
});

//初始化老师信息
function init(){
    $('#current_center').html($.cookie('current_teacher') + '<span class="caret"></span>');
    $('#current_mange_course').text(decodeURI($.cookie('courseName')));
}