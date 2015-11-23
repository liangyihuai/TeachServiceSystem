/**
 * Created by dust on 2015/11/17.
 */

$(function () {
    getCourseProcess();
    changeCourseProcess();
});
//获取课程进度模块
/*点击获取课程进度*/
function getCourseProcess() {
    $.ajax({
        type: 'POST',
        url: "../schedule?operate=getSchedule",
        success: function (data, statusText) {
            var html = '';
            var jsonData = $.parseJSON(data).schedule;
            $.each(jsonData, function (index, value) {
                html += "<tr><td>" + jsonData[index].scheduleID + "</td><td>" + jsonData[index].courseTime + "</td><td>" + jsonData[index].arrangement + "</td><td><a class='btn btn-default changePlan' href='#' role='button' data-toggle='modal' data-target='#changePlan'>修改</a></td></tr>"
            })
            $('#courseProcessPanel tbody').empty().append(html);
        }
    });
}
//修改课程进度模块
/*点击修改按钮修改课程进度*/
function changeCourseProcess() {
    $('#courseProcessPanel').on('click', '.changePlan', function () {
        var processNum = $(this).parents('tr').children().eq(0).html();
        var changeTime = $(this).parents('tr').children().eq(1).html();
        var changeText = $(this).parents('tr').children().eq(2).html();
        $('#changePlan .modal-title').text(changeTime);
        $('#change_plan_text').val(changeText);
        //提交修改合法性检测
        $('#changePlan .confirm').unbind('click').bind('click', function () {
            $('#changePlan').find('form').submit();
        });
        $('#changePlan').find('form').validate({
            submitHandler: function (form) {
                alert(processNum);
                $(form).ajaxSubmit({
                    type: 'POST',
                    url: '../schedule?operate=modifySchedule',
                    data: {
                        scheduleID: processNum,
                    },
                    clearForm: true,
                    success: function (data, statusText) {
                        if (data == 1) {
                            alert('修改成功');
                            $('#changePlan').modal('hide');
                            getCourseProcess();
                        }
                    }
                });
            },
            rules: {
                change_plan_text: {
                    required: true,
                }
            },
            messages: {
                change_plan_text: {
                    required: '亲，你没有填写修改内容哟！',
                }
            },
            highlight: function (element, errorClass) {
                $(element).css('border', '1px solid red');
            },
            unhighlight: function (element, errorClass) {
                $(element).css('border', '1px solid #ccc');
            }
        });
        /*end of validate*/
    });
}