/**
 * Created by dust on 2015/11/17.
 */
$(function () {
    getCourseList();
    addCourse();
    chooseCourse();
    deleteCourse();
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
                html += "<li class='col-md-3 col-sm-6 col-xs-12'><div class='course-item'><div class='course-item-cover'><img src='../images/" + jsonData[index].courseID + ".jpg' class='img-responsive' /></div><div class='course-item-desc'><div class='course-item-desc-courseID'>课程编号：" + jsonData[index].courseID + "</div><div><h3 class='course-item-desc-title'>课程名称：" + jsonData[index].courseName + "</h3></div><div class='course-item-desc-text'><p>" + jsonData[index].courseDescription + "</p></div>" + "<div class='enter_manage'><button class='btn btn-default'>管理</button></div>" +"<div class='delete'><button class='btn btn-default'>删除</button></div>"+"</div></div></li>";
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
//添加课程
/*点击添加课程，填写课程信息，对课程Id进行检查，确认添加*/
function addCourse(){
    $('#addCourse').find('.confirm').click(function () {
        $('#addCourse').find('form').submit();
    });
    $('#addCourse').find('form').validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                type: 'POST',
                url: '../schedule?operate=addCourse',
                clearForm: true,
                success: function (data, statusText) {
                    if (data == 1) {
                        alert('添加课程成功！');
                        $('#addCourse').modal('hide');
                        getCourseList();
                    }else if(data==0){
                        alert('添加课程失败，请重试！');
                    }
                }
            });
        },
        rules:{
            courseName:{
                required:true,
            },
            courseDec:{
                required:true,
            }
        },
        messages:{
            courseName:{
                required:"请输入课程名称！"
            },
            courseDec:{
                required:"请输入课程描述！"
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
//删除课程
/*点击删除按钮，进行删除*/
function deleteCourse(){
    $('.course .row').on('click', '.delete button', function () {
        var current_course=$(this).parent().parent().find('.course-item-desc-courseID').html().substr(5);
        $.ajax({
            url:"../course?operate=deleteCourse",
            type:"POST",
            data:{
                courseID:current_course
            },
            //TODO 发生选择课程错误的处理
            success: function (data, statusText) {
                if(data==1){
                    alert('删除成功！');
                    getCourseList();
                }else if(data==0){
                    alert('删除失败，请重试！');
                }
            }
        });
    });
}