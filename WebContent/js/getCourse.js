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
        url: "../course?operate=getCourses",
        //url:"courseList_data.php",
        data: {
            //teacherID:localStorage.currentUser
            teacherID: $.cookie('currentUser')
        },
        success: function (data, statusText) {
            var html = '';
            var jsonData = $.parseJSON(data).courses;
            $.each(jsonData, function (index, value) {
                html += "<li class='col-md-4 col-sm-6 col-xs-12'><div class='course-item'><div class='course-item-cover'><a href='#'><img src='../images/" + jsonData[index].courseID + ".png' class='img-responsive' /></a></div><div class='course-item-desc'><div class='course-item-desc-title'><a href='#'><h3>" + jsonData[index].courseName + "</h3></a></div><div class='course-item-desc-text'><p>" + jsonData[index].courseDescription + "</p></div></div></div></li>";
            })
            $('.course .row').empty().append(html);
        }
    });
}