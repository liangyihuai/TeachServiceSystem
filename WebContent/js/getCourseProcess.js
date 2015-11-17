/**
 * Created by dust on 2015/11/17.
 */
//获取课程进度模块
/*点击获取课程进度*/
$(function () {
    getCourseProcess();
});
function getCourseProcess() {
            $.ajax({
                type:'POST',
                //url:'courseProcess_data.php',
                url:"../course?operate=getSchedule",
                data:{
                    courseID:'1'//TODO 这个值需要变动，测试设置为1
                },
                success: function (data,statusText) {
                    var html = '';
                    var jsonData = $.parseJSON(data).schedule;
                    $.each(jsonData, function (index, value) {
                        html+="<tr><td>"+jsonData[index].scheduleID+"</td><td>"+jsonData[index].courseTime+"</td><td>"+jsonData[index].arrangement+"</td><td><a class='btn btn-default' href='#' role='button' data-toggle='modal'data-target='#myModal'>修改</a></td> <td><a class='btn btn-default' href='#' role='button'>删除</a></td></tr>"
                    })
                    $('#courseProcessPanel tbody').empty().append(html);
                }
            });
}