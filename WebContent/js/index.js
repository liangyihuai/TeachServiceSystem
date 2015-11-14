$(function() {
	/*取消清除输入框内容*/
	$('.modal .cancel').click(function() {
		$(this).parents('.modal').find('form').get(0).reset();
	});
	/*学生登录Ajax*/
	$('#student_login .confirm').click(function() {
		$.ajax({
			type: "post",
			url: "",
			dataType: 'json',
			data: {
				student_num: $('#student_num').val(),
				student_pass: $('#student_pass').val()
			},
			success: function(data, textStatus) {
				alert(data);
			}
		});
	});
	/*老师登录Ajax*/
	$('#teacher_login_form .confirm').click(function() {
		$.ajax({
			type: "post",
			url: "../login?operate=teacherLogin",
			data: {
				username: $('#teacher_num').val(),
				password: $('#teacher_pass').val()
			},
			success: function(data, statusText) {
				var jsondata=$.parseJSON(data);
				if(jsondata.status==1){
					localStorage.currentUser=$('#teacher_num').val();
					window.location.href = "course-list.html";
				}else{
					alert('登录失败，请重试');
				}
			},
		});
	});
	/*老师登录初始化*/
	$('#teacher_login_modal').modal();
	$('#teacher_login_modal').on('hidden.bs.modal', function() {
			alert('欢迎登录');
			$('#course_link').trigger('click');
		})
		/*作业布置截至时间选框*/
	$('#homeworkStopTime').datetimepicker({
		format: 'yyyy-mm-dd',
		minView: 2,
		autoclose: true,
		language: 'zh-CN'
	});
	/*教学计划ajax*/
	$('#addPlan .confirm').click(function() {
		$.ajax({
			type: "post",
			url: "",
			dataType: 'json',
			data: {
				stopTime: $('#plan_time').val(),
				planText: $('#plan_text').val()
			}
		});
	});
	/*课程列表获取*/
	$('#course_link').click(function() {
		$.ajax({
			type: "get",
			url: "../course?operate=getCourses",
			dataType: "json",
			/*
			 * url:课程点击后的链接
			 * courseName:课程名（交互）
			 * courseDescription:课程描述（交互）
			 */
			data:{
				teacherID:localStorage.currentUser
			},
			success: function(data, textStatus) {
				var html = '';
				var jsonData = $.parseJSON(data);
				for (var i = 0; i < jsonData.length; i++) {
					html += "<li class='col-md-4 col-sm-6 col-xs-12'><div class='course-item'><div class='course-item-cover'><a href='#'><img src='../images/"+jsonData[i].courseID+".png' class='img-responsive' /></a></div><div class='course-item-desc'><div class='course-item-desc-title'><a href='#'><h3>" + jsonData[i].courseName + "</h3></a></div><div class='course-item-desc-text'><p>" + jsonData[i].courseDescription + "</p></div></div></div></li>";
				}
				$('.course .row').empty().append(html);
			}
		});
	});
});