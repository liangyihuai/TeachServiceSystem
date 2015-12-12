/**
 * Created by dust on 2015/12/11.
 */
$(function () {
    showButton();
    loadList($.cookie('currentPage'));
    turnPage();
});
function showButton() {
    var $sendButton = $('.send');
    var $leave_msg = $('.leave_msg');
    $sendButton.click(function () {
        $leave_msg.slideToggle();
    });
}
function turnPage() {
    var prev = $('.prev');
    var next = $('.next');
    prev.click(function () {
        if($.cookie('currentPage')!=1){
        loadList($.cookie('currentPage') * 1 - 1);
        $.cookie('currentPage', $.cookie('currentPage') * 1 - 1);
        }
    });
    next.click(function () {
        if($.cookie('currentPage')!=pageCount) {
            loadList($.cookie('currentPage') * 1 + 1);
            $.cookie('currentPage', $.cookie('currentPage') * 1 + 1);
        }
    });

}
function loadList(currentPage) {
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
            pageCount=$.parseJSON(data).pageCount;
            var html = '';
            $.each(jsonData, function (index, value) {
                var msg_item_name = jsonData[index].writer;
                var msg_item_text = jsonData[index].content;
                var msg_item_time = jsonData[index].time;
                html += "<div class='msg_item row'><span class='msg_item_name pull-left'>" + msg_item_name + "</span><div class='msg_item_content pull-left'><span class='msg_item_text'>" + msg_item_text + "</span><span class='msg_item_time'>" + msg_item_time + "</span></div></div>"
            });
            $msg_box.empty().append(html);
            $.cookie('currentPage', currentPage);
            if($.cookie('currentPage')==1){
                prev.addClass('disabled');
            }else{
                prev.removeClass('disabled');
            }
            if($.cookie('currentPage')==pageCount){
                next.addClass('disabled');
            }else{
                next.removeClass('disabled');
            }
        }
    })
}