/**
 * Created by dust on 2015/12/11.
 */
$(function () {
    var color=['#F44336','#F50057','#2196F3','#03A9F4','#FFEA00'];//声明16机制颜色数组
    showButton();
    loadList($.cookie('currentPage'),color);
    turnPage(color);
    sendMessage(color);
});
/*发送信息
* 模块功能：判断输入框中字符的数目，合法进行发送
*/
function sendMessage(color) {
    var $sendButton = $('.leave_msg').find('button');
    var $msg_content = $('#msg_content');
    $sendButton.click(function () {
        if ($msg_content.val().length > 250 || $msg_content.val().length == 0) {
            alert('留言为空，或者超出最大留言长度250个字符！');
        } else {
            $.ajax({
                type: "post",
                url: "../leaveword?operate=add",
                data: {
                    content: $msg_content.val()
                },
                success: function (data) {
                    if (data == 1) {
                        loadList(1,color);
                        $msg_content.val('');
                    }
                    else if (data == 0) {
                        alert('小纸条在路上被大风刮跑了,赶紧再发一张!')
                    }
                }
            });
        }
    });
}
/*按钮显示隐藏*/
function showButton() {
    var $sendButton = $('.send');
    var $leave_msg = $('.leave_msg');
    $sendButton.click(function () {
        $leave_msg.slideToggle();
    });
}
/*页面跳转
*模块功能：点击上一页下一页调用 loadList(currentPage,color)进行跳转
*/
function turnPage(color) {
    var prev = $('.prev');
    var next = $('.next');
    prev.click(function () {
        if ($.cookie('currentPage') != 1) {
            loadList($.cookie('currentPage') * 1 - 1,color);
            $.cookie('currentPage', $.cookie('currentPage') * 1 - 1);
        }else{
            alert('这已经是第一页，点击下一页或者刷新试试！');
        }
    });
    next.click(function () {
        if ($.cookie('currentPage') != pageCount) {
            loadList($.cookie('currentPage') * 1 + 1,color);
            $.cookie('currentPage', $.cookie('currentPage') * 1 + 1);
        }else {
            alert('这已经是最后一页，点击上一页或者刷新试试！');
        }
    });

}
function loadList(currentPage,color) {
    var prev = $('.prev');
    var next = $('.next');
    var $msg_box = $('.msg_box');
    $.ajax({
        type: "POST",
        url: "../leaveword?operate=list",
        data: {
            page: currentPage,
        },
        success: function (data) {
            var jsonData = $.parseJSON(data).leaveWords;
            pageCount = $.parseJSON(data).pageCount;
            var html = '';
            $.each(jsonData, function (index, value) {
                var msg_item_name = jsonData[index].writer;
                var msg_item_text = jsonData[index].content;
                var msg_item_time = jsonData[index].time;
                var thisIndex=Math.floor(Math.random()*(color.length-1));
                var thisColor=color[thisIndex];
                html += "<div class='msg_item row'><span class='msg_item_name' style='background-color:"+thisColor +"'>" + msg_item_name + "</span><div class='msg_item_content' style='background-color:"+thisColor+"'><span class='msg_item_text'>" + msg_item_text + "</span><span class='msg_item_time'>" + msg_item_time + "</span></div></div>"
            });
            $msg_box.empty().append(html);
            $.cookie('currentPage', currentPage);
            if ($.cookie('currentPage') == 1) {
                prev.addClass('disabled').attr('disabled','disabled');
                // TODO 考虑学生端不引用bootstrap处理
            } else {
                prev.removeClass('disabled').attr('disabled','enabled');
            }
            if ($.cookie('currentPage') == pageCount) {
                next.addClass('disabled').attr('disabled','disabled');
            } else {
                next.removeClass('disabled').attr('disabled','disabled');
            }
        }
    })
}