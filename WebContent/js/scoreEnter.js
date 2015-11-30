/**
 * Created by dust on 2015/11/28.
 */
$(function () {
    $('[data-toggle="tooltip"]').tooltip();
    setPercent();
    getScore();
    update();
});
function setPercent(){
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
                    url: '../score?operate=updateCoursePercent',
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
                range:[0,100],
            },
            finalTime: {
                number:true,
                required: true,
                range:[0,100],
            }
        },
        messages: {
            normalTime: {
                number:'',
                required: '',
                range:'',
            },
            finalTime: {
                number:'',
                required: '',
                range:'',
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
        url: "../score?operate=getScore",
        success: function (data, statusText) {
            if (data) {
                var html = '';
                var jsondata = $.parseJSON(data).score;
                $.each(jsondata, function (index, value) {
                    html += "<tr><td>" + jsondata[index].studentNO + "</td><td>" + jsondata[index].name + "</td><td><input class='form-control' disabled='disabled' value='"+jsondata[index].commonScore+"' type='text' name='commonScore' /></td><td><input class='form-control' disabled='disabled' value='"+jsondata[index].finalScore+"' type='text' name='finalScore' /></td><td class='countScore'>" + jsondata[index].totalScore + "</td>+<td><button type='button' class='btn btn-default edit' aria-label='yes'><span class='glyphicon glyphicon-edit' aria-hidden='true'></span></button></td></tr>";
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
    $('tbody').on('click','.edit',function () {
        $(this).find('span').toggleClass(function () {
            if ($(this).hasClass('glyphicon-edit')) {
                $(this).removeClass('glyphicon-edit');
                return 'glyphicon-ok';
            }else{
                $(this).removeClass('glyphicon-ok')
                return 'glyphicon-edit';
            }
        });
    });
}