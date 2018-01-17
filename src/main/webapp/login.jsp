
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="${pagecontext.request.getcontextpath}/common/common.jsp"/>
<script src="${ctx}/js/verifyCode.js"></script>


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


</head>

<body class="signin">
<div class="signinpanel">
    <div class="row">
        <div class="col-sm-12">
            <form method="post" id="form">
                <h4 class="no-margins">登录：</h4>
                <p class="m-t-md">登录到moment后台主题UI框架</p>
                <input type="text" class="form-control uname" placeholder="用户名" name="loginname"/>
                <input type="password" class="form-control pword m-b" placeholder="密码" name="password"/>

                <div style="display: none" id="code">
                <input type="text" name="checkValiCode" style="width: 140px" class="form-control" placeholder="验证码"/>
                　<div id="v_container" style="width: 100px;height: 30px;position: relative;top: -65px;left: 157px;">
                　　　　　　　　<canvas id="verifyCanvas" width="100" height="40" style="cursor: pointer;"></canvas></div>
                <a href="">忘记密码了？</a>
                <input type="hidden" name="valiCode" id="checkValiCode">
                </div>
                <input type="hidden" name="erroCount" id="erroCount" value="0">

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
<script>

    if (window.top !== window.self) {
        window.top.location = window.location;
    }


    $(function(){
        $("#form").validate({
            submitHandler:function(){
                $.post("${ctx}/user/checkLoginNameAndPassoword",$("#form").serialize(),function(data){
                    if(data.flag){

                        window.location.href="${ctx}/user/main";
                    }else{
                        $("#erroCount").val(data.erroCount);
                        if(data.erroCount>2){
                            $("#code").show();
                        }
                        alert(data.message);
                    }
                })
            },
            rules:{
                loginname:{
                    remote:{
                        url:"${ctx}/user/checkLoginName",
                        dataType:"json",
                        data:{ loginname:function(){ return $("input[name='loginname']").val() }  }
                    }
                }
            },
            messages:{
                loginname:"用户名不存在"
            }
        })
        // jQuery.validator().addMethod("login",function(){
        //
        //
        // },"用户名不存在")
        var verifyCode = new GVerify("v_container");
    })


</script>
</html>
