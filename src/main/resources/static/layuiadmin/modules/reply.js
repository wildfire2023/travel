/**

 @Name：layuiAdmin 社区系统
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：LPPL

 */


layui.define(['table', 'form'], function (exports) {
    var $ = layui.$
        , table = layui.table
        , form = layui.form
        , admin = layui.admin;

    //帖子管理
    table.render({
        elem: '#LAY-app-forum-list'
        , url: '/travel-essay/reply-list' //模拟接口
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'userHeadImgUrl', title: '头像', width: 100, templet: '#imgTpl'}
            , {field: 'userNickname', title: '回复人'}
            , {field: 'title', title: '回复内容'}
            , {field: 'createTime', title: '回复时间', sort: true}
            , {field: 'tag', title: '回复类型', minWidth: 80, align: 'center'}
            , {title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-forum-list'}
        ]]
        , page: true
        , limit: 10
        , limits: [10, 15, 20, 25, 30]
        , text: {
            none: "搜索的内容不存在"
        }
    });

    //监听工具条
    table.on('tool(LAY-app-forum-list)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('确定删除此条帖子？', function (index) {
                obj.del();
                var arr = [];
                arr.push(obj.data);
                admin.req({
                    url: '/travel-essay/reply-batch-delete'
                    , type: 'post'
                    , contentType: 'application/json'
                    , data: JSON.stringify(arr)
                });
                layer.close(index);
            });
        } else if (obj.event === 'edit') {}    });

    exports('reply', {})
});