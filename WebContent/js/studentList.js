/**
 * Created by dust on 2015/11/22.
 */
$(function(){
    getStudentList();
    deleteStudent();
});
//得到学生列表模块
/*点击当前学生列表发起请求得到学生列表*/
function getStudentList(){
    $.ajax({
        type:'POST',
        url:'../student?operate=list',
        success: function (data, statusText) {
            var html='';
            var jsonData= $.parseJSON(data).students;
            var count=jsonData.length;
            $.each(jsonData, function (index,value) {
                html+="<tr><td>"+jsonData[index].studentNO+"</td><td>"+jsonData[index].name+"</td><td>"+jsonData[index].sex+"</td><td>"+jsonData[index].clazz+"</td><td>"+"计算机科学与技术"+"</td><td><a class='btn btn-default deleteStudent'>删除</a></td></tr>";
            });
            $('tbody').empty().append(html);
            $('.countman').text(count+'人');
        }
    });
}
//删除学生列表模块
/*点击删除传递学号进行学生删除*/
function deleteStudent(){
    $('tbody').on('click','.deleteStudent',function () {
        var stuNum=$(this).parent().prevAll().eq(4).html();
        $.ajax({
            type:'POST',
            url:'../student?operate=delete',
            data:{
                stuNum:stuNum,
            },
            success: function (data,statusText) {
                if(data==1){
                    alert('删除成功！');
                }else if(data==0){
                    alert('发生错误，请重试！')
                }
            }
        });
        getStudentList();
    })
}