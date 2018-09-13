<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AdminLTE 2 | General Form Elements</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <!-- daterange picker -->
  <link rel="stylesheet" href="../plugins/daterangepicker/daterangepicker-bs3.css">
  <!-- bootstrap datepicker -->
  <link rel="stylesheet" href="../plugins/datepicker/datepicker3.css">
  <!-- iCheck for checkboxes and radio inputs -->
  <link rel="stylesheet" href="../plugins/iCheck/all.css">
  <!-- Bootstrap Color Picker -->
  <link rel="stylesheet" href="../plugins/colorpicker/bootstrap-colorpicker.min.css">
  <!-- Bootstrap time Picker -->
  <link rel="stylesheet" href="../plugins/timepicker/bootstrap-timepicker.min.css">
  <!-- Select2 -->
  <link rel="stylesheet" href="../plugins/select2/select2.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="../dist/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="../dist/css/skins/_all-skins.min.css">
						
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media que	ries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>

<body class="hold-transition skin-blue sidebar-mini">
	
	<div class="box-body">
	    <div class="row">
			<div class="col-md-6">
				<div class="box box-info">
					<div class="box-header with-border">
			        	<div class="form-group">
					        <label>身份证</label>
					        <input type="text" class="form-control" placeholder="xxxxxxxxx" disabled>
				        </div>
				        <div class="form-group">
			                 <label>管理员姓名</label>
			                 <input type="text" class="form-control" placeholder="Enter ...">
			            </div>
		            </div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="box box-info" >
					<div class="box-header with-border" >
				       	<div class="form-group">
		                	<label>管理员手机号:</label>
			                <div class="input-group">
			                  <div class="input-group-addon">
			                    <i class="fa fa-phone"></i>
			                  </div>
			                  <input type="text" class="form-control" data-inputmask='"mask": "999-9999-9999"' data-mask>
			                </div>
			             </div>
			             <div class="form-group">
					        <label>邮件</label>
					        <div class="input-group">
				               <span class="input-group-addon"><i class="fa fa-envelope"></i></span>
				               <input type="email" class="form-control" placeholder="Email">
				           	</div>
			           	</div>
		           	</div>
		    	</div>
		    </div>
	    </div>
	</div>
    
    <div class="box-body">
	    <div class="row">
		    <div class="col-md-6">
				<div class="box box-info">
					<div class="box-header with-border">
				        	<div class="form-group">
						        <label>组织机构代码|三证合一码</label>
						        <input type="text" class="form-control" placeholder="12345678-3" disabled>
					        </div>
					        <div class="form-group">
			                  <label>单位网址</label>
			                  <input type="text" class="form-control" placeholder="http://www.baidu.com">
			                </div>
			                <div class="form-group">
			                  <label>主营业务</label>
			                  <input type="text" class="form-control" placeholder="Enter ...">
			                </div>
			                <div class="form-group">
			                  <label>注册资本(万元)</label>
			                  <div class="input-group">
				                <span class="input-group-addon"><i class="fa fa-dollar"></i></span>
				                <input type="text" class="form-control">
				                <span class="input-group-addon"><i class="fa fa-ambulance"></i></span>
				              </div>
			                </div>
			                <div class="form-group">
			                  <label>通信地址</label>
			                  <input type="text" class="form-control" placeholder="Enter ...">
			                </div>
			                <div class="form-group">
			                  <label>主管部门</label>
			                  <select class="form-control">
			                    <option>option 1</option>
			                    <option>option 2</option>
			                    <option>option 3</option>
			                    <option>option 4</option>
			                    <option>option 5</option>
			                  </select>
			                </div>
			                <div class="form-group">
			                  <label>领域标签</label>
				                <div class="input-group">
				                	<!-- /btn-group -->
					                <input type="text" class="form-control" disabled>
					                <div class="input-group-btn">
					                  <button type="button" class="btn btn-danger">选择</button>
					                </div>
				              	</div>
				            </div>
				            <div class="form-group">
					            <label>
					           	      科技中介机构：
				                  <input type="checkbox" class="minimal">
				                </label>
			                </div>
			                <div class="form-group">
			                  <label for="exampleInputFile">企业LOGO</label>
			                  <input type="file" id="exampleInputFile">
<!-- 			                  <p class="help-block">Example block-level help text here.</p> -->
			                </div>
			                <div class="form-group">
			                  <label for="exampleInputFile">荣誉资质</label>
			                  <input type="file" id="exampleInputFile">
<!-- 			                  <p class="help-block">Example block-level help text here.</p> -->
			                </div>
				    </div>
		    	</div>
		    </div>
		    <div class="col-md-6">
				<div class="box box-info">
					<div class="box-header with-border">
							<div class="form-group">
			                  <label>单位名称</label>
			                  <input type="text" class="form-control" placeholder="Enter ...">
			                </div>
				        	<div class="form-group">
						        <label>经营范围</label>
						        <input type="text" class="form-control" placeholder="Enter ..." >
					        </div>
					        <div class="form-group">
			                  <label>注册地址</label>
			                  <input type="text" class="form-control" placeholder="Enter ...">
			                </div>
			                 <div class="form-group">
				                <label>成立时间</label>
				                <div class="input-group date">
				                  <div class="input-group-addon">
				                    <i class="fa fa-calendar"></i>
				                  </div>
				                  <input type="text" class="form-control pull-right" id="datepicker">
				                </div>
				            </div>
			                <div class="form-group">
			                  <label>邮政编码</label>
			                  <input type="text" class="form-control" placeholder="Enter ...">
			                </div>
			                <div class="form-group">
			                  <label>行政区域</label>
			                  <select class="form-control">
			                    <option>option 1</option>
			                    <option>option 2</option>
			                    <option>option 3</option>
			                    <option>option 4</option>
			                    <option>option 5</option>
			                  </select>
			                </div>
			                <div class="form-group">
			                  <label>行业</label>
				                <div class="input-group">
				                	<!-- /btn-group -->
					                <input type="text" class="form-control" disabled>
					                <div class="input-group-btn">
					                  <button type="button" class="btn btn-danger">选择</button>
					                </div>
				              	</div>
				            </div>
				            <div class="form-group">
			                  <label>详情描述</label>
			                  <textarea class="form-control" rows="3" placeholder="Enter ..."></textarea>
			                </div>
			                <div class="form-group">
			                	<label>radio组件</label>
				                <label>
				                  <input type="radio" name="r3" class="flat-red">
				                </label>
			                </div>
				    </div>
		    	</div>
		    </div>
		</div>
	</div>
    
<!-- ./wrapper -->

<!-- jQuery 2.2.0 -->
<script src="../plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="../bootstrap/js/bootstrap.min.js"></script>
<!-- Select2 -->
<script src="../plugins/select2/select2.full.min.js"></script>
<!-- InputMask -->
<script src="../plugins/input-mask/jquery.inputmask.js"></script>
<script src="../plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="../plugins/input-mask/jquery.inputmask.extensions.js"></script>
<!-- date-range-picker -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.min.js"></script>
<script src="../plugins/daterangepicker/daterangepicker.js"></script>
<!-- bootstrap datepicker -->
<script src="../plugins/datepicker/bootstrap-datepicker.js"></script>
<!-- bootstrap color picker -->
<script src="../plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
<!-- bootstrap time picker -->
<script src="../plugins/timepicker/bootstrap-timepicker.min.js"></script>
<!-- SlimScroll 1.3.0 -->
<script src="../plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- iCheck 1.0.1 -->
<script src="../plugins/iCheck/icheck.min.js"></script>
<!-- FastClick -->
<script src="../plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="../dist/js/app.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="../dist/js/demo.js"></script>
<!-- Page script -->
<script>
  $(function () {
    //Initialize Select2 Elements
    $(".select2").select2();

    //Datemask dd/mm/yyyy
    $("#datemask").inputmask("dd/mm/yyyy", {"placeholder": "dd/mm/yyyy"});
    //Datemask2 mm/dd/yyyy
    $("#datemask2").inputmask("mm/dd/yyyy", {"placeholder": "mm/dd/yyyy"});
    //Money Euro
    $("[data-mask]").inputmask();

    //Date range picker
    $('#reservation').daterangepicker();
    //Date range picker with time picker
    $('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'MM/DD/YYYY h:mm A'});
    //Date range as a button
    $('#daterange-btn').daterangepicker(
        {
          ranges: {
            'Today': [moment(), moment()],
            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
            'This Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
          },
          startDate: moment().subtract(29, 'days'),
          endDate: moment()
        },
        function (start, end) {
          $('#daterange-btn span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
        }
    );

    //Date picker
    $('#datepicker').datepicker({
      autoclose: true
    });

    //iCheck for checkbox and radio inputs
    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
      checkboxClass: 'icheckbox_minimal-blue',
      radioClass: 'iradio_minimal-blue'
    });
    //Red color scheme for iCheck
    $('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red').iCheck({
      checkboxClass: 'icheckbox_minimal-red',
      radioClass: 'iradio_minimal-red'
    });
    //Flat red color scheme for iCheck
    $('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
      checkboxClass: 'icheckbox_flat-green',
      radioClass: 'iradio_flat-green'
    });

    //Colorpicker
    $(".my-colorpicker1").colorpicker();
    //color picker with addon
    $(".my-colorpicker2").colorpicker();

    //Timepicker
    $(".timepicker").timepicker({
      showInputs: false
    });
  });
</script>
</body>
</html>