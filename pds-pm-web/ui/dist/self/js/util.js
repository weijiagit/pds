function List() {
	this.arrys = [];
	this.position = -1;

	function equals($_this, $_another) {
		if ($_this.equals && $.isFunction($_this.equals)) {
			return $_this.equals.apply($_this, [ $_another ]);
		} else {
			if ($.type($_this) === 'string' || $.type($_this) === 'boolean'
					|| $.type($_this) === 'number' || $.type($_this) === 'date') {
				return $_this == $_another;
			} else {
				for ( var i in $_this) {
					equals($_this[i], [ $_another[i] ]);
				}
			}
		}
	}

	this.size = function() {
		return this.position + 1;
	};

	this.length = function() {
		return this.arrys.length;
	};

	this.resize = function() {
		tempArr = [ this.size() * 1.25 + 5 ];
		for (var i = 0; i < this.size(); i++) {
			tempArr[i] = this.arrys[i];
		}
		this.arrys = tempArr;
	};

	this.exists = function(id) {
		exists = false;
		var i = 0;
		for (; i < this.size(); i++) {
			if (equals(this.arrys[i], id)) {
				exists = true;
				break;
			}
		}
		return exists;
	};

	this.indexOf = function(id) {
		exists = false;
		var i = 0;
		for (; i < this.size(); i++) {
			if (equals(this.arrys[i], id)) {
				exists = true;
				break;
			}
		}
		return exists ? i : -1;
	};

	this.add = function(id) {
		if (this.size() == this.length()) {
			this.resize();
		}
		this.position++;
		this.arrys[this.position] = id;
	};

	this.compress = function() {
		var tempPosition = -1;
		for (var i = 0; i <= this.position; i++) {
			if (this.get(i) == null) {
				var j = i + 1;
				while (j <= this.position) {
					if (this.get(j) != null) {
						this.arrys[i] = this.get(j);
						this.arrys[j] = null;
						tempPosition = i;
						break;
					} else {
						j++;
					}
				}
			} else {
				tempPosition = i;
			}
		}
		this.position = tempPosition;
	};

	this.get = function(index) {
		return this.arrys[index];
	}

	this.remove = function(id) {
		var index = this.indexOf(id);
		if (index != -1) {
			this.arrys[index] = null;
		}
		this.compress();
	};

	this.val = function() {
		var tempArr = [];
		for (var i = 0; i < this.size(); i++) {
			if (this.get(i) != null) {
				tempArr[i] = this.get(i);
			}
		}
		return tempArr;
	}

}
function ListMap(){

	this.entries = new List();

	function genEntry(key, val) {
		return {
			key : key,
			value : val,
			equals : function($obj) {
				return this.key == $obj.key;
			}
		}
	}

	this.put = function(key, val) {
		var entry = genEntry(key, val);
		if (this.entries.exists(entry)) {
			this.entries.remove(entry);
		}
		this.entries.add(entry);
	}

	this.get = function(key) {
		var entry = genEntry(key, null);
		var index = this.entries.indexOf(entry);
		if (index != -1) {
			return this.entries.get(index).value;
		}
	}

	this.exists = function(key) {
		var entry = genEntry(key, null);
		return this.entries.exists(entry);
	}

	this.remove = function(key) {
		var entry = genEntry(key, null);
		return this.entries.remove(entry);
	}

	this.val = function() {
		return this.entries.val();
	}

	this.size = function() {
		return this.entries.size();
	}

	this.keys = function() {
		var keys = [];
		var keyVals = this.val();
		for (var i = 0; i < keyVals.length; i++) {
			keys[i] = keyVals[i].key;
		}
		return keys;
	}

	this.values = function() {
		var values = [];
		var keyVals = this.val();
		for (var i = 0; i < keyVals.length; i++) {
			values[i] = keyVals[i].value;
		}
		return values;
	}

}
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
window.$_ready=function(fn){
	$(function(){
		try{
			fn();
		}catch (e) {
			// TODO: handle exception
			console.error(e);
		}
	})
}