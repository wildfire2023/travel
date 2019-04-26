/*
*@Name: 微新闻
*@Author: xzw
*@Copyright:layui.com
*/
layui.define(['carousel', 'jquery', 'element', 'flow', 'laytpl', 'element', 'laypage', 'form', 'util'], function (exports) {
    var carousel = layui.carousel
        , $ = layui.$
        , flow = layui.flow
        , laytpl = layui.laytpl
        , element = layui.element
        , laypage = layui.laypage
        , form = layui.form
        , util = layui.util
        , admin = layui.admin;


    // 模拟首页数据
    var index = {


        // 移动端下面导航按钮
        common: function () {
            var off = true;
            $('.menu-icon').on('click', function () {
                if (off) {
                    $('.mobile-nav').addClass('layui-show')
                } else {
                    $('.mobile-nav').removeClass('layui-show')
                }
                off = !off
            })
        },

        // 首页轮播
        banner: function () {
            //轮播
            var elemBanner = $('#micronews-carouse'), ins1 = carousel.render({
                elem: elemBanner
                , width: '100%'
                , height: elemBanner.height() + 'px'
                , arrow: 'none'
                , interval: 5000
            });
            $(window).on('resize', function () {
                var width = $(this).prop('innerWidth');
                ins1.reload({
                    height: (width > 768 ? 300 : 150) + 'px'
                });
            });
        },


        // 内容的留加载
        // contFlow: function () {
        //     flow.load({
        //         elem: '#LAY_demo2' //流加载容器
        //         , scrollElem: '#LAY_demo2' //滚动条所在元素，一般不用填，此处只是演示需要。
        //         , isAuto: false
        //         , done: function (page, next) { //加载下一页
        //             var lis = [];
        //             //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
        //             $.get('/api/list?page=' + page, function (res) {
        //                 //假设你的列表返回在data集合中
        //                 var getTpl = demo.innerHTML;
        //
        //                 layui.each(res.data, function (index, item) {
        //                     laytpl(getTpl).render(item, function (html) {
        //                         lis.push(html)
        //                     });
        //                 });
        //
        //                 //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
        //                 //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
        //                 next(lis.join(''), page < res.pages);
        //             });
        //         }
        //     });
        // },


        // 输入留言
        EnterMessage: function (user, essayId) {
            $('.micronews-details-Publish').on('click', function (e) {
                var event = e || event;
                var messagetext = $(this).siblings('.message-text').find('.txt');
                var textarea = $(this).parents('.layui-form-item').siblings('.layui-form-text').children('.layui-input-block').children('textarea');
                if (user == null) {
                    messagetext.text('请登录后发表评论');
                    return;
                }
                if (!textarea.val()) {
                    messagetext.text('请输入留言');
                    return;
                }
                var name = $(textarea).val();
                var data;
                // var img = $(this).parents('form').siblings('a').find('img').attr('src');
                var img = user.imgUrl;
                if (img === "" || img == null) {
                    data = [{
                        nickname: user.nickname,
                        createTime: '刚刚',
                        content: name
                    }];
                } else {
                    data = [{
                        imgUrl: user.imgUrl,
                        nickname: user.nickname,
                        createTime: '刚刚',
                        content: name
                    }];
                }
                // 执行留言方法
                admin.req({
                    url: '/travel-essay/add-comment'
                    , type: 'post'
                    , data: {essayId: essayId, userId: user.id, content: name}
                });
                var html = messageTpl.innerHTML;
                var view = $('.ulCommentList');
                $('.message-text .txt').text('');
                laytpl(html).render(data, function (html) {
                    view.prepend(html)
                });
                textarea.val("");
                return false;
            })
        },
        // 留言列表
        MessageList: function (essayId) {
            var page = 1;
            var limit = 10;

            admin.req({
                url: '/travel-essay/list-comments'
                , type: 'post'
                , data: {limit: limit, page: page, essayId: essayId}
                , success: function (res) {
                    // 获取节点
                    var html = messageTpl.innerHTML;
                    // 执行tpl渲染
                    laytpl(html).render(res.data, function (html) {
                        document.getElementById('view').innerHTML = html;
                    });
                    // 设置留言条数
                    $('.comment-num').text(res.count);
                    laypage.render({
                        elem: 'page'
                        , count: res.count
                        , first: '首页'
                        , last: '尾页'
                        , prev: '<em>←</em>'
                        , next: '<em>→</em>'
                        , limit: limit
                        , jump: function (obj, first) {
                            page = obj.curr;
                            limit = obj.limit;
                            if (!first) {
                                // loadData();
                                admin.req({
                                    url: '/travel-essay/list-comments'
                                    , type: 'post'
                                    , async: false
                                    , data: {
                                        limit: limit
                                        , page: page
                                        , essayId: essayId
                                    }, success: function (res) {
                                        // 获取节点
                                        var html = messageTpl.innerHTML;
                                        // 执行tpl渲染
                                        laytpl(html).render(res.data, function (html) {
                                            document.getElementById('view').innerHTML = html;
                                        });
                                    }
                                });
                            }
                        }
                    });

                }
            });
        },
        // 输入答案
        EnterAnswer: function (user, questionId) {
            $('.micronews-details-Publish').on('click', function (e) {
                var event = e || event;
                var messagetext = $(this).siblings('.message-text').find('.txt');
                var textarea = $(this).parents('.layui-form-item').siblings('.layui-form-text').children('.layui-input-block').children('textarea');
                if (user == null) {
                    messagetext.text('请登录后发表答案');
                    return;
                }
                if (!textarea.val()) {
                    messagetext.text('请输入答案');
                    return;
                }
                var name = $(textarea).val();
                var data;
                // var img = $(this).parents('form').siblings('a').find('img').attr('src');
                var img = user.imgUrl;
                if (img === "" || img == null) {
                    data = [{
                        nickname: user.nickname,
                        createTime: '刚刚',
                        content: name
                    }];
                } else {
                    data = [{
                        imgUrl: user.imgUrl,
                        nickname: user.nickname,
                        createTime: '刚刚',
                        content: name
                    }];
                }
                // 执行留言方法
                admin.req({
                    url: '/question/add-answer'
                    , type: 'post'
                    , data: {questionId: questionId, userId: user.id, content: name}
                });
                var html = messageTpl.innerHTML;
                var view = $('.ulCommentList');
                $('.message-text .txt').text('');
                laytpl(html).render(data, function (html) {
                    view.prepend(html)
                });
                textarea.val("");
                return false;
            })
        },
        // 答案列表
        AnswerList: function (questionId) {
            var page = 1;
            var limit = 10;

            admin.req({
                url: '/question/list-answers/'
                , type: 'post'
                , data: {limit: limit, page: page, questionId: questionId}
                , success: function (res) {
                    // 获取节点
                    var html = messageTpl.innerHTML;
                    // 执行tpl渲染
                    laytpl(html).render(res.data, function (html) {
                        document.getElementById('view').innerHTML = html;
                    });
                    // 设置留言条数
                    $('.comment-num').text(res.count);
                    laypage.render({
                        elem: 'page'
                        , count: res.count
                        , first: '首页'
                        , last: '尾页'
                        , prev: '<em>←</em>'
                        , next: '<em>→</em>'
                        , limit: limit
                        , jump: function (obj, first) {
                            page = obj.curr;
                            limit = obj.limit;
                            if (!first) {
                                // loadData();
                                admin.req({
                                    url: '/question/list-answers/'
                                    , type: 'post'
                                    , async: false
                                    , data: {
                                        limit: limit
                                        , page: page
                                        , questionId: questionId
                                    }, success: function (res) {
                                        // 获取节点
                                        var html = messageTpl.innerHTML;
                                        // 执行tpl渲染
                                        laytpl(html).render(res.data, function (html) {
                                            document.getElementById('view').innerHTML = html;
                                        });
                                    }
                                });
                            }
                        }
                    });

                }
            });
        },
        onInput: function () {
            // if (!("oninput" in document.body)) {
            // element.onpropertychange = function() {
            //   if (event.propertyName == "value")
            //     this.oninput && this.oninput(event);
            //   }
            // }
            $("#onInput").bind("input propertychange", function (event) {
                if (!$(this).val()) {
                    $('.message-text .txt').text('');
                    return;
                }
                $('.message-text .txt').text('已经输入' + $(this).val().length + '字')
            });


        }
        ,
        //超出多少文件就省略号
        omitted: function (ele, num) {
            $(ele).each(function () {
                var str = $(this).html;
                var subStr = str.substring(0, num);
                $(this).html(subStr + (str.length > num ? '...' : ''));
            });
        },

        //搜索跳转的页面
        seachBtn: function () {
            $('.search-btn').on('click', function () {
                var inpVal = $(this).siblings('input').val();
                // 搜索条件不为空串
                if (inpVal !== '') {
                    var url = "/front/page/search-page";
                    // window.open(encodeURI(url + "?pattern=" + inpVal));
                    window.location.href = encodeURI(url + "?pattern=" + inpVal);
                    // window.location.href = "/front/page/search-page?pattern=" + inpVal;
                }
                return false;
            })
        },


        //获取验证码事件
        login: function () {
            var data = {
                status: true
            };
            $("#veriCodeBtn").click(function () {
                if (!/^1\d{10}$/.test($("#phone").val())) {
                    layer.msg("请输入正确的手机号");
                    return false;
                }
                if (!$("#imgCode").val()) {
                    layer.msg("请输入验证码");
                    return false;
                }
                var obj = this;
                if (data.status) {
                    $(obj).addClass(" layui-btn-disabled");
                    $(obj).attr('disabled', "true");
                    settime(obj);
                }
            });
            var countdown = 60;

            function settime(obj) {
                if (countdown == 0) {
                    obj.removeAttribute("disabled");
                    obj.classList.remove("layui-btn-disabled")
                    obj.value = "获取验证码";
                    countdown = 60;
                    return;
                } else {

                    obj.value = "重新发送(" + countdown + ")";
                    countdown--;
                }
                setTimeout(function () {
                    settime(obj)
                }, 1000)
            }

            form.on('submit(*)', function (data) {
                window.location.href = 'index.html';
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            })
        }

        // 右侧小箭头
        , arrowutil: function () {
            //固定 bar
            util.fixbar({
                click: function (type) {
                    if (type === 'bar1') {
                        //
                    }
                }
            });
        }

    };

    index.common();
    exports('news', index);
});