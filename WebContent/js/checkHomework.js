/**
 * Created by dust on 2015/11/28.
 */
$(function () {
    getCheckList();
    checkOne();
});
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
                html1 += "<tr><td>" + commited[index].studentNO + "</td><td>" + commited[index].name + "</td><td><a data-toggle='modal' data-target='#check' class='btn btn-default checkBtn'>批改</a></td><td class='homeworkContent' hidden='hidden'>" + commited[index].content + "</td></tr>";
            });
            $.each(corrected, function (index, value) {
                html2 += "<tr><td>" + corrected[index].studentNO + "</td><td>" + corrected[index].name + "</td></tr>";
            });
            $.each(unCommited, function (index, value) {
                html3 += "<tr><td>" + unCommited[index].studentNO + "</td><td>" + unCommited[index].name + "</td></tr>";
            });
            $('#commited table').append(html1);
            $('#corrected table').append(html2);
            $('#unCommited table').append(html3);
            $('.commitedBadge').text(commited.length);
            $('.correctedBadge').text(corrected.length);
            $('.unCommitedBadge').text(unCommited.length);
        }
    });
}
function checkOne() {
    $('#commited table').on('click', '.checkBtn', function () {
        $.cookie('commitTheOne', $(this).parent().prev().prev().html());
        $('.oneHomework').html($(this).parent().next().html());
        $('.modal-title').html($(this).parent().prev().html());
        confirmCommit();
    })
}
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
                    studentNO: $.cookie('commitTheOne')
                },
                success: function (data,status) {
                    if(data==1){
                        $(form).resetForm();
                        $('#check').modal('hide');
                        alert('添加成功！');
                        getCheckList();
                    }else if(data==0){
                        alert('添加失败，请重试！');
                    }
                }
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