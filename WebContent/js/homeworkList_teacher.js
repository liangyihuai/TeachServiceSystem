/*Created by dust on 2015/11/23.*/
$(function () {
    getHomeworkList();
    chooseHomework();
});
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
};
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