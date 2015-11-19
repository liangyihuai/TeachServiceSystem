/**
 * Created by dust on 2015/11/17.
 */
$(function () {
    getCourseList();
    $('.course .row').on('click', 'course-item-desc-title', function () {
        alert('hi')
    });
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
//      url: "../course?operate=getCourses",
          url:"courseList_data.php",
        data: {
            //teacherID:localStorage.currentUser
            teacherID: $.cookie('currentUser')
        },
        success: function (data, statusText) {
            var html = '';
            var jsonData = $.parseJSON(data).courses;
            $.each(jsonData, function (index, value) {
                html += "<li class='col-md-4 col-sm-6 col-xs-12'><div class='course-item'><div class='course-item-cover'><img src='../images/" + jsonData[index].courseID + ".jpg' class='img-responsive' /></div><div class='course-item-desc'><div class='course-item-desc-courseID'>课程编号："+jsonData[index].courseID+"</div><div class='course-item-desc-title'><h3>课程名称：" + jsonData[index].courseName + "</h3></div><div class='course-item-desc-text'><p>" + jsonData[index].courseDescription + "</p></div>"+"<div class='enter_manage'><a href='course-process.html' class='btn btn-default'>管理</a></div>"+"</div></div></li>";
            })
            $('.course .row').empty().append(html);
        }
    });
}