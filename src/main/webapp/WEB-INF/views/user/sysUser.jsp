

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
        function callback() {
            alert('yes!');
        }

        $(function() {
//            $("#datatables").click(function() {
                var paramMap = {
                    /* deptName : '华北电力大学' */
                };

                var dataColumns = [
                    {'data':'id'},
                    {'data':'loginname'},
                    {'data':'password'},
                    {'data':'loginMark'},
                    {'data':"id","render": function (data, type, row) {
                        return '<div class="dropdown text-center">'
                            +'  <span class="glyphicon glyphicon-cog" data-toggle="dropdown"'
                            +' aria-haspopup="true" aria-expanded="false" style="cursor:pointer;"></span>'
                            +'  <ul class="dropdown-menu" aria-labelledby="dLabel">'
                            +'  <li><a href="#" data-toggle="modal" data-target="#deptModal" onclick="deptMain.edit(\''+row.deptId+'\')">修改</a></li> '
                            +'  <li><a href="#" onclick="deptMain.deleteInfo(\''+row.deptId+'\')">删除</a></li> '
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
                console.log(dataTable);
                dataTable.initTable(
                    'example',
                    '${ctx}/user/getAllUser',
                    paramMap,
                    dataColumns,
                    /* dataColumnDefs,  */
                    null,
                    callback);

            });
//        });


    </script>

</head>
<body>

<div class="container">
    <button id="datatables" class="btn btn-success" type="button">datatables</button>
    <form action="" id="form" method="POST">
        <table id="example" class="table table-bordered table-hover">
            <thead>
            <tr>
                <th>id</th>
                <th>姓名</th>
                <th>密码</th>
                <th>日志</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </form>
</div>

</body>
</html>
