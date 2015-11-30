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
            var commonPercent=$(form).find('input').eq(0).val();
            var finalPercent=$(form).find('input').eq(1).val();
            if(commonPercent*1+finalPercent*1==100){
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
            commonPercent: {
                number:true,
                required: true,
                range:[0,100],
            },
            finalPercent: {
                number:true,
                required: true,
                range:[0,100],
            }
        },
        messages: {
            commonPercent: {
                number:'',
                required: '',
                range:'',
            },
            finalPercent: {
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
                 commonPercent=$.parseJSON(data).commonPercent/100;
                 finalPercent=$.parseJSON(data).finalPercent/100;
                $.each(jsondata, function (index, value) {
                    html += "<tr><td>" + jsondata[index].studentNO + "</td><td>" + jsondata[index].name + "</td><td><input class='form-control' disabled='disabled' value='"+jsondata[index].commonScore+"' type='text' name='commonScore' /></td><td><input class='form-control' disabled='disabled' value='"+jsondata[index].finalScore+"' type='text' name='finalScore' /></td><td class='countScore'>" + jsondata[index].totalScore + "</td>+<td><button type='button' class='btn btn-default edit' aria-label='yes'><span class='glyphicon glyphicon-edit' aria-hidden='true'></span></button></td></tr>";
                });
                $('.scoreManage tbody').empty().append(html);
                countScore(commonPercent,finalPercent);
            } else {
                alert('获取成绩列表失败！')
            }
        }
    });
}
function countScore(commonPercent,finalPercent) {
    $("tbody").on('blur','input',function () {
        var normalItem=$(this).parent().parent().find('input').eq(0).val();
        var finalItem=$(this).parent().parent().find('input').eq(1).val();
        var that=$(this).parent().parent().find('.countScore')
        that.text(Math.round(normalItem*commonPercent+finalItem*finalPercent));
    })
}
function update() {
    $('tbody').on('click','.edit',function () {
        $(this).find('span').toggleClass(function () {
            if ($(this).hasClass('glyphicon-edit')) {
                $(this).removeClass('glyphicon-edit');
                return 'glyphicon-ok';
            }else{
                $(this).removeClass('glyphicon-ok');
                var tdList=$(this).parents('tr').find('td');
                var studentNO=tdList.eq(0).text();
                var commonScore=tdList.eq(2).children().val();
                var finalScore=tdList.eq(3).children().val();
                var totalScore=tdList.eq(4).text();
                $.ajax({
                    type:'POST',
                    url:'../score?operate=updateScore',
                    data:{
                        studentNO:studentNO,
                        commonScore:commonScore,
                        finalScore:finalScore,
                        totalScore:totalScore
                    },
                    success: function (data,status) {
                        if(data==1){
                            alert('修改成功');
                        }else{
                            alert('发生错误');
                        }                    }
                });
                return 'glyphicon-edit';
            }
        });
        if($(this).find('span').hasClass('glyphicon-ok')){
            var inputList=$(this).parents('tr').find('input');
            inputList.eq(0).removeAttr('disabled');
            inputList.eq(1).removeAttr('disabled');
        }else{
            var inputList=$(this).parents('tr').find('input');
            inputList.eq(0).attr('disabled','disabled');
            inputList.eq(1).attr('disabled','disabled');
        }
    });
}