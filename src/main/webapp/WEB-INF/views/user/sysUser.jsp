

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="${pagecontext.request.getcontextpath}/common/common.jsp"/>
<html>
<head>

    <link rel="stylesheet" href="${ctx}/fonts/glyphicons-halflings-regular.woff" />
    <link rel="stylesheet" href="${ctx}/fonts/glyphicons-halflings-regular.woff2" />
    <link rel="stylesheet" href="${ctx}/fonts/glyphicons-halflings-regular.ttf" />
    <link rel="stylesheet" href="${ctx}/css/jquery.dataTables.min.css" />
    <link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${ctx}/css/bootstrap-theme.min.css" />
    <script type="text/javascript" src="${ctx}/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/ajax-datatables.js"></script>
    <script>



        <%--执行成功后的，回调函数--%>
       function callback() {

        }

        $(function() {



                /*传参，后台可接参数,必须在ajax里面传参*/
                var paramMap = {id:$("#abc").val()};

                var dataColumns = [
                    {'data':'id'},
                    {'data':'loginname'},
                    {'data':'password'},
                    {'data':'loginMark'},
                    {'data':'createtime',"render":function (data, type, row, meta) {
                        return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss");

                    }},
                    {'data':"id","render": function (data, type, row) {
                        return '<div class="dropdown text-center">'
                            +'  <span class="glyphicon glyphicon-cog" data-toggle="dropdown"'
                            +' aria-haspopup="true" aria-expanded="false" style="cursor:pointer;"></span>'
                            +'  <ul class="dropdown-menu" aria-labelledby="dLabel">'
                            +'  <li><a href="#" data-toggle="modal" data-target="#deptModal" onclick="deptMain.edit(\''+row.id+'\')">修改</a></li> '
                            +'  <li><a href="#" onclick="deptMain.deleteInfo(\''+row.id+'\')">删除</a></li> '
                            +'  </ul>'
                            +'</div>';
                    }
                    }
                ];

                var dataColumnDefs = [
                    { targets: [0, 1], visible: true},
                    { targets: 3, "searchable": false },
                    { targets: 4, visible: false},
                ]
//                console.log(dataTable);
                dataTable.initTable(
                    'example',
                    '${ctx}/user/getAllUser',
                    paramMap,
                    dataColumns,
                    /* dataColumnDefs,  */
                    null,
                    callback);

            });

        //时间格式化
        Date.prototype.Format = function(fmt) { //author: meizz
            var o = {
                "M+": this.getMonth() + 1,
                //月份
                "d+": this.getDate(),
                //日
                "h+": this.getHours(),
                //小时
                "m+": this.getMinutes(),
                //分
                "s+": this.getSeconds(),
                //秒
                "q+": Math.floor((this.getMonth() + 3) / 3),
                //季度
                "S": this.getMilliseconds() //毫秒
            };
            if (/(y+)/.test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            }
            for (var k in o) {
                if (new RegExp("(" + k + ")").test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                }
            }
            return fmt;
        }

        //配置DataTables默认参数
        $.extend(true, $.fn.dataTable.defaults, {
            "dom": "<'row'<'col-md-6'l<'#toolbar'>><'col-md-6'f>r>" +
            "t" +
            "<'row'<'col-md-5 sm-center'i><'col-md-7 text-right sm-center'p>>"
        });

    </script>

</head>
<body>

<div class="container">
    <span> <input type="text" value="" id="abc"></span>
    <form action="" id="form" method="POST">
        <table id="example" class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th>id</th>
                <th>姓名</th>
                <th>密码</th>
                <th>日志</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </form>
</div>

</body>
</html>
