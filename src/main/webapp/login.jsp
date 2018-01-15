
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="${pagecontext.request.getcontextpath}/common/common.jsp"/>


<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

    <title> - 登录</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/css/animate.css" rel="stylesheet">
    <link href="${ctx}/css/style.css" rel="stylesheet">
    <link href="${ctx}/css/login.css" rel="stylesheet">


    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>
        if (window.top !== window.self) {
            window.top.location = window.location;
        }

        $(function(){
           $("#form").validate({
               submitHandler:function(){
                   $.post("${ctx}/user/checkLoginNameAndPassoword",$("#form").serialize(),function(data){
                       if(data.ret){
                           window.location.href="";
                       }else{
                           alert("用户名或密码不正确");
                       }

                   })
               },
               rules:{
                   loginName:{
                       remote:{
                           url:"${ctx}/user/checkLoginName",
                           dataType:"json",
                           data:{ loginname:function(){ return $("input[name='loginname']").val() }  }
                       }
                   }
               },
               messages:{
                   loginName:"用户名不存在"
               }
           })
            // jQuery.validator().addMethod("login",function(){
            //
            //
            // },"用户名不存在")

        })
    </script>

</head>

<body class="signin">
<div class="signinpanel">
    <div class="row">
        <div class="col-sm-12">
            <form method="post" id="form">
                <h4 class="no-margins">登录：</h4>
                <p class="m-t-md">登录到H+后台主题UI框架</p>
                <input type="text" class="form-control uname" placeholder="用户名" name="loginname"/>
                <input type="password" class="form-control pword m-b" placeholder="密码" name="password"/>
                <a href="">忘记密码了？</a>
                <button class="btn btn-success btn-block" id="login">登录</button>
            </form>
        </div>
    </div>
    <div class="signup-footer">
        <div class="pull-left">
            &copy; hAdmin
        </div>
    </div>
</div>
</body>

</html>
