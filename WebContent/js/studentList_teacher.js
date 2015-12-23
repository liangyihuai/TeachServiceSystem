/**
 * Created by dust on 2015/11/22.
 */
$(function(){
    getStudentList();
    deleteStudent();
    $('[data-toggle="tooltip"]').tooltip();
    uploadFile();
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
                html+="<tr><td>"+jsonData[index].studentNO+"</td><td>"+jsonData[index].name+"</td><td>"+jsonData[index].sex+"</td><td>"+jsonData[index].clazz+"</td><td>"+jsonData[index].college+"</td><td><a class='btn btn-default deleteStudent'>删除</a></td></tr>";
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
                    getStudentList();
                }else if(data==0){
                    alert('发生错误，请重试！')
                }
            }
        });
        getStudentList();
    })
}
//导入学生Excel列表模块
/*点击导入之后检查文件名，如果是excel文件就允许导入，否则不允许提交*/
function uploadFile(){
    var fileForm=$('.uploadFile form');
    var supporFile=/.(excel|xls|xlsx)$/g;
    $('.uploadFile button').click(function () {
        if(supporFile.test($('#file').val())){
            $(this).removeClass('disabled');
            fileForm.ajaxForm({
                type: 'POST',
                url: '../student?operate=import',
                success: function (data, statusText) {
                    if (data == 1) {
                        $(this).resetForm();
                        alert('导入成功');
                        getStudentList();
                    } else {
                        alert(statusText);
                    }
                }
            });
        }else{
            $(this).addClass('disabled');
            alert('文件格式不正确，请检查！');
            return false;
        }
    });
}
