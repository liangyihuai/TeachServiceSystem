$(function () {
    getCheckList();
    checkOne();
});


//得到作业列表
/*
* 请求参数：无
* 返回：json数组
* */
function getCheckList() {
    $.ajax({
        type: 'POST',
        url: '../homework?operate=listHomework',
        success: function (data, statusText) {
            var html1 = '';
            var html2 = '';
            var html3 = '';
            var jsondata = $.parseJSON(data);
            var commited = jsondata.commited;
            var corrected = jsondata.corrected;
            var unCommited = jsondata.unCommited;
            $.each(commited, function (index, value) {
                html1 += "<tr><td hidden>"+commited[index].studentHomeworkID+"</td><td>" + commited[index].studentNO + "</td><td>" + commited[index].name + "</td><td><a data-toggle='modal' data-target='#check' class='btn btn-default checkBtn'>批改</a></td><td class='homeworkContent' hidden='hidden'>" + commited[index].content + "</td></tr>";
            });
            $.each(corrected, function (index, value) {
                html2 += "<tr><td>" + corrected[index].studentNO + "</td><td>" + corrected[index].name + "</td><td>"+corrected[index].score+"</td></tr>";
            });
            $.each(unCommited, function (index, value) {
                html3 += "<tr><td>" + unCommited[index].studentNO + "</td><td>" + unCommited[index].name + "</td></tr>";
            });
            $('#commited tbody').empty().append(html1);
            $('#corrected tbody').empty().append(html2);
            $('#unCommited tbody').empty().append(html3);
            $('.commitedBadge').text(commited.length);
            $('.correctedBadge').text(corrected.length);
            $('.unCommitedBadge').text(unCommited.length);
        }
    });
}

//选取批改项
/*
* 将选取项Id存入cookie
* 动态更改批改作业modal框
* */
function checkOne() {
    $('#commited table').on('click', '.checkBtn', function () {
        $.cookie('commitTheOne', $(this).parents('tr').find('td').eq(0).html());
        $('.oneHomework').html($(this).parent().next().html());
        $('.modal-title').html($(this).parent().prev().html());
        confirmCommit();
    })
}

//批改作业modal处理
/*
* 批改验证
* 请求参数：作业ID：studentHomeworkID
* 返回：{成功：1,错误：0}*/
function confirmCommit() {
    $('#check .confirm').click(function () {
        $('#check form').submit();
    })
    $('#check form').validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                type:"POST",
                url:"../homework?operate=correctHomework",
                data:{
                    studentHomeworkID: $.cookie('commitTheOne')
                },
                success: function (data,status) {
                    if(data==1){
                        $(form).resetForm();
                        $('#check').modal('hide');
                        alert('批改成功！');
                        getCheckList();
                    }else if(data==0){
                        alert('批改失败，请重试！');
                    }
                }
                //TODO 错误处理
            });
        },
        rules: {
            score: {
                required:true,
                number:true
            }
        },
        messages:{
            score:{
                required:"分数为必填项！",
                number:"请给出一个0-100的有效数字！"
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