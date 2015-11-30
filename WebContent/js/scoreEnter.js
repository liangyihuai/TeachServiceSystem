/**
 * Created by dust on 2015/11/28.
 */
$(function () {
    $('[data-toggle="tooltip"]').tooltip();
    checkPercent();
    getScore();
    update();
});
function checkPercent(){
    $('#setPercent .confirm').click(function () {
        //验证相加是否等于100
            $('#setPercent form').submit();
    });
    $('#setPercent form').validate({
        submitHandler: function (form) {
            var normalTime=$(form).find('input').eq(0).val();
            var finalTime=$(form).find('input').eq(1).val();
            if(normalTime*1+finalTime*1==100){
                $(form).ajaxSubmit({
                    type: 'POST',
                    url: '567.php',
                    success: function (data, statusText) {
                        if (data == 1) {
                            $(form).resetForm();
                            alert('设置成功');
                            $('#addPlan').modal('hide');
                            getScore();
                        } else {
                            alert(statusText);
                        }
                    }
                });
            }else{
                alert('平时占比和期末占比总和不为100！')
            }

        },
        rules: {
            normalTime: {
                number:true,
                required: true,
            },
            finalTime: {
                number:true,
                required: true,
            }
        },
        messages: {
            normalTime: {
                number:'',
                required: '',
            },
            finalTime: {
                number:'',
                required: '',
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
function getScore() {
    $.ajax({
        type: "POST",
        url: "123.php",
        success: function (data, statusText) {
            if (data) {
                var html = '';
                var jsondata = $.parseJSON(data);
                $.each(jsondata, function (index, value) {
                    html += "<tr><td>" + jsondata[index].stuNum + "</td><td>" + jsondata[index].stuName + "</td><td><input class='form-control' type='text' name='normalItem' /></td><td><input class='form-control' type='text' name='finalItem' /></td><td class='countScore'>" + jsondata[index].countScore + "</td></tr>";
                });
                $('.scoreManage tbody').append(html);
                countScore();
            } else {
                alert('获取成绩列表失败！')
            }
        }
    });
}
function countScore() {
    $(".scoreManage input").bind('blur', function () {
        var normalItem=$(this).parent().parent().find('input').eq(0).val();
        var finalItem=$(this).parent().parent().find('input').eq(1).val();
        var that=$(this).parent().parent().find('.countScore')
        that.text(Math.round(normalItem*0.3+finalItem*0.7));
    })
}
function update() {
    $('#update').click(function () {
        var scoreList = $('.scoreManage tbody tr')
        var scoreTable = [];
        $.each(scoreList, function (index) {
            var scoreItem = {};
            scoreItem.stuNum = $(scoreList[index]).find('td').eq(0).text();
            scoreItem.normalScore = $(scoreList[index]).find('input').eq(0).val();
            scoreItem.finalScore = $(scoreList[index]).find('input').eq(1).val();
            scoreItem.coutScore= $(scoreList[index]).find(':last-child').text();
            scoreTable.push(scoreItem);
        })
        $.ajax({
            type: "POST",
            url: "234.php",
            data: {
                scoreTable: JSON.stringify(scoreTable),
            },
            success: function (data, statusText) {
                if (data == 1) {
                    alert('更新成功！');
                } else {

                }
            }
        });
    });
}