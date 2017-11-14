<!DOCTYPE html>

<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>404</title>
    <script src="${rc.contextPath}/service/webjars/ldf/js/jquery.js"></script>
    <!-- 需要引用的CSS -->
    <link href="${rc.contextPath}/service/webjars/ldf/css/font-awesome.css" rel="stylesheet">
    <link href="${rc.contextPath}/service/webjars/ldf/css/ui.css" rel="stylesheet">
    
	<style type="text/css">
	body {
		background-color: #fff;
	}
	.content {
		background-image: url("${rc.contextPath}/service/skin/platform/img/404.png");
		background-repeat: no-repeat;
		margin: 5% auto;
		width: 590px;
		height: 380px;
	}
	.content .fa {
		margin-right: 6px;
		font-size: 18px;
	}
	ul {
		position: relative;
		top: 280px;
		padding-left: 20px;
		padding-top: 10px;
		font-family: Microsoft yahei;
	}
	li {
		line-height: 30px;
		margin-left: 190px;
		list-style:none;
	}
	a {
		color: #027bff;
		text-decoration: none;
	}
	a:hover {
		color: blue;
		text-decoration: underline;
	}
	</style>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="${rc.contextPath}/service/js/html5shiv.js"></script>
      <script src="${rc.contextPath}/service/js/respond.js"></script>
    <![endif]-->
  </head>
  <body>
	<!-- 页面结构 -->
	<div class="content">
		<ul>
			<li><a href="javascript:window.location.reload();"><i class="fa fa-angle-double-left"></i>刷新一下</a></li>
			<li><a href="javascript:history.go(-1);"><i class="fa fa-angle-double-left"></i>返回上一页</a></li>
		</ul>
	</div>
    <!-- 需要引用的JS -->
    <script type="text/javascript">
	    var tmpUrl = "${rc.contextPath}/service/skin/platform/img/404.png";
	    $(".content").css('background-image',"url("+tmpUrl+")");
    </script>
  </body>
</html>