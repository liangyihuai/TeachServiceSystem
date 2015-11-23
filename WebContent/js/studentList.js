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
            $.each(jsonData, function (index,value) {
                html+="<tr><td>"+jsonData[index].studentNO+"</td><td>"+jsonData[index].name+"</td><td>"+jsonData[index].sex+"</td><td>"+jsonData[index].clazz+"</td><td>"+"计算机科学与技术"+"</td><td><a class='btn btn-default deleteStudent'>删除</a></td></tr>";
            });
            $('tbody').empty().append(html);
        }
    });
}
//删除学生列表模块
/*点击删除传递学号进行学生删除*/
function deleteStudent(){
    $('tbody').on('click','.deleteStudent',function () {
        $.ajax({
            type:'POST',
            url:'',
            data:{
                //TODO 传递删除学生参数
            },
            success: function (data,statusText) {
                if(data==1){
                    alert('删除成功！');
                }else{
                    alert('发生错误，请重试！')
                }
            }
        });
        getStudentList();
    })
}