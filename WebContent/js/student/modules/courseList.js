$(function(){
    if ($.cookie('current_student')) {
        $('#current_user').text($.cookie('current_student'));
        getCourseList();
        chooseCourse();
    } else {
        alert('请登录！')
        window.location.href = 'index.html';
    }
    //注销登录
    /*点击注销按钮，清除cookies*/
    $('#logout').click(function () {
        $.removeCookie('current_student');
        window.location.href = '../login?operate=logout';
    });
    $('#close').click(function () {
        $('#curent_center').css({'width': $(document).width(), 'height': $(document).height()}).fadeToggle();
    })
});


//课程列表获取模块
/*接口注释:
 * url:课程点击后的链接
 * courseID:获取课程图片(存储在本地)
 * courseName:课程名
 * courseDescription:课程描述
 */
function getCourseList() {
    var $courseList=$('#courseList_student');
    $.ajax({
        type: "POST",
        url: "../course?operate=getCourses",
        success: function (data, statusText) {
            var html = '';
            var jsonData = $.parseJSON(data).courses;
            $.each(jsonData, function (index, value) {
                html+="<div class='item'><div class='inner'><img src='../images/"+jsonData[index].courseID+".jpg' /><div class='desc'><div class='desc_courseName'><h2>课程名：<span>" + jsonData[index].courseName + "</span></h2></div> <div class='desc_courseNum'><p>课程编号：<span class='desc_courseID'>"+ jsonData[index].courseID +"</span></p></div><p><span  class='desc_courseText'>"+jsonData[index].courseDescription+"</span></p></div></div></div>"
            })
            $courseList.empty().append(html);
        }
    });
}

//选择课程列表模块
/*点击课程列表中的管理按钮后，用AJAX的方式将所选课程ID传给后台存入session中，并且前端存入cookies中方便后续使用*/
function chooseCourse() {
    var $courseList=$('#courseList_student');
    $courseList.one('click', '.item', function () {
        var $current_course=$(this).find('.desc_courseID').text();
        var $courseName=$(this).find('.desc_courseName span').text();
        $.cookie('courseID',$current_course);
        $.cookie('courseName',encodeURI($courseName));
        $.ajax({
            url:"../course?operate=choose",
            type:"POST",
            data:{
                courseID:$current_course,
            },
            //TODO 发生选择课程错误的处理
            success: function (data, statusText) {
                if(data==1){
                    window.location.href='index_student.html';
                }else if(data==0){
                    alert('发生错误，请重试！');
                }
            }
        });
    });
}