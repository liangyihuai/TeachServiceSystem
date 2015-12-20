/**
 * Created by dust on 2015/12/17.
 */
$(function () {
    initCurrentPage();//初始化留言板
    if ($.cookie('current_student')) {
        toogleTab();
        modal();
        getCourseProcess();
        getHomeworkList();
        selectOne();
        uploadFile();
        getFileList();
        remove();
    } else {
        //alert('请登录！')
        //window.location.href='index.html';
    }
    //注销登录
    /*点击注销按钮，清除cookies*/
    $('#logout').click(function () {
        $.removeCookie('current_student');
        window.location.href = '../login?operate=logout';
    });
    $('#close').click(function () {
        $('#curent_center').css({'width': $(document).width(), 'height': $(document).height()}).fadeToggle();
    })
});
//modal框体切换
function modal(){
    var $modal=$('.modal'),
        $close=$modal.find('.close_modal'),
        $cancel=$modal.find('.cancel'),
        $confirm=$modal.find('.confirm'),
        $modal_form=$modal.find('form');

    $close.click(function(){
        $modal_form.get(0).reset();
        $(this).parents('.modal').hide(500);
    });
    $cancel.click(function(){
        $modal_form.get(0).reset();
        $(this).parents('.modal').hide(500);
    });
    $confirm.click(function () {
        $modal_form.ajaxSubmit({
            type:'POST',
            url:'123.php',
            data: {
                homeworkID:$.cookie('selectHomework'),
            },
            success:function(data){
                if(data==1){
                    alert('提交成功！');
                    $(this).reset();
                }else if(data==0){
                    alert('提交失败，请重试！');
                }
            }
        })
    });
}
//选项卡切换
function toogleTab() {
    var $menu_li = $('nav li');
    var $menu_a = $('nav li a');
    var $contents = $('.tab_design');
    $menu_a.click(function (e) {
        var $this = $(this),
            target = $this.attr('href');
        if ($this.parent().hasClass('active')) return;
        $menu_li.removeClass('active');
        $this.parent().addClass('active');
        // 平滑切换
        $contents.find('section')
            .fadeTo(500, 0, function () {
                $(this).removeClass('active').filter(target).addClass('active').fadeTo(500, 1);
            });
        // 阻止浏览器默认的链接动作
        e.preventDefault();
    });
}
//查询课程进度
/*点击获取课程进度*/
function getCourseProcess() {
    var beginTime=new Date(2015,8,7);
    var currentTime= (Math.ceil((new Date()-beginTime)/1000/3600/24/7));
    $.ajax({
        type: 'POST',
        //url: "../schedule?operate=getSchedule",
        url:"courseProcess_data.php",
        success: function (data, statusText) {
            var html = '';
            var jsonData = $.parseJSON(data).schedule;
            $.each(jsonData, function (index, value) {
                html += "<tr><td>" + jsonData[index].scheduleID + "</td><td>" + jsonData[index].courseTime + "</td><td>" + jsonData[index].arrangement + "</td></tr>"
            });
            $('#course_process tbody').empty().append(html);
            $('.currentTime').text('第'+currentTime+'周');
        }
    });
}
//获取作业列表
function getHomeworkList() {
    var $homeworkList=$('#homework');
    $.ajax({
        type: 'POST',
        //url: '../homework?operate=correct',
        url:"homeworkList.php",
        success: function (data, statusText) {
            var html = '';
            var jsonData = $.parseJSON(data);
            $.each(jsonData, function (index, value) {
                var buildTime = new Date(value.buildDate.time);
                var deadline=new Date(value.deadline.time);
                var buildeTimeText=buildTime.getFullYear()+'年'+buildTime.getMonth()+'月'+buildTime.getDate()+'日';
                var deadlineText=deadline.getFullYear()+'年'+deadline.getMonth()+'月'+deadline.getDate()+'日';
                html+="<tr><td>"+value.homeworkID+"</td><td>"+buildeTimeText+"</td><td>"+deadlineText+"</td><td>"+value.content+"</td><td><button type='button' class='btn'>提交作业</button></td></tr>";
            });
            $homeworkList.find('tbody').empty().append(html);
            $('.acount-work').text(jsonData.length);
        }
    });
};
//选择作业，存入当前选择的作业ID，改变modal的作业内容
function selectOne(){
    var $homeworkList=$('#homework'),
        $homeworkList_body=$homeworkList.find('tbody'),
        $modal=$('.modal');
    $homeworkList_body.on('click','button', function () {
        var $allTd=$(this).parents('tr').children(),
            $homeworktitle=$allTd.eq(3).text(),
            $thishomeworkID=$allTd.eq(0).text();
        $.cookie('selectHomework',$thishomeworkID);
        $modal.find('.homeworkTitle p').html($homeworktitle);
        $modal.show(500);
    })

}
//点击上传按钮上传文件
/*参数：文件名和数据
 * 返回:1,0
 */
function uploadFile() {
    var $upload = $('.upload');
    var $fileForm = $upload.find('form');
    var $uploadBtn = $fileForm.find('button');
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
//查询删除下载资料
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
                html+="<li><form action='../source?operate=download' method='get'><div class='left'><span class='file_icon'></span><input type='hidden' name='sourceID' id='sourceID' value='"+jsondata[index].sourceID+"'/><input type='hidden' name='operate' value='download' /><span class='headline'>"+jsondata[index].headline+"</span></div><div class='right'><button type='submit' class='btn download'>下载</button><button type='button' class='btn remove'>删除</button></div></form></li>";
            })
            $fileList.empty().append(html);
        }
    });
}
//点击删除按钮删除文件
/*参数：fileID
 * 返回：后台调用进行删除
 */
function remove(){
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
        });
    })
}
//刷新留言墙，发送信息
/*点击了留言板之后将cookie的currentPage设置为1*/
function initCurrentPage() {
    var messageWallLink = $('a[href="#msgwall"]');// TODO 修改class字段
    messageWallLink.click(function () {
        $.cookie('currentPage', 1);
    });
}