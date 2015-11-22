/**
 * Created by dust on 2015/11/22.
 */
$(function(){
    getStudentList();
});
function getStudentList(){
    $.ajax({
        type:'POST',
        url:'../student?operate=list',
        success: function (data, statusText) {
            var html='';
            var jsonData= $.parseJSON(data).students;
            $.each(jsonData, function (index,value) {
                html+="<tr><td>"+jsonData[index].studentNO+"</td><td>"+jsonData[index].name+"</td><td>"+jsonData[index].sex+"</td><td>"+jsonData[index].clazz+"</td><td>"+"计算机科学与技术"+"</td><td><a class='btn btn-default'>删除</a></td></tr>";
            });
            $('tbody').empty().append(html);
        }
    });
}