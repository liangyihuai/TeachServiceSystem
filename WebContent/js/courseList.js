/**
 * Created by dust on 2015/11/17.
 */
$(function () {
    getCourseList();
    chooseCourse();
});
//课程列表获取模块
/*接口注释:
 * url:课程点击后的链接
 * courseID:获取课程图片(存储在本地)
 * courseName:课程名
 * courseDescription:课程描述
 */
function getCourseList() {
    $.ajax({
        type: "POST",
        url: "../course?operate=getCourses",
        data: {
            teacherID: $.cookie('currentUser')
        },
        success: function (data, statusText) {
            var html = '';
            var jsonData = $.parseJSON(data).courses;
            $.each(jsonData, function (index, value) {
                html += "<li class='col-md-4 col-sm-6 col-xs-12'><div class='course-item'><div class='course-item-cover'><img src='../images/" + jsonData[index].courseID + ".jpg' class='img-responsive' /></div><div class='course-item-desc'><div class='course-item-desc-courseID'>课程编号：" + jsonData[index].courseID + "</div><div><h3 class='course-item-desc-title'>课程名称：" + jsonData[index].courseName + "</h3></div><div class='course-item-desc-text'><p>" + jsonData[index].courseDescription + "</p></div>" + "<div class='enter_manage'><button class='btn btn-default'>管理</button></div>" + "</div></div></li>";
            })
            $('.course .row').empty().append(html);
        }
    });
}
//选择课程列表模块
/*点击课程列表中的管理按钮后，用AJAX的方式将所选课程ID传给后台存入session中，并且前端存入cookies中方便后续使用*/
function chooseCourse() {
    $('.course .row').one('click', '.enter_manage button', function () {
        var current_course=$(this).parent().parent().find('.course-item-desc-courseID').html().substr(5);
        var courseName=$(this).parent().parent().find('.course-item-desc-title').html().substr(5);
        $.cookie('courseID',current_course);
        $.cookie('courseName',encodeURI(courseName));
        $.ajax({
            url:"../course?operate=choose",
            type:"POST",
            data:{
                courseID:current_course,
            },
            //TODO 发生选择课程错误的处理
            success: function (data, statusText) {
                if(data==1){
                    window.location.href='course-process.html';
                }
            }
        });
    });
}