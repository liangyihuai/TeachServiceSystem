/*Created by dust on 2015/12/29.*/
$(function () {
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
