if (!window.kjcs) {
	var kjcs = {};

};

kjcs.testPaginCode = (function(win) {
	
	var _this = null;
	var paginCode = function(options) {
	this.options = options;
	this.DefaultsGrid = {
			htmlBox:null,                           // dom元素id
	        url: null,                             //ajax url
	        data: null,                            //每次加载的数据
	        page: 0,                                //默认当前页 
	        pageSize: 5,                           //每页默认的结果数
	        params: {},                              //提交到服务器的参数 
	        columns: [],                          //数据源
			rowId : 'id',                     //唯一标识
			Topn :5,                        // 分页导航条数
			searchParam :[],                     //搜索参数
			pageSizeOptions:[5, 10, 15, 20, 25],  //可选择设定的每页结果数
			myfunction :{},
			render:null,
			checkbox:false,
			
			
			
	    };
		
		
	this.DefaultsGrid_columns = {
	        name: null,
	        display: null,
	        type: null,
	        width: 120,
	        minWidth: 80,
	        align: 'left',
	        render:null,             //和default_render同时使用时优先使用
			default_render:null,   
	    };
		this.pageCount = null;               //总共多少页数据
		this.resultCount = null;             //总共多少条数据
		this.actualPageSize = null;          //实时返回的页面数据条数
		_this = this;
		init(true);
		this.choicerowdata={};                    //选择后数据
		
	};
	paginCode.prototype = {
			
		callback : function(back){
			this.callback =back;
		},
		setParam : function(pageparam){
			this.pageparam = pageparam;
			
		},
		/*外部初始化*/
		_init:function( params){
			if(!!params){
				_this.DefaultsGrid.params = params;
			}	
			_this.DefaultsGrid.page = _this.options.page;
			loadServerData(null,null,null,false);
			
			$("#pagination2").jqPaginator('option', {
		    currentPage: _this.DefaultsGrid.page+1
			});
		}
	};
	
	/**
	 * 1  调去后台数据
	 * 2、 生成html代码；
	 * 
	 * 
	 * 
	 * **/
	function init(flag){
		if(flag){
			
			setColumns();
			$.extend(_this.DefaultsGrid,_this.options);	
			
		}  
		$("#"+_this.DefaultsGrid.htmlBox).html("");
		createSearchHtml(_this.DefaultsGrid.searchParam);
		
		 loadServerData(null,null,null,true);
		
	}

	/*设置Columns*/
	function setColumns(){
		var columns =[];
		for (var i in _this.options.columns){
			var t ={};
			 for(var j in _this.DefaultsGrid_columns){
				 if(!_this.options.columns[i][j]){
					 _this.options.columns[i][j] =_this.DefaultsGrid_columns[j] ;
				 }
						
			}
			
		}
		return columns;
	}
	/*搜索form*/
	function createSearchHtml(searchParam){
		if(searchParam.length>0){
			var _html_Search = "<form>";
			for (var i in searchParam){
				_html_Search +=searchParam[i].display+"<input id = '"+searchParam[i].name+"'type ='text' value =''/>";
			}
			_html_Search  +="<input type = 'button' value ='搜索' class ='button'/>";
			_html_Search +="</form>"
		}
		$("#searchBox").html(_html_Search);
		
		$("#searchBox").on('click','[type =button]',function(){
			$("#searchBox form [type =text]").each(function(){
				var _key =$(this).attr("id");
				var _value =$(this).val();
				_this.DefaultsGrid.params[_key]= _value;
				init(false);
				
			});
		});
	}
	/*后台加载数据*/
	  function  loadServerData(params,callback,url,flag){
		  var HtmlMenuOpt ={endpoint:_this.DefaultsGrid.url,
			  		data:_this.DefaultsGrid.params,
			  		success:function(data){
			  			_this.pageCount =data.pageCount;
			  			_this.DefaultsGrid.Ton = _this.DefaultsGrid.Ton>_this.pageCount?_this.DefaultsGrid.Ton:_this.pageCount;
			  			_this.resultCount = data.resultCount;
			  			_this.DefaultsGrid.page = data.pageIndex;
			  			_this.actualPageSize = data.data.length;
			  		    _this.DefaultsGrid.data =data.data;
						var $_html_tbody = createHtml_tbody(data.data);
				         if(!flag){
							 $("#"+_this.DefaultsGrid.htmlBox+" tbody").replaceWith($_html_tbody);
							 setPageData ();
							 $("#pagination2").jqPaginator('option', {
								 totalPages: _this.pageCount,
								 visiblePages: _this.DefaultsGrid.Topn,
								 currentPage: _this.DefaultsGrid.page+1,
									});
						 }else{
							
							  createHtml(_this.DefaultsGrid.data);
						 }
			  			},
			  		failure:function(data){
			  			console.log(data);
			  			},
			  		page:_this.DefaultsGrid.page,
			  		size:_this.DefaultsGrid.pageSize,
			  		};
		  
		
		  kjcs.ajaxEnt.getRequest(HtmlMenuOpt);
		  
				
			};
			
	/*表单中表头*/
	function  createHtml(data){
	
		
		var $_html_table = $("<table  id='kjcs_grid' class='table table-bordered table-hover  ta_custom'></table>");
		var columns = _this.DefaultsGrid.columns;
		
		var $_html_thead = createHtml_thead(columns);
		$_html_table.append($_html_thead);
		
		var $_html_tbody = 	createHtml_tbody(data);
		$_html_table.append($_html_tbody);
		
		                                                                                                                                                                                                               
		//$("#"+_this.DefaultsGrid.htmlBox).append($_html_table).append("<div class='col-sm-5'><div class='dataTables_info' id='pageSizeOptions' role='status' aria-live='polite'>每页显示：<select ></select> <span id = 'checkData'></sapn></div></div> <div class='col-sm-7'><div class='dataTables_paginate paging_simple_numbers' id='example2_paginate'><ul class='pagination' id='pagination2'></ul></div></div>");
		$("#"+_this.DefaultsGrid.htmlBox).html($_html_table).append("<div class='col-sm-5'><div class='dataTables_info' id='pageSizeOptions' role='status' aria-live='polite'>每页显示：<select ></select> <span id = 'checkData'></sapn></div></div> <div class='col-sm-7'><div class='dataTables_paginate paging_simple_numbers' id='example2_paginate'><ul class='pagination' id='pagination2'></ul></div></div>");
		
		setPageData ();
		getpagination();
		setSelectData ();
		
		if(_this.options.checkbox){
			/*选择select框后重新初始化数据*/
			$('#pageSizeOptions').on("change","select",function(){
				
				_this.DefaultsGrid.pageSize = $(this).children('option:selected').val();
				_this.DefaultsGrid.page = 0;
				 _this.choicerowdata = {};
				 $('table tr > th:first-child input:checkbox')[0].checked = false;
				 setPageData ();
				loadServerData(null,null,null,false);
			});
			
			
		}
		
		
		/*checkbox的全选 和全取消* 和点击"td"中checkbox  取消"th"中的checkbox */
	/*	$('body').on('click', 'table tr .icheckbox_minimal-blue ', function () {
	        
			setcheckBoxDataCss(this) 
    });*/
		 
	}
	
	/*checkbox的全选 和全取消* 和点击"td"中checkbox  取消"th"中的checkbox */
	/*设置checked样式*/
	function setcheckBoxDataCss(obj){
		console.log(obj);
		
		
			var that = obj;
	        var flag = false;
	        var _tmp_className = $(obj).attr("class");
	        if(_tmp_className.indexOf("checked")>0){
	        	$(obj).removeClass("checked").attr("aria-checked",false).children("input")[0].checked=false;
	        	flag = false;
	        }else{
	        	$(obj).addClass("checked").attr("aria-checked",true).children("input")[0].checked=true;
	        	flag = true;
	        }
        
	        if($($(obj).parent()).is("th")){
	        	
	        	$(obj).closest('table').find('tr > td:first-child input:checkbox').each(function () {	
	        		this.checked = flag;
					if(flag){
						$(this).parent().addClass("checked").attr("aria-checked",true);
					}else{
						$(this).parent().removeClass("checked").attr("aria-checked",false);
					}
	        	});
	       }else{
			 
			if(!flag){
				 var tmp = $(obj).closest('table').find('tr > th input:checkbox');
				     tmp.parent().removeClass("checked").attr("aria-checked",false);
				     tmp[0].checked = flag;
					$(obj).parent().removeClass("checked").attr("aria-checked",false);
				}else{
					$(obj).parent().addClass("checked").attr("aria-checked",true);
				}
		}
	        
	        getRowData()
	}
	
	/*获取checked选择后的rowData*/
	function getRowData(){
		var choicerowdata={};
		$('table tr > td:first-child input:checkbox').each(function(){
			if(this.checked){
				choicerowdata[$(this).attr("rowindex")] = _this.DefaultsGrid.data[$(this).attr("rowindex")];
			}
		});
		_this.choicerowdata = choicerowdata;
	}
	
   
	/*表中thead数据*/
	function createHtml_thead(columns){
		var $_html_thead = $("<thead><tr ></tr></thead>");
		
		if(_this.DefaultsGrid.checkbox){
			var $_tmp_checkBox = $("<th style = 'width:1%'><div class='icheckbox_minimal-blue ' aria-checked='false' aria-disabled='false' style='position: relative;'><input type='checkbox' rowindex='0' style='position: absolute; opacity: 0;'><ins class='iCheck-helper' style='position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; border: 0px; opacity: 0; background: rgb(255, 255, 255);'></ins></div></th>");
			$_html_thead.children("tr").append($_tmp_checkBox);
			$_tmp_checkBox.on("click",function(){
				var t = $(this).find(".icheckbox_minimal-blue");
				setcheckBoxDataCss(t)
			});
		
		}
		for(var i in columns){
			var $_tmp_thead =$("<th style='text-align:"+columns[i].align+";width:"+columns[i].width+"px'></th>");
			$_tmp_thead.html(columns[i].display)
			$_html_thead.children("tr").append($_tmp_thead);
		}
		
		return $_html_thead;
	}
	
	/*设置select框的默认值*/
	function setSelectData (){
		var _html_select = "";
		for(var i in _this.DefaultsGrid.pageSizeOptions){
			_html_select += "<option value="+_this.DefaultsGrid.pageSizeOptions[i]+">"+_this.DefaultsGrid.pageSizeOptions[i]+"条</option>"
		}
		$("#pageSizeOptions select").html(_html_select);
		$("#pageSizeOptions option[value="+_this.DefaultsGrid.pageSize+"]").attr("selected",true);
	}
	
	
	
	/*表单中_tbody*/
	
	function createHtml_tbody(data){
		var $_html_tbody = $("<tbody></tbody>");	
		var columns = _this.DefaultsGrid.columns;
		var _data=data;
		
		for(var i in _data){
			var $_tmp_tbody_tr = $("<tr></tr>");
			$_tmp_tbody_tr.on("click",function(){
				var t = $(this).find(".icheckbox_minimal-blue");
				setcheckBoxDataCss(t)
			});
			if(_this.DefaultsGrid.checkbox){
				var $_tmp_checkBox = $("<td ><div class='icheckbox_minimal-blue ' aria-checked='false' aria-disabled='false' style='position: relative;'><input type='checkbox' rowindex='"+i+"'  style='position: absolute; opacity: 0;'><ins class='iCheck-helper' style='position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; border: 0px; opacity: 0; background: rgb(255, 255, 255);'></ins></div></td>");
				$_tmp_tbody_tr.append($_tmp_checkBox);
		}
			for(var j in columns){
				var $_tmp_tbody_td = $("<td></td>");
				if(!!columns[j].render){
					var content =_getCustomtHtml(_data[i],columns[j].render );
					$_tmp_tbody_td.css("text-align",columns[j].align).css("width",columns[j].width+"px").html(content);
				
				}else if(!!columns[j].default_render){
					var content =_getDefaultHtml(_data[i],columns[j].default_render );
					$_tmp_tbody_td.css("text-align",columns[j].align).css("width",columns[j].width+"px").html(content);
				}else{
				  $_tmp_tbody_td.css("text-align",columns[j].align).css("width",columns[j].width+"px").html(_data[i][columns[j].name]);
				
				}
				$_tmp_tbody_tr.append($_tmp_tbody_td);	
			}
			$_html_tbody.append($_tmp_tbody_tr);
		}
		return $_html_tbody;
	}
	/*刷新分页数据*/
	function setPageData (){
		$("#checkData").html("显示："+((_this.DefaultsGrid.page*_this.DefaultsGrid.pageSize)+1)+"至"+((_this.DefaultsGrid.page*_this.DefaultsGrid.pageSize)+_this.actualPageSize)+"共"+_this.resultCount+"条记录。");
	};
	
	/*获取外部自定义操作的html*/
	 function _getDefaultHtml(rowdata, column){
		 
         if (!rowdata || !column) return "";
         var $_button =$("<div style ='text-align: center;'></div>");
		 for(var i in column){		  
			 var $tmp_button =$("<button b_type ='"+i+"' class ='btn btn-primary btn-xs '>"+column[i].title+"</button >&nbsp;&nbsp;");
			 if(i=="del"){
				 $tmp_button.addClass("btn-danger");
				// $tmp_button.css("color", "red");
			 }
			 $tmp_button.on('click',function(){
				
				 column[$(this).attr("b_type")].listener.call(this,rowdata);
			 });
			 $_button.append($tmp_button);
		 }
		return $_button ;
	 };
	 
	 /*获取默认操作的html*/
	 function _getCustomtHtml(rowdata, column){
		 
         if (!rowdata || !column) return "";
		 var content = "";
         content = column.call(this, rowdata);
		return content || "";
	 };

	/*分页事件*/	
	function dofunction(pn){
		
		setPageData ();
		_this.DefaultsGrid.page = pn-1;
		loadServerData(null,null,null,false);
		
		$("#pagination2").jqPaginator('option', {
		    currentPage: pn
		});
	}
	/*分页组件*/
	function getpagination(){
		var _type =false;
		 $.jqPaginator('#pagination2', {
		    totalPages: _this.pageCount,
		    visiblePages: _this.DefaultsGrid.Topn,
		    currentPage: _this.DefaultsGrid.page+1,
		    prev: '<li class="prev"><a href="javascript:;"><</a></li>',
		    next: '<li class="next"><a href="javascript:;">></a></li>',
		    page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
		    onPageChange: function (num, type) {
		    	if(_type){
		    	dofunction(num);
		    	}
		    	_type =true;
		    }
		});
	}
	
	
	
	return paginCode;
})(window);


