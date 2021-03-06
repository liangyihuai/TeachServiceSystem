$(function () {
    getFileList();
    uploadFile();
    removeFile();
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
                html+=" <li class='list-group-item'><form action='../source?operate=download' method='get' ><span class='glyphicon glyphicon-file'></span><input type='hidden' name='sourceID' id='sourceID' value='"+jsondata[index].sourceID+"' /><input type='hidden' name='operate' value='download' /><span class='headline'>"+jsondata[index].headline+"</span><div class='pull-right'><button type='submit' class='download'><span class='glyphicon glyphicon-download'></span></button><a href='javascript:void(0);' class='remove'><span class='glyphicon glyphicon-remove'></span></a></div></form></li>";
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
        var theSelectFile=$fileForm.find('input[type="file"]').val();
        if(theSelectFile.length!=0){
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
        }else{
            alert('请选择一个文件进行上传');
            return;
        }

    })
}

//点击删除按钮删除文件
/*参数：fileID
 * 返回：后台调用进行删除
*/
function removeFile(){
    var $fileList=$('.file-list ul');
    $fileList.on('click','.remove',function () {
        var thisID=$(this).parents('form').find('input').val();
        $.ajax({
            type:"POST",
            url:"../source?operate=deleteFile",
            data:{
                sourceID:thisID,
            },
            success: function (data) {
                if(data==1){
                    alert('删除成功！');
                    getFileList();
                }
                else if(data==0){
                    alert('删除失败，请重试！');
                }
                else{
                    alert('你没有权限删除此文件！');
                }
            }
            //TODO 错误处理
        });
    })
}