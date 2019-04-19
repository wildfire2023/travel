/**

 @Name：layuiAdmin（iframe版） 设置
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License: LPPL

 */

layui.define(['form', 'upload'], function (exports) {
	var $ = layui.$
			, layer = layui.layer
			, laytpl = layui.laytpl
			, setter = layui.setter
			, view = layui.view
			, admin = layui.admin
			, form = layui.form
			, upload = layui.upload;

	var $body = $('body');

	//自定义验证
	form.verify({
		nickname: function (value, item) { //value：表单的值、item：表单的DOM对象
			if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
				return '用户名不能有特殊字符';
			}
			if (/(^\_)|(\__)|(\_+$)/.test(value)) {
				return '用户名首尾不能出现下划线\'_\'';
			}
			if (/^\d+\d+\d$/.test(value)) {
				return '用户名不能全为数字';
			}
		}

		//我们既支持上述函数式的方式，也支持下述数组的形式
		//数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
		, pass: [
			/^[\S]{6,12}$/
			, '密码必须6到12位，且不能出现空格'
		]

		//确认密码
		, repass: function (value) {
			if (value !== $('#LAY_password').val()) {
				return '两次密码输入不一致';
			}
		}
	});

	//网站设置
	form.on('submit(set_website)', function (obj) {
		layer.msg(JSON.stringify(obj.field));

		//提交修改
		/*
		admin.req({
			url: ''
			,data: obj.field
			,success: function(){

			}
		});
		*/
		return false;
	});

	//邮件服务
	form.on('submit(set_system_email)', function (obj) {
		layer.msg(JSON.stringify(obj.field));

		//提交修改
		/*
		admin.req({
			url: ''
			,data: obj.field
			,success: function(){

			}
		});
		*/
		return false;
	});


	//设置我的资料
	form.on('submit(setmyinfo)', function (obj) {
		// layer.msg(JSON.stringify(obj.field));

		//提交修改
		admin.req({
			url: '/user/modify-info'
			, type: 'put'
			, data: obj.field
			, success: function (res) {
				if (res.code === 200) {
					location.reload();
				}
			}
		});
		return false;
	});

	//上传头像图片
	var avatarSrc = $('#LAY_avatarSrc');
	upload.render({
		url: '/file/upload?way=user'
		, elem: '#LAY_avatarUpload'
		, done: function (res) {
			if (res.code === 200) {
				avatarSrc.val(res.data);
				// 刷新当前页面
				// location.reload();
				layer.msg(res.message);
			} else {
				layer.msg(res.message, {icon: 5});
			}
		}
	});

	//上传文章头图
	var headImgSrc = $('#LAY_headImgSrc');
	upload.render({
		url: '/file/upload?way=article'
		, elem: '#LAY_headImgUpload'
		, done: function (res) {
			if (res.code === 200) {
				headImgSrc.val(res.data);
				// 刷新当前页面
				// location.reload();
				layer.msg(res.message);
			} else {
				layer.msg(res.message, {icon: 5});
			}
		}
	});

	//查看头像
	admin.events.avartatPreview = function (othis) {
		var src = avatarSrc.val();
		layer.photos({
			photos: {
				"title": "查看头像" //相册标题
				, "data": [{
					"src": src //原图地址
				}]
			}
			, shade: 0.01
			, closeBtn: 1
			, anim: 5
		});
	};

	// 查看头图
	admin.events.headImgPreview = function (othis) {
		var src = headImgSrc.val();
		layer.photos({
			photos: {
				"title": "查看头图" //相册标题
				, "data": [{
					"src": src //原图地址
				}]
			}
			, shade: 0.01
			, closeBtn: 1
			, anim: 5
		});
	};


	//设置密码
	form.on('submit(setmypass)', function (obj) {
		// layer.msg(JSON.stringify(obj.field));

		//提交修改
		admin.req({
			url: ''
			, data: obj.field
			, success: function () {

			}
		});
		return true;
	});

	//对外暴露的接口
	exports('set', {});
});