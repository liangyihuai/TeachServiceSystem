/**
 * Created by dust on 2015/12/12.
 */
$(function () {
    getFileList();
    uploadFile();
});
//获取文件列表
function getFileList() {
    $.ajax({
        type: "GET",
        url: "../source?operate=getFileList",
        success: function (data) {
            var jsondata= $.parseJSON(data);
            console.log(jsondata);
            var html = "";
        }
    });
}
//点击上传按钮上传文件
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
                } else {
                    alert(statusText);
                }
            }
        });
    })
}