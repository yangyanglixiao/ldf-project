<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>error</title>
    <script src="${rc.contextPath}/service/webjars/ldf/js/jquery.js"></script>
    <!-- 需要引用的CSS -->
    <link href="${rc.contextPath}/service/webjars/ldf/css/bootstrap.css" rel="stylesheet">
    <link href="${rc.contextPath}/service/webjars/ldf/css/font-awesome.css" rel="stylesheet">
    <link href="${rc.contextPath}/service/webjars/ldf/css/ui.css" rel="stylesheet">
   <style type="text/css">
   	body {
		background-color: #fff;
	}
	.imginfo {
		background-image: url("${rc.contextPath}/service/skin/platform/img/errorpage.png");
		background-repeat: no-repeat;
		margin: 5% auto;
		margin-bottom: 0px;
		width: 80%;
		height: 200px;
	}
	.detailinfo {
	    display: none;
	    margin: 0 auto;
	    width: 80%;
	    height: 300px;
	    overflow: auto;
	    border: 1px solid #ddd;
	    border-weight: bold;
	}
    .ue-btn {
    	float: right;
		margin-right: 25%;
		background-color: #30c0d0;
		color: #fff;
		border-style: none;
	}
	.btn.ue-btn:hover, .btn.ue-btn:focus {
	    color: #fff;
	    background-color: #42d2e2;
	}
    </style>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="${rc.contextPath}/service/js/html5shiv.js"></script>
      <script src="${rc.contextPath}/service/js/respond.js"></script>
    <![endif]-->
  </head>
   <body style="margin: 0;padding: 0;">  
    <div class="container">
    	<div class="imginfo"></div>
    	<a class="btn ue-btn pull-right" id="infobtn" >详细信息</a>
    	<div class="detailinfo" id="info">
    	  <%-- <%= exception.getMessage()%>  --%>
    	</div>
    </div>
    
    <!-- 需要添加的JS -->
	<script  type="text/javascript" src="<l:asset path='jquery.js'/>"></script>
	<script>
		var tmpUrl = "${rc.contextPath}/service/skin/platform/img/errorpage.png";
	    $(".content").css('background-image',"url("+tmpUrl+")");
		$(document).ready(function(){
			$("#infobtn").click(function(){
				$(this).next().slideToggle(800);
			})
		})
	</script>
</body>
</html>