$(function () {
    getHomeworkList();
    chooseHomework();
});

//获取作业列表
/*
* 请求参数：无
* 返回数据：作业列表json数组
* */
function getHomeworkList() {
    $.ajax({
        type: 'POST',
        url: '../homework?operate=correct',
        success: function (data, statusText) {
            var html = '';
            var jsonData = $.parseJSON(data);
            $.each(jsonData, function (index, value) {
                var buildTime = new Date(value.buildDate.time);
                var deadline=new Date(value.deadline.time);
                var buildeTimeText=buildTime.getFullYear()+'年'+buildTime.getMonth()+'月'+buildTime.getDate()+'日';
                var deadlineText=deadline.getFullYear()+'年'+deadline.getMonth()+'月'+deadline.getDate()+'日';
                html+="<tr><td>"+value.homeworkID+"</td><td>"+buildeTimeText+"<td>"+deadlineText+"<td>"+value.content+"<td><a class='btn btn-default selectBtn' href='studentWorkList_teacher.html'>批改</a>";
            });
            $('tbody').empty().append(html);
            $('.acount-work').text(jsonData.length);
        }
    });
}

//选择作业列表项
/*
* 请求参数：作业ID
* 返回：无*/
function chooseHomework() {
    $('table').on('click', '.selectBtn',function () {
        var homeworkNum=$(this).parent().parent().find('td').eq(0).text();
        $.ajax({
            type:"POST",
            url:"../homework?operate=setHomeworkID",
            data:{
                homeworkID:homeworkNum,
            }
        });
    })
}