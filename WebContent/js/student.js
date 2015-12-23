/**
 * Created by dust on 2015/12/17.
 */
$(function () {
    var color=['#F44336','#F50057','#2196F3','#03A9F4','#FFEA00'];//声明16机制颜色数组
    initCurrentPage();//初始化留言板
    if ($.cookie('current_student')) {
        $('#current_user').text($.cookie('current_student'));
        toogleTab(color);
        $('nav li a').eq(0).trigger('click');
    } else {
        alert('请登录！')
        window.location.href = 'index.html';
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
function modal() {
    var $modal = $('.modal'),
        $close = $modal.find('.close_modal'),
        $cancel = $modal.find('.cancel'),
        $confirm = $modal.find('.confirm'),
        $modal_form = $modal.find('form');

    $close.click(function () {
        $modal_form.get(0).reset();
        $(this).parents('.modal').hide(500);
    });
    $cancel.click(function () {
        $modal_form.get(0).reset();
        $(this).parents('.modal').hide(500);
    });
    $confirm.click(function () {
        $.ajax({
            type: 'POST',
            url: '../StudentHomeworkServlet?operate=commitHomework',
            data: {
                homeworkID: $.cookie('selectHomework'),
                content:$('#homeworkContent').val()
            },
            success: function (data) {
                if (data == 1) {
                    alert('提交成功！');
                    $(this).reset();
                    $(this).parents('.modal').hide(500);
                    getHomeworkList();
                } else if (data == 0) {
                    alert('提交失败，请重试！');
                }
            },
            error: function (jqXHR,textStatus,errorThrown) {
                alert('发生错误，错误码：'+jqXHR.status+",参考错误："+errorThrown);
            }
        })
    });
}
//选项卡切换
function toogleTab(color) {
    var $menu_li = $('nav li');
    var $menu_a = $('nav li a');
    var $contents = $('.tab_design');
    $menu_a.click(function (e) {
        var $this = $(this),
            index = $menu_a.index(this);
        switch (index){
            case 0:
                getCourseProcess();
                break;
            case 1:
                getHomeworkList();
                selectOne();
                modal();
                break;
            case 2:
                uploadFile();
                getFileList();
                remove();
                break;
            case 3:
                loadList(1,color);
                break;
            case 4:
                getScore();
                break;
        }
        if ($this.parent().hasClass('active')) return;
        $menu_li.removeClass('active');
        $this.parent().addClass('active');
        // 平滑切换
        $contents.find('section').fadeTo(500, 0).removeClass('active').eq(index).addClass('active').fadeTo(500, 1);
        e.preventDefault();
    });
}
//查询课程进度
/*点击获取课程进度*/
function getCourseProcess() {
    var beginTime = new Date(2015, 8, 7);
    var currentTime = (Math.ceil((new Date() - beginTime) / 1000 / 3600 / 24 / 7));
    $.ajax({
        type: 'POST',
        url: "../schedule?operate=getSchedule",
        success: function (data, statusText) {
            var html = '';
            var jsonData = $.parseJSON(data).schedule;
            $.each(jsonData, function (index, value) {
                html += "<tr><td>" + jsonData[index].scheduleID + "</td><td>" + jsonData[index].courseTime + "</td><td>" + jsonData[index].arrangement + "</td></tr>"
            });
            $('#course_process tbody').empty().append(html);
            $('.currentTime').text('第' + currentTime + '周');
        },
        error: function (jqXHR,textStatus,errorThrown) {
            alert('发生错误，错误码：'+jqXHR.status+",参考错误："+errorThrown);
        }
    });
}
//获取作业列表
function getHomeworkList() {
    var $homeworkList = $('#homework');
    $.ajax({
        type: 'POST',
        url: '../StudentHomeworkServlet?operate=getHomework',
        success: function (data, statusText) {
            var html = '';
            var jsonData = $.parseJSON(data);
            var currentTime=new Date().getTime();
            $.each(jsonData, function (index, value) {
                var buildTime = new Date(value.buildDate.time);
                var deadline = new Date(value.deadline.time);
                var buildeTimeText = buildTime.getFullYear() + '年' + (buildTime.getMonth()+1) + '月' + buildTime.getDate() + '日';
                var deadlineText = deadline.getFullYear() + '年' + (deadline.getMonth()+1) + '月' + deadline.getDate() + '日';
                var studentHomeworkContent=value.studentHomeworkContent;
                if(currentTime>deadline.getTime()){
                    if(value.score!=-1){
                        html += "<tr><td>" + value.homeworkID + "</td><td>" + buildeTimeText + "</td><td>" + deadlineText + "</td><td>" + value.content + "</td><td><button type='button' class='btn'><input type='hidden' value='"+studentHomeworkContent+"' /><input type='hidden' value='"+value.score+"'><input type='hidden' value='"+value.comment+"'>查看成绩</button></td></tr>";
                    }else{
                        html += "<tr><td>" + value.homeworkID + "</td><td>" + buildeTimeText + "</td><td>" + deadlineText + "</td><td>" + value.content + "</td><td><button type='button' class='btn'><input type='hidden' value='"+studentHomeworkContent+"' /><input type='hidden' value='老师还未批阅'>查看成绩</button></td></tr>";
                    }
                }else{
                    if(studentHomeworkContent.length>0){
                        html += "<tr><td>" + value.homeworkID + "</td><td>" + buildeTimeText + "</td><td>" + deadlineText + "</td><td>" + value.content + "</td><td><button type='button' class='btn'><input type='hidden' value='"+studentHomeworkContent+"' />修改作业</button></td></tr>";
                    }else{
                        html += "<tr><td>" + value.homeworkID + "</td><td>" + buildeTimeText + "</td><td>" + deadlineText + "</td><td>" + value.content + "</td><td><button type='button' class='btn'>提交作业</button></td></tr>";
                    }
                }
            });
            $homeworkList.find('tbody').empty().append(html);
            $('.acount-work').text(jsonData.length);
        },
        error: function (jqXHR,textStatus,errorThrown) {
            alert('发生错误，错误码：'+jqXHR.status+",参考错误："+errorThrown);
        }
    });
};
//选择作业，存入当前选择的作业ID，改变modal的作业内容
function selectOne() {
    var $homeworkList = $('#homework'),
        $homeworkList_body = $homeworkList.find('tbody'),
        $modal = $('.modal');
    $homeworkList_body.on('click', 'button', function () {
        var $allTd = $(this).parents('tr').children(),
            $homeworktitle = $allTd.eq(3).text(),
            $thishomeworkID = $allTd.eq(0).text();
        $.cookie('selectHomework', $thishomeworkID);
        if($(this).text()=="提交作业"){
            $modal.find('#homeworkContent').html('');
            $modal.find('.homeworkTitle p').html($homeworktitle);
            $modal.find('#homeworkContent').html($(this).find('input').eq(0).val()).removeAttr('readonly');
            $modal.find('#homeworkScore').hide();
            $modal.find('.confirm').text('提交');
            $modal.find('button').show();
            $modal.show(500);
        }else if($(this).text()=="修改作业"){
            $modal.find('.homeworkTitle p').html($homeworktitle);
            $modal.find('#homeworkContent').html($(this).find('input').eq(0).val()).removeAttr('readonly');
            $modal.find('#homeworkContent').html($(this).find('input').val());
            $modal.find('#homeworkScore').hide();
            $modal.find('.confirm').text('修改');
            $modal.find('button').show();
            $modal.show(500);
        }else{
            $modal.find('.homeworkTitle p').html($homeworktitle);
            $modal.find('#homeworkContent').html($(this).find('input').eq(0).val()).attr('readonly','readonly');
            $modal.find('#homeworkScore').show().find('p span').eq(0).text($(this).find('input').eq(1).val());
            $modal.find('#homeworkScore').show().find('p span').eq(1).text($(this).find('input').eq(2).val());
            $modal.find('button').hide();
            $modal.show(500);
        }
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
                },
                error: function (jqXHR,textStatus,errorThrown) {
                    alert('发生错误，错误码：'+jqXHR.status+",参考错误："+errorThrown);
                }
            });
        }else{
            alert('请选择一个文件进行上传');
            return;
        }
    })
}
//查询删除下载资料
//获取文件列表
/*参数：无
 * 返回：json字符串
 * */
function getFileList() {
    var $fileList = $('.file-list ul');
    $.ajax({
        type: "GET",
        url: "../source?operate=getFileList",
        success: function (data) {
            var jsondata = $.parseJSON(data).sourseList;
            var html = "";
            $.each(jsondata, function (index, value) {
                html += "<li><form action='../source?operate=download' method='get'><div class='left'><span class='file_icon'></span><input type='hidden' name='sourceID' id='sourceID' value='" + jsondata[index].sourceID + "'/><input type='hidden' name='operate' value='download' /><span class='headline'>" + jsondata[index].headline + "</span></div><div class='right'><button type='submit' class='btn download'>下载</button><button type='button' class='btn remove'>删除</button></div></form></li>";
            })
            $fileList.empty().append(html);
        },
        error: function (jqXHR,textStatus,errorThrown) {
            alert('发生错误，错误码：'+jqXHR.status+",参考错误："+errorThrown);
        }
    });
}
//点击删除按钮删除文件
/*参数：fileID
 * 返回：后台调用进行删除
 */
function remove() {
    var $fileList = $('.file-list ul');
    $fileList.on('click', '.remove', function () {
        var thisID = $(this).parents('form').find('input').val();
        $.ajax({
            type: "POST",
            url: "../source?operate=deleteFile",
            data: {
                sourceID: thisID,
            },
            success: function (data) {
                if (data == 1) {
                    alert('删除成功！');
                    getFileList();
                }
                else if (data == 0) {
                    alert('删除失败，请重试！');
                }
                else if(data==2){
                    alert('你没有权限删除此文件！');
                }
            },
            error: function (jqXHR,textStatus,errorThrown) {
                alert('发生错误，错误码：'+jqXHR.status+",参考错误："+errorThrown);
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
//得到成绩模块
function getScore() {
    $.ajax({
        type: "POST",
        url: "../score?operate=getScore",
        success: function (data, statusText) {
            if (data) {
                var html = '';
                var jsondata = $.parseJSON(data).score;
                var commonPercent = $.parseJSON(data).commonPercent;
                var finalPercent = $.parseJSON(data).finalPercent;
                $('#tipCommonPercent').text(commonPercent + "%");
                $('#tipFinalPercent').text(finalPercent + "%");
                $.each(jsondata, function (index, value) {
                    html += "<tr><td>" + jsondata[index].commonScore + "</td><td>" + jsondata[index].finalScore + "</td><td class='countScore'>" + jsondata[index].totalScore + "</td></tr>";
                });
                $('#achievements tbody').empty().append(html);
            }
        },
        error: function (jqXHR,textStatus,errorThrown) {
            alert('发生错误，错误码：'+jqXHR.status+",参考错误："+errorThrown);
        }
    });
}