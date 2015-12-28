/**
 * Created by dust on 2015/12/28.
 */
$(function () {
    if ($.cookie('current_student')) {
        $('#current_user').text($.cookie('current_student'));
        (function getAccountInfo() {

        })();
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
    $('#info').click(function () {
        $('#curent_center').css({'width': $(document).width(), 'height': $(document).height()}).fadeIn();
        $(this).hide();
    })
    $('.close').click(function () {
        $('#curent_center').fadeOut();
        $('#info').show(500);
    })
    $('#current_user').hover(function () {
        $('#current_user_info').show(300);
        $('#current_course').show(500);
        $('#logout').show(800);
    }, function () {
        $(document).click(function () {
            $('#current_user_info').hide(800);
            $('#current_course').hide(500);
            $('#logout').hide(300);
        });
    })
    $('.changePassBtn').click(function () {
        $(this).next().slideToggle(300, "easeInOutBack");
    });
    $('.tip form').validate({
        submitHandler: function (form) {
            var oldPass = $(form).find('input').eq(0).val();
            var newPass = $(form).find('input').eq(1).val();
            $(form).ajaxSubmit({
                type: 'POST',
                url: '123.php',
                success: function (data, statusText) {
                    if (data == 1) {
                        alert('设置成功');
                        $.removeCookie('current_student');
                        window.location.href = '../login?operate=logout';
                    } else {
                        alert(statusText);
                    }
                },
                error: function (jqXHR,textStatus,errorThrown) {
                    alert('发生错误，错误码：'+jqXHR.status+",参考错误："+errorThrown);
                }
            });
        },
        rules: {
            oldPass: {
                required: true,
            },
            newPass: {
                required: true,
            },
            newPass2:{
                required: true,
                equalTo:"#newPass"
            }
        },
        messages: {
            oldPass: {
                required: '',
            },
            newPass: {
                required: '',
            },
            newPass2:{
                required: '',
                equalTo:''
            }
        },
        highlight: function (element, errorClass) {
            $(element).css('border', '1px solid red');
        },
        unhighlight: function (element, errorClass) {
            $(element).css('border', '1px solid green');
        }
    });
});