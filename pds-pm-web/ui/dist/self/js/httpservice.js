// 初始化httpservice对象
if (!window.httpservice) {
	httpservice = {}
}

// 为httpservice对象初始化成员工具类
httpservice.tools = {
	alert : function(level, msg) {
		try {
			eval("alertTool." + level + "('" + msg + "')");
		} catch (e) {
			alert(msg);
		}
	}
}


httpservice.cookie={
	getKey : function(){
		return "manage_project_key"
	},
    setCookie : function(key,value){
        var Days = 30;
		var exp = new Date();
		exp.setTime(exp.getTime() + Days*24*60*60*1000);
		document.cookie = key + "="+ escape (value) + ";expires=" + exp.toGMTString();
    },
    getCookie : function(key){
       var arr,reg=new RegExp("(^| )"+key+"=([^;]*)(;|$)");
		if(arr=document.cookie.match(reg))
			return unescape(arr[2]);
		else
			return null;
    },
    remove : function(key){
        var exp = new Date();
		exp.setTime(exp.getTime() - 1);
		var cval=httpservice.cookie.getCookie(key);
		if(cval!=null)
		document.cookie= key + "="+cval+";expires="+exp.toGMTString();
    }
}


httpservice.token={
	set : function(token){
		window.localStorage.setItem("_token",token);
	},
	get : function(){
		var _token= window.localStorage.getItem("_token");
		return _token==null?'':_token;
	},
	remove : function(){
		window.localStorage.removeItem("_token");
        httpservice.funcPrivilege.remove();
	}
}

httpservice.ssoToken={
	set: (token)=>{
		window.localStorage.setItem("_redis_token",token);
		httpservice.cookie.setCookie(httpservice.cookie.getKey(),token);
	},
	get: ()=>{	
		var _token= window.localStorage.getItem("_redis_token");
		if(_token==null){
			_token=httpservice.cookie.getCookie(httpservice.cookie.getKey());
		}
		return _token==null?'':_token;
	},
	remove: ()=>{
		//1 
		httpservice.token.remove();
		//2

	}
}



httpservice.funcPrivilege={
	get : function(){
        var _funcPrivilege = window.localStorage.getItem("_funcPrivilege");
        return _funcPrivilege == null?[]:JSON.parse(_funcPrivilege);
	},
	set : function(funcPrivilege){
        var _funcPrivilege = JSON.stringify(funcPrivilege);
        window.localStorage.setItem("_funcPrivilege",_funcPrivilege);
	},
	remove : function(){
        window.localStorage.removeItem("_funcPrivilege");
	}
}
httpservice.errorCode={
    get : function(){
        var _errorCode = window.localStorage.getItem("_errorCode");
        return _errorCode == null?[]:JSON.parse(_errorCode);
    },
    getVal : function(key){
        var _errorCode = httpservice.errorCode.get();
        if(_errorCode.length == 0){
        	return key;
		}else{
			for(var item in _errorCode){
				if(item == key){
                    return _errorCode[key];
				}
			}
			return key;
		}
    },
    set : function(errorCode){
        var _errorCode = JSON.stringify(errorCode);
        window.localStorage.setItem("_errorCode",_errorCode);
    },
    remove : function(){
        window.localStorage.removeItem("_errorCode");
    }
}

//为httpservice对象初始化成员对象html
httpservice.html = (function(data) {
	var _internal;

	// 定义内置对象，html对象实例化后返回
	var internalFunction = function(obj) {
		_internal = obj;
	};

	// 为html内置对象初始化成员方法
	internalFunction.prototype = {
		// 初始化
		skiphtml : function(url) {
			load(url);
		},
		_dom:function(){
			return _internal.dom;
		}

	}

	function params(url){
		var _params={};
		url.substring(url.indexOf(".html?")+6)
				.split('&')
				.forEach(function(item){
					var _itm=item.split('=');
					_params[_itm[0]]=_itm[1];
				})
		return _params;
	}

	// 初始化html
	function load(url) {
		// 拉取页面
		url += (url.indexOf('?') == -1 ? '?' : '&') + "timestamp="
		+ new Date().getTime();
		$("#" + _internal.dom).data("_param_",params(url));
		$("#" + _internal.dom).load(VIEW_ROOT + url, function(response, status, xhr) {
			if(xhr.status == 400){
				// window.location.href= VIEW_ROOT;
				alertTool.error("请求发送失败!");
			}
		});
	}

	return internalFunction;
})(window);

// 为httpservice初始化成员对象 ajax
httpservice.ajax = (function(data) {
	var _internal = null;

	// ajax 内置返回对象
	var internalFunction = (function(obj) {

	});

	// 为内置对象初始化成员方法
	internalFunction.prototype = {
		submitForm : function(opt) { // 提交form

			// 转换参数
			var _opt = {
				async : opt.async !== false,
				method : 'POST',
				success : opt.success,
				failure : opt.failure,
				formObj : opt.formObj,
				endpoint : opt.endpoint
			}
			$.extend(_opt,opt);

			var form = _opt.formObj;
			var axajData = opt.data;
			var data = {};
			if(axajData){
				$.extend(data,form.serializeJson(),axajData);
			}else{
				data = form.serializeJson();
			}
			_opt.data = data;

			// submit时固定发送post请求
			doAjaxRequest(_opt);
		},
		getRequest : function(opt) { // 请求

			if (!!!opt.endpoint) {
				httpservice.tools.alert('error', 'endpoint 属性不能为空!');
				return;
			}

			var _opt = {
				async : opt.async !== false,
				//method : 'GET',
				method : 'GET',
				success : opt.success,
				failure : opt.failure,
				endpoint : opt.endpoint
			};
			$.extend(_opt,opt);
			var data = opt.data;
			if(!data){
				data = {};
			}
			data.page = opt.page || 0;
			data.size = opt.size || 0;
			_opt.data = data;
			if(!_opt.headers){
				_opt.headers={};
			}
			doAjaxRequest(_opt);
		},
		doPost : function(opt) { // 请求

			if (!!!opt.endpoint) {
				httpservice.tools.alert('error', 'endpoint 属性不能为空!');
				return;
			}

			var _opt = {
				async : opt.async !== false,
				method : 'POST',
				success : opt.success,
				failure : opt.failure,
				endpoint : opt.endpoint
			};
			$.extend(_opt,opt);
			var data = opt.data;
			if(!data){
				data = {};
			}
			data.page = opt.page || 0;
			data.size = opt.size || 0;

			_opt.data = data;
			if(!_opt.headers){
				_opt.headers={};
			}
			doAjaxRequest(_opt);
		}
	}

	// 发送ajax 请求
	function doAjaxRequest(opt) {
		// 初始化发送ajax请求的一些必要参数信息
		var async = opt.async !== false, data = opt.data || {}, success = opt.success
				|| doSuccess, failure = opt.failure || doFailure;

		var _url = ROOT + opt.endpoint;

		// 不管了,无论POST GET 都增加时间戳吧
		_url += (_url.indexOf('?') == -1 ? '?' : '&') + "timestamp="
				+ new Date().getTime();
	   
	 //_url = "http://www.baidu.com"+_url;
	 //window.alert(_url);

		// 发送ajax请求
		$.ajax({
			async : async,
			type : opt.method,
			url : _url,
			data : data,
			timeout : 30000,
			dataType : 'json',
			headers:
			$.extend({},opt.headers,{
					_token: httpservice.token.get(),
					_redis_token: httpservice.ssoToken.get()
			}),
			success : function(msg) {
				// 每次请求完成之后,
				if (msg.success) {
					success(msg.data); 
				} else if(msg.code=="0001"){
					window.location.href =msg.url;
				}else{
					var error=msg.errorModel;
					if("BYS"==error.type){ //BYS
						if('E001'==error.code){
							if(!opt.stay){
								httpservice.token.remove();
								window.location.href =VIEW_ROOT+"/pages/login.html";
								alertTool.error("请重新登录.");
							}
						}
						failure(error.code);
					}else if("LYS"==error.type){
						if('DL001'==error.code){
							//重新定向登陆
							window.location.href =error.message;	
						}else if('DL005'==error.code)
						{
							//登出
							localStorage.clear();
							httpservice.cookie.remove(httpservice.cookie.getKey());
							window.location.href =error.message;	
						}
						else{
							window.location.href =VIEW_ROOT+"/pages/noSync.html";
						}
					}else{
                        var codeValue = httpservice.errorCode.getVal(error.code);
						alertTool.error(codeValue);
					}
				}
			},
			error : function(textStatus) {
				if(textStatus.status == 400){
					window.location.href= "login.html";
				}
				failure("请求发送失败!");
			}
		});
	}

	// 默认的成功回调函数
	function doSuccess(data) {
		httpservice.tools.alert("info", "操作成功!");
	}

	// 默认的失败回调函数
	function doFailure(data) {
        var codeValue = httpservice.errorCode.getVal(data);
		httpservice.tools.alert("error", codeValue);
	}
	return internalFunction;
})(window);

//
httpservice.ajaxEnt = new httpservice.ajax({

});

httpservice.htmlEnt = new httpservice.html({
	dom : "rightRefreshDiv"
});

(function($) {
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		var str = this.serialize();
		$(array).each(
				function() {
					if (serializeObj[this.name]) {
						if ($.isArray(serializeObj[this.name])) {
							serializeObj[this.name].push(this.value);
						} else {
							serializeObj[this.name] = [
									serializeObj[this.name], this.value ];
						}
					} else {
						serializeObj[this.name] = this.value;
					}
				});
		return serializeObj;
	};
})(jQuery);

$.fn.extend({
	namespace : function() {
		var api = {
			currentLayout : function() {
				var dom = this.root;
				while (!dom.attr('data-layout-id')) {
					dom = dom.parent();
				}

				return dom;
			},
			getViewParam : function() {
				var dom = this.currentLayout();

				return dom.data('vp');
			},
			skiphtml : function(url) {
				window.httpservice.htmlEnt.skiphtml(url);
			},
			submitForm : function(opt) {
				window.httpservice.ajaxEnt.submitForm(opt);
			},
			getRequest : function(opt) {
				window.httpservice.ajaxEnt.getRequest(opt);
			},
			postRequest : function(opt) {
				window.httpservice.ajaxEnt.doPost($.extend({},opt));
			},
			getParam:function(name){
				//"_param_"
				//$("#" + _internal.dom).data   我们从这个数据缓存上拿到参数
				return $("#" + window.httpservice.htmlEnt._dom()).data("_param_")[name];
			},
			confirm:function(fn,title){
				$.confirm({
    			    title: false,
    			    content: title?title:'确定删除？',
    			    confirm: function(){
    			    	fn();
    			    },
    			    cancel: function(){
    			    },
    			    confirmButton: '确定',
    			    cancelButton: '取消',
    			    onOpen: function(){
    			    }
    			});
			},
			getCodes:function(fn,code,type){
				this.getRequest({
					endpoint : "/dictionary/getDictionaryByCode",
					data : {"code":code},
					success : function(data) {
						 return fn(data);
					},
					failure : function(data) {
						alertTool.error(httpservice.errorCode.getVal(data));
						return fn({});
					}
				});
			},
			modal : function(){
				return {
					parent : {},
					/**
					 * modalOpts : {
					 * id : //modal id,
					 * title : // title,
					 * hidden :  // fn(e(事件对象),array(弹出页面的返回参数))
					 * 监听函数，当弹出页面关闭的时候能够获得通知
					 * opt : {}  //modal options
					 * }
					 */
					open : function(page,modalOpts,url,params,width){
						if(width=='' || width == undefined){
                            width='550px';
						}
						var divId=modalOpts.id;
						var template='<div name="modalSource" id="'+divId +'" class="modal fade " tabindex="-1" role="dialog">'
						+'<div class="modal-dialog" style="width:'+width+'" role="document">'
					    +'<div class="modal-content">'
					    +'<div class="modal-header">'
				        +'<button type="button" class="close" data-dismiss="modal" aria-label="Close">'
				        +'</button>'
				        +'<h4 class="modal-title" >'+modalOpts.title+'</h4>'
				        +'</div>'
						+'<div class="modal-body">'
					    +'</div>'
					    +'</div>'
						+'</div>';
						page.namespace.root.find('#'+divId).remove();
						page.namespace.root.append($(template));
						var $dom=page.namespace.root.find('#'+divId);
						$dom.data('parentPage',page);
						$dom.data('params',params);
						$dom.data('modalOpts',modalOpts);
						$dom.modal({
							show : true,
							keyboard: false,
							backdrop : 'static'
						});
						$dom.find('.modal-content > .modal-body').load(VIEW_ROOT+url);
						$dom.on('hidden.bs.modal',function(e){
							try{
								if($dom.data('modalHiddenFn')){
									$dom.data('modalHiddenFn')(e);
								}
							}catch (e) {
							}
							if($dom.data('modalSkip')){
								return ;
							}
							var modalReturnFn=$dom.data('modalReturnFn');
							var result=[];
							if(modalReturnFn){
								result[0]=modalReturnFn();
							}
							try{
								modalOpts.hidden(e,result);
							}catch (e) {
							}
							$dom.remove();
						});
					},
					/**
					 * modalOpts : {
					 * id : //modal id,   // optional
					 * title : // title,
					 * hidden : // fn 监听函数，当弹出页面关闭的时候能够获得通知
					 * returnFn :  //fn   注册返回参数函数，此参数会作为父页面注册的关闭函数的入参
					 * skip : true/false // skip hide function  父页面的监听函数不会收到通知（true）
					 * opt : {}  //modal options
					 * }
					 */
					close : function(page,modalOpts){

						var defaultOpts={
							hidden : function(e){},
							returnFn : function(){return {}},
							skip : false,
							opt : {}
						};
						var _modalOpts=defaultOpts;
						if(modalOpts){
							_modalOpts=$.extend({},defaultOpts,modalOpts);
						}
						
						var $dom;
						if(_modalOpts.id){
							$dom=this.parent.namespace.root.find('#'+_modalOpts.id);
						}else{
							$dom=this.modalSource(page);
						}
						$dom.data('modalHiddenFn',_modalOpts.hidden);
						this.registerReturn(page,_modalOpts.returnFn);
						$dom.data('modalSkip',_modalOpts.skip);
						/*
						if(modalOpts.skip){
							$dom.off('hidden.bs.modal');
						}*/
						$dom.modal('hide');
					},

					/**
					 * 注册返回参数函数
					 */
					registerReturn : function(page,fn){
						this.modalSource(page).data('modalReturnFn',fn);
					},
					/**
					 * 注册当前弹出页面的父页面
					 */
					registerParent : function(page){
						var $modalSource=this.modalSource(page);
						this.parent=$modalSource.data('parentPage');
					},

					modalSource : function(page){
						return page.namespace.root.parents('div[name="modalSource"]');
					},

					params : function(page){
						var params=this.modalSource(page).data('params');
						if(params){
							return params;
						}else{
							return {};
						}
					},

					param : function(page,key){
						var params=this.params(page);
						if(params){
							return params[key];
						}else{
							return null;
						}
					}
				}
			}(),
			grantFunc : function(){
                var funcs = httpservice.funcPrivilege.get();
                if(funcs.length == 0){
                    httpservice.tools.alert('error', '没有任何权限');
                    api.root.find("[func]").each(function () {
                        $(this).remove();
					});
                }else{
                    api.root.find("[func]").each(function () {
                        var that = $(this);
                        var func = that.attr("func");
                        var exists = false;
                        var i = 0;
                        for(; i < funcs.length;i++){
                            if (func === funcs[i]) {
                                exists = true;
                                break;
                            }
                        }
                        if(!exists){
                            that.remove();
                        }
                    });
				}

			}
		};

		api.root = $(this);

        if(OPEN_GRANT_FUNC){
            api.grantFunc();
        }
		return api;
	}
});
