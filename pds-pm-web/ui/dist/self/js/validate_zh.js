$.extend($.validator.messages, {
    required: "必选字段",
    remote: "请修正该字段",
    email: "请输入正确格式的电子邮件",
    url: "请输入合法的网址",
    date: "请输入合法的日期",
    dateISO: "请输入合法的日期 (ISO)",
    number: "请输入合法的数字",
    digits: "只能输入整数",
    creditcard: "请输入合法的信用卡号",
    equalTo: "请再次输入相同的值",
    accept: "请输入拥有合法后缀名的字",
    maxlength: $.validator.format("请输入一个长度最多是 {0} 的字"),
    minlength: $.validator.format("请输入一个长度最少是 {0} 的字"),
    rangelength: $.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字"),
    range: $.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
    max: $.validator.format("请输入一个最大为 {0} 的值"),
    min: $.validator.format("请输入一个最小为 {0} 的值"),
    isPhone:$.validator.format("请正确填写您的联系电话"),
    decimal:$.validator.format("请输入整数或者小数（小数点后不能超过两位）"),
    alnum :$.validator.format("请输入英文字母和数字")
});

$(function(){

	// 只能输入大写英文
	jQuery.validator.addMethod("string", function(value, element) {
	    var chrnum = /^([a-zA-Z]+)$/;
	    return this.optional(element) || (chrnum.test(value));
	}, "请输入英文字母");

	// 只能汉字
	jQuery.validator.addMethod("chinese", function(value, element) {
		var chrnum = /^([\u4E00-\u9FA5]+)$/;
		return this.optional(element) || (chrnum.test(value));
	}, "请输入中文");

	// 只能输入大小写英文和数字
	jQuery.validator.addMethod("stringNum", function(value, element) {
	    var chrnum = /^([a-zA-Z0-9]+)$/;
	    return this.optional(element) || (chrnum.test(value));
	}, "请输入大小写英文和数字");

	// 正数验证
	jQuery.validator.addMethod("positiveNum", function (value, element){
		var posNum = /^(?:\d+|-?\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/;
		return this.optional(element) || (posNum.test(value));
	}, "请输入正数");

  // 日期验证
	jQuery.validator.addMethod("cdate", function (value, element){
		var posNum = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\s+([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$/;
    var ps=this.optional(element) || (posNum.test(value));
    if(!ps){
      posNum = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\s+([01][0-9]|2[0-3]):[0-5][0-9]$/;
      ps=this.optional(element) || (posNum.test(value));
    }
    if(!ps){
      posNum = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\s+([01][0-9]|2[0-3])$/;
      ps=this.optional(element) || (posNum.test(value));
    }
    if(!ps){
      posNum = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)\s*$/;
      ps=this.optional(element) || (posNum.test(value));
    }

    return ps;
	}, "请输入正确日期（例如：2008-08-08 12：30：30  | 2008-08-08 | 2008-08-08 12 | 2008-08-08 12：30）");

})
