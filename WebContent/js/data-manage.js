/**
 * Created by dust on 2015/12/12.
 */
$(function () {
    getFileList();
    uploadFile();
    download();
});
//获取文件列表
/*参数：无
* 返回：json字符串
* */
function getFileList() {
    var $fileList=$('.file-list ul');
    $.ajax({
        type: "GET",
        url: "../source?operate=getFileList",
        success: function (data) {
            var jsondata= $.parseJSON(data).sourseList;
            var html = "";
            $.each(jsondata, function (index,value) {
                html+=" <li class='list-group-item'><span class='glyphicon glyphicon-file'></span><span class='none fileId'>"+jsondata[index].sourceID+"</span><span class='headline'>"+jsondata[index].headline+"</span><a href='javascript:void(0);' class='download'><span class='glyphicon glyphicon-download'></span></a></li>";
            })
            $fileList.empty().append(html);
        }
    });
}
//点击上传按钮上传文件
/*参数：文件名和数据
* 返回:1,0
*/
function uploadFile() {
    var $upload = $('.upload');
    var $fileForm = $upload.find('form');
    var $uploadBtn = $upload.find('a');
    $uploadBtn.click(function () {
        $fileForm.ajaxSubmit({
            type: 'POST',
            url: '../source?operate=uploadFile',
            success: function (data, statusText) {
                if (data == 1) {
                    $(this).resetForm();
                    alert('共享成功！');
                    getFileList();
                } else if(data==0) {
                    alert("上传失败，请重试！");
                }
            }
        });
    })
}
//点击下载按钮下载文件
/*参数：fileID
* 返回：后台调用浏览器进行下载
*/
function download(){
    var $fileList=$('.file-list ul');
    $fileList.on('click','.download',function () {
        var thisID=$(this).prev().prev().html();
        $.ajax({
            type:"POST",
            url:"../source?operate=download",
            data:{
                sourceID:thisID,
            }
        });
    })
}