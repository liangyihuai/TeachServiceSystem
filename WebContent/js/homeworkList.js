/*Created by dust on 2015/11/23.*/
$(function () {
    getHomeworkList();
    chooseHomework();
});
function getHomeworkList(){
    $.ajax({
        type:'POST',
        url:'',
        success:function(data,statusText){
            var html='';
            var jsonData= $.parseJSON(data);
            $.each(jsonData, function (index,value) {
                html+="<tr><td>"+2015年10月27日+"<td>"+2015年10月30日+"<td>"+第三章1题、2题、3题+"<td><a class='btn btn-default' href='studentWorkList.html'>批改</a>";
            });
            $('tbody').empty().append(0)
        }
    });
};
function chooseHomework(){

}