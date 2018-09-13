if (!window.kjcs) {
	var kjcs = {};

};

var Tool = {
	isArray : function(obj) {
		return this.type(obj) == "array";
	},
	isFun : function(obj) {
		return this.type(obj) == "function";
	},
	isStr : function(obj) {
		return this.type(obj) == "string";
	},
	log : function(msg) {
		this.info(msg);
	},
	info : function(msg) {
		if (window.console && window.console.info) {
			window.console.info(msg);
		}
	},
	error : function(msg) {
		if (window.console && window.console.error) {
			window.console.error(msg);
		}
	},

	setCookie : function(name, value, time) {
		var _exp = new Date();
		time = time || 60000;
		_exp.setTime(_exp.getTime() + time);
		document.cookie = name + "=" + escape(value) + ";expires="
				+ _exp.toGMTString();
	},
	/**
	 * 读cookies
	 */
	getCookie : function(name) {
		var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
		if (arr = document.cookie.match(reg))
			return (arr[2]);
		else
			return null;
	},

	getUrlParam : function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = decodeURI(window.location.search.substr(1)).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	},
	setUrlParam : function(name, value) {
		var href = window.location.href;
		var _newParam = null;// "&"+name+"=" ;
		if (null != Tool.getUrlParam(name)) {
			_newParam = name + "=" + value;
			href = href.replace(name + "=" + Tool.getUrlParam(name), _newParam)
			return href;
		}
		if (window.location.search.substr(1).length == 0) {
			_newParam = "?" + name + "=" + value;
		} else {
			_newParam = "&" + name + "=" + value;
		}
		return href + _newParam;
	},

	/**
	 * 
	 */
	isIE : function() { // ie?
		if (!!window.ActiveXObject || "ActiveXObject" in window)
			return true;
		else
			return false;
	},

	serializeObject : function(obj) {
		var resultObj = new Object();
		$.each(obj.serializeArray(), function(index, param) {
			if (!(param.name in resultObj)) {
				if (param.value != undefined && param.value != '') {
					param.value = $.trim(param.value);
				}
				resultObj[param.name] = param.value;
			}
		});
		return resultObj;
	},

	jsonToParam : function(obj) {
		var param = "";
		for ( var key in obj) {
			// if(Tool.isStr(obj[key])){
			param += "&" + key + "=" + obj[key];
			// }
		}
		if (param.length) {
			param = param.substr(1);
		}
		return param;
	},
	stringify : function(obj) {
		var JSON = window.JSON;
		if (JSON) {
			return JSON.stringify(obj);
		}
		return Tool.jsonToParam(obj);
	},

	parseJSON : function(json) {
		if (typeof json != 'string')
			return json = json.replace(/^\s+|\s+$/g, '');
		var isValid = /^[\],:{}\s]*$/
				.test(json
						.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, "@")
						.replace(
								/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,
								"]").replace(/(?:^|:|,)(?:\s*\[)+/g, ""));
		if (!isValid)
			throw "Invalid JSON";
		var JSON = window.JSON;
		return JSON && JSON.parse ? JSON.parse(json) : (new Function("return "
				+ json))();
	},

	JsonToString : function(String) {
		return JSON.stringify(String);
	}

};

Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

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

// /data/getdata
kjcs.ajax = (function(data) {
	var _this = null;
	var myfunction = function(obj) {
		this.obj = obj;
		this.host = !!obj.host ? obj.host : "";
		this.getDataUrl = this.host + "/data/getdata";
		this.getuploadUrl = this.host + "/data/uploaddata";
		_this = this;
		this.init();
	};
	myfunction.prototype = {
		init : function() {
			Tool.info("hello word!");
		},
		submitForm : function(opt) {
			var _opt = {};
			_opt.success = opt.success || defSuccess,
					_opt.failure = opt.failure || defFailure,
					_opt.formObj = opt.formObj, _opt.page = opt.page || 0,
					_opt.size = opt.size || 0;
			var form = _opt.formObj;
			var data = {};
			if (form.children("[token=token]").length == 1) {
				data.token = form.children("[token=token]").val();
			}
			if (!!!opt.endpoint) {
				alert("endpoint is null");
			}
			data.endpoint = opt.endpoint;
			data.formData = Tool.JsonToString(form.serializeJson());
			data.paginationData = Tool.JsonToString({
				page : _opt.page,
				size : _opt.size
			});
			_opt.data = data;
			submitFile(_opt);

		},
		search : function(opt) {
			alert("功能未完善");
		},
		getRequest : function(opt) {
			var _opt = {};
			_opt.async = opt.async !== false, _opt.timely = true,
					_opt.method = 'GET', _opt.success = opt.success
							|| defSuccess, _opt.failure = opt.failure
							|| defFailure, page = opt.page || 0,
					size = opt.size || 0;
			var mydata = {};
			if (!!!opt.endpoint) {
				alert("endpoint is null");
			}
			mydata.endpoint = opt.endpoint;
			mydata.formData = Tool.JsonToString(opt.data);
			mydata.paginationData = Tool.JsonToString({
				page : page,
				size : size
			});
			_opt.data = mydata;
			request(_opt);

		},
		postRequest : function(opt) {
			alert("功能未完善");
		},
		get : function(opt) {
			alert("功能未完善");
		},
		post : function(opt) {
			alert("功能未完善");
		},
		/**
		 * 
		 */
		uploadImage : function(opt) {

			var success = opt.success || defSuccess, failure = opt.failure
					|| defFailure

			var form = $("#" + opt.formid);
			var ajax_option = {
				type : 'post',
				url : this.getuploadUrl,
				data : {
					'fileType' : 'image'
				},
				dataType : 'json',
				success : function(msg) {
					if (msg.success) {
						success(msg.data);
					} else {
						failure(msg.errorMessage);
					}

				},
				error : function(res) {
					failure(res);
				}
			};

			form.ajaxSubmit(ajax_option);

		}

	};

	function defSuccess() {
	}

	function defFailure(code) {

	}
	function FinalFailure(code) {
		if (!!$("#error-box-warning") && $("#error-box-warning").length > 0) {
			$("#errmessageBody").html(code);

		} else {
			var box = $("<div/>", {
				class : "box box-warning collapsed-box",
				id : "error-box-warning"
			});
			var box_header = $("<div/>", {
				class : "box-header with-border"
			})
					.html(
							'  <h3 class="box-title">E001</h3>'
									+ ' <div class="box-tools pull-right">'
									+ ' <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-plus"></i></button>'
									+ ' <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>'
									+ '  </div>');

			var box_body = $("<div/>", {
				class : 'box-body',
				id : 'errmessageBody'
			}).html(code);
			box.append(box_header);
			box.append(box_body);
			$(".content-wrapper .content-header").after(box);
		}

		// Tool.log("requset failure: status" + code);
	}
	function submitFile(opt) {
		var async = opt.async !== false, method = 'POST', success = opt.success
				|| defSuccess, failure = opt.failure || defFailure, page = opt.page || 0, size = opt.size || 0, data = opt.data;
		var form = opt.formObj;
		var _url = _this.getDataUrl;

		$.ajax({
			type : method,
			url : _url,
			data : data || {},
			async : async,
			dataType : 'json',
			success : function(msg) {
				if (msg.success) {
					success(msg.data);
				} else {
					if (!!msg.token) {
						if (form.children("[token=token]").length == 1) {
							form.children("[token=token]").val(
									Tool.JsonToString(msg.token));
						}
					}
					FinalFailure(msg.errorMessage);
					failure(msg.errorMessage);
				}

			},
			error : function(textStatus) {
				failure(textStatus);
			}
		});
	}

	/**
	 * 默认get，not timely 请求 对 fileRespectSource 进行解析
	 * 
	 * @param url
	 * @param opt
	 */
	function request(opt) {
		var async = opt.async !== false, timely = opt.timely !== false, method = opt.method
				|| 'GET', data = opt.data || null, success = opt.success
				|| defSuccess, failure = opt.failure || defFailure, method = method
				.toUpperCase();
		var _url = _this.getDataUrl;
		if (method == 'GET' && timely) {
			_url += (_url.indexOf('?') == -1 ? '?' : '&') + "timestamp="
					+ new Date().getTime();
		}
		$.ajax({
			type : method,
			url : _url,
			data : data || {},
			async : async,
			dataType : 'json',
			success : function(msg) {
				if (msg.success) {
					success(msg.data)
				} else {
					failure(msg.errorMessage);
					FinalFailure(msg.errorMessage);
				}

			},
			error : function(textStatus) {
				failure(textStatus);
			}
		});
	}
	return myfunction;
})(window);

kjcs.html = (function(data) {
	var donum = 0;
	var _this = null;
	var createHtml = function(obj) {
		this.obj = obj;
		this.url = !!obj.url ? obj.url : "";
		_this = this;
		this.init();
	};
	createHtml.prototype = {

		init : function() {
			// 获取根节点html
			var rootDoms = $("[data-layoutBody]");

			var datas = getRequstParams(rootDoms);
			this.doMethod(datas);

		},
		doview : function(htmlUrl, layoutId) {

			this.doMethod(datas);
		},

		doMethod : function(datas) {
			donum++;
			for ( var i in datas) {
				var data = datas[i];
				doget(data, doGetCallBack, data.layoutId);
			}

		},

		menuHref : function(obj) {
			$(obj).parent().addClass("active")
			$(obj).parent().siblings().removeClass("active");

			var data = getRequstParamsByParam($(obj));
			if (!!!data) {
				return;
				Tool.error("data is null")
			}
			doget(data, hrefhtmlCallBack, data.request.layoutId)
		},

		loading : function() {
			if ($(".content-wrapper").size() != 1) {
				alert(" 页面结构不符合标准  content-wrapper 标签数量错误");
			}
			var content_wrapper_m_h = $(window).outerHeight()
					- $(".main-header").outerHeight()
					- $(".main-footer").outerHeight();
			$(".content-wrapper").css("min-height", content_wrapper_m_h + "px");
		},
		skiphtml : function(url, params, layoutId) {
			var _layoutId = layoutId || "main-bodyindex";
			var data = getRequstParam(_layoutId, url);
			doget(data, doGetCallBack, _layoutId, params);
		},
		getparam : function(id, layoutId) {
			var _layoutId = layoutId || "main-bodyindex";
			return $("#" + _layoutId + "-" + id).val();

		}

	};
	/**
	 * 根据dom元素获取传入参数
	 */
	function getRequstParams(doms) {
		var data = [];
		for ( var i in doms) {
			var dom = doms.eq(i);
			if (!!dom.attr("data-htmlUrl")) {
				var params = {
					url : _this.url
				};
				var request = {
					htmlUrl : dom.attr("data-htmlUrl"),
					layoutId : dom.attr("data-layoutId")
				};
				params.request = request;
				data.push(params);
			}
		}
		return data;
	}
	;
	/**
	 * 根据入参获取传入参数
	 */
	function getRequstParamsByParam(dom) {

		if (!!dom.attr("href-htmlUrl")) {
			var params = {
				url : _this.url
			};
			var request = {
				htmlUrl : dom.attr("href-htmlUrl"),
				layoutId : dom.attr("href-layoutId")
			};
			params.request = request;

		}

		return params;
	}

	function getRequstParam(layoutId, url) {

		var params = {
			url : _this.url
		};
		var request = {
			htmlUrl : url,
			layoutId : layoutId
		};
		params.request = request;

		return params;

	}
	function doget(params, callback, domId, urlparams) {
		var backFunction = callback, _data = params || {}, url = _data.url;
		url += (url.indexOf('?') == -1 ? '?' : '&') + "timestamp="
				+ new Date().getTime();

		var data = {
			"data" : Tool.JsonToString(_data)
		};
		// data.data = _data;
		$.ajax({
			type : "GET",
			url : url,
			data : data || {},
			async : true,
			dataType : 'json',
			success : function(msg) {
				backFunction(msg, domId, urlparams);
			},

			error : function(textStatus) {

				failure(textStatus);
			}
		});

	}

	function hrefhtmlCallBack(data, layoutId) {
		/*
		 * $("[data-layoutId='" + layoutId + "']").eq(0).html(data.html);
		 */
		doGetCallBack(data, layoutId);
	}

	function createUrlparamsHtml(layoutId, urlparams) {
		if (!urlparams) {
			return;
		}

		if ($("[data-layoutId='" + layoutId + "']").length != 1) {
			callErrData("layoutId 不存在", layoutId);

		} else {
			for ( var key in urlparams) {
				var html = "<input type='hidden' id='" + layoutId + "-" + key
						+ "' value='" + urlparams[key] + "'/>";
				$("[data-layoutId='" + layoutId + "']").eq(0).append(html);
			}
		}
	}

	// 获取html 成功后回调
	function doGetCallBack(data, layoutId, urlparams) {

		if ($("[data-layoutId='" + data.htmlDef.layoutId + "']").length != 1) {
			callErrData(data);
			$("[data-layoutId='" + layoutId + "']").eq(0).empty();
			createUrlparamsHtml(layoutId, urlparams);

			$("[data-layoutId='" + layoutId + "']").eq(0).append(data.html);
		} else {

			$("[data-layoutId='" + data.htmlDef.layoutId + "']").eq(0).empty();
			createUrlparamsHtml(data.htmlDef.layoutId, urlparams);
			$("[data-layoutId='" + data.htmlDef.layoutId + "']").append(
					data.html);

			if (data.htmlDef.type == "html") {
				if ($("[data-layoutId='" + data.htmlDef.layoutId
						+ "'] [token='token']").length == 1) {
					$(
							"[data-layoutId='" + data.htmlDef.layoutId
									+ "'] [token='token']").eq(0).val(
							data.token);
				}
			}
			var doms = $("[data-layoutId='" + data.htmlDef.layoutId
					+ "'] [data-layoutId]");
			var params = getRequstParams(doms);
			_this.doMethod(params);

		}

	}

	function failure(data) {
		Tool.info("[doGet error error ]", data);

	}
	function callErrData(data) {
		Tool.info("[doGet  html layoutId:]", Tool.JsonToString(data));

	}

	return createHtml;

})(window);

kjcs.uploadMode = (function(data) {
	var _this;
	var dofunction = function(opt) {
		this.opt = {};
		this.fun = {};
		this.opt.bodyId = opt.bodyId;
		this.opt.fileSelectName = opt.fileSelectName || "选择文件";
		this.opt.fileSelectNameclass = opt.fileSelectNameclass
				|| "btn btn-block btn-warning btn-lg";
		this.opt.beginId = this.opt.bodyId + "-";
		this.allowsubmit = true; // 似的系统只能有一个文件在提交
		this.fun.addFile = opt.addfun;
		this.fun.remFile = opt.remfun;
		_this = this;
		this.init();
	}
	dofunction.prototype = {
		init : function() {
			createModeHtml();
			addEventListener();
		}
	}

	function addEventListener() {

		var fileSelect = document.getElementById(_this.opt.beginId
				+ "fileSelect"), fileElem = document
				.getElementById(_this.opt.beginId + "fileElem"), formsumitElem = document
				.getElementById(_this.opt.beginId + "formsumit");
		// 选择图片事件绑定
		fileSelect.addEventListener("click", function(e) {
			if (fileElem) {
				fileElem.click();
			}
			e.preventDefault(); // prevent navigation to "#"
		}, false);
		// file 框 加change事件
		fileElem.addEventListener("change",
				function(e) {
					var files = document.getElementById(_this.opt.beginId
							+ "fileElem").files;
					changeFile(files);
				}, false);

		// 上传按钮添加点击监听
		formsumitElem.addEventListener("click",
				function(e) {
					var files = document.getElementById(_this.opt.beginId
							+ "fileElem").files;
					if (!_this.allowsubmit) {
						return;
					}
					_this.allowsubmit = false;
					var opt = {
						formid : _this.opt.beginId + "ImageUpload",
						success : function(data) {
							_this.fun.addFile(data);
							_this.allowsubmit = true;
				/*			var file = $(_this.opt.beginId + "fileElem");
							var demo = $("<input/>",{name:'image',class:'form-control',id: _this.opt.beginId+'fileElem',type:'file'});
							file.after(demo);
							file.remove();*/

							createLinkA(data);
						},
						failure : function(data) {
							_this.allowsubmit = true;
							Tool.info(data);
						}
					}
					kjcs.ajaxEnt.uploadImage(opt);

				}, false);
	}

	function createModeHtml() {
		var bodyHtml = $("#" + _this.opt.bodyId);

		var fileSelect = $("<button/>", {
			"html" : _this.opt.fileSelectName,
			"type" : "button",
			"class" : _this.opt.fileSelectNameclass,
			"id" : _this.opt.beginId + "fileSelect"
		});

		bodyHtml.append(fileSelect);

		var links = $("<div id=\"" + _this.opt.beginId
				+ "links\" class=\"links\"></div>");
		var formsubmit = $("<div id ='"
				+ _this.opt.beginId
				+ "formsumit' class='formsumitButton' ><span class='fileSize' id=\""
				+ _this.opt.beginId
				+ "fileSize\"></span><span class='fileName' id=\""
				+ _this.opt.beginId + "fileName\"></span></div>");
		links.append(formsubmit);

		bodyHtml.append(links);

		var htmlform = $("<form />", {
			"id" : _this.opt.beginId + "ImageUpload",
			"method" : "post",
			"enctype" : "multipart/form-data"
		})
				.html(
						"<div class='form-group' style='display: none' >"
								+ "<input type='file' name='image' class='form-control' id='"
								+ _this.opt.beginId + "fileElem'  /></div>");
		bodyHtml.append(htmlform);
		if (!!$("#blueimp-gallery") || $("#blueimp-gallery").length != 1) {
			var blueimp_gallery = $("<div />", {
				"id" : "blueimp-gallery",
				"class" : "blueimp-gallery"
			})
					.html(
							"<div class='slides'></div> <h3 class='title'></h3> <a class='prev'>‹</a> <a class='next'>›</a> <a class='close'>×</a> <a class='play-pause'></a> <ol class='indicator'></ol>");
			bodyHtml.append(blueimp_gallery);
		}
	}

	function createsubmit(name, size, type) {

		$("#" + _this.opt.beginId + "fileSize").html("大小：" + size + "M");
		$("#" + _this.opt.beginId + "fileName").html("文件名：" + name);

		$("#" + _this.opt.beginId + "formsumit").css("display", "block");
	}
	function removesubit() {
		$("#" + _this.opt.beginId + "formsumit").css("display", "none");
	}

	function changeFile(files) {

		if (!!files && !!files[0]) {
			var file = files[0];
			var name = file.name, size = (file.size / 1024000).toFixed(2), type = file.type;
			createsubmit(name, size, type);

		} else {
			removesubit();
		}

	}

	function createLinkA(data) {
		for ( var o in data) {
			var htmsource = data[o];
			var html = '<li><a class="links-a" href="'
					+ htmsource.path
					+ '" title="'
					+ htmsource.name
					+ '" data-gallery=""><img src="'
					+ htmsource.path
					+ '" showGallery=true ></a><div class="tools tools-top" ><i class="icon-remove" fileid="'
					+ htmsource.id + '" id="' + _this.opt.beginId + 'ic-remove'
					+ o + '" ></i></div></li>';
			if ($("#" + _this.opt.beginId + "links").children().size() == 1) {
				$("#" + _this.opt.beginId + "links").append(html);
				removesubit();

				document.getElementById(_this.opt.beginId + 'links').onclick = function(
						event) {
					event = event || window.event;
					if (!$(event.target).attr('showgallery')) {
						return;
					}
					var target = event.target || event.srcElement, link = target.src ? target.parentNode
							: target, options = {
						index : link,
						event : event
					}, links = document.getElementById(
							_this.opt.beginId + 'links').getElementsByTagName(
							'a');
					blueimp.Gallery(links, options);
				};
			} else {

				$("#" + _this.opt.beginId + "links").children().eq(1).before(
						html);
				removesubit();
			}

			var ic_removes = document.getElementById(_this.opt.beginId
					+ 'ic-remove' + o);
			ic_removes.addEventListener("click", function(e) {
				var fileId = $(e.target).attr("fileid");
				$(e.target).parent().parent().remove();
				_this.fun.remFile(fileId);
			}, false);

		}
	}

	return {
		imageUpload : dofunction
	};
})(window);