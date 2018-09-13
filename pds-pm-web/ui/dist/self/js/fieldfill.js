function AutoFill(){
  /**
  opts is  _Opts -function
  **/

  function Col(){
    var property , name ,
    /*date,number,int,longtext,shorttext*/
    type , required=false ,val='',length=0;

    this.isNumber=function(){
      return this.type=='number' || this.type=='int';
    },
    this.isInt=function(){
      return this.type=='int';
    },

    this.isTxt=function(){
      return this.type=='longtext'||this.type=='shorttext';
    },

    this.isDate=function(){
      return this.type=='date';
    },

    this.isInput = function(){
      return this.isNumber()||this.isDate()||this.type=='shorttext';
    },

    this.isTextarea = function(){
      return this.type=='longtext';
    },

    this.element=function(){
      var txt='';
      if(this.isInput()){
        txt='<input name="'+this.property+'" type="text" class="form-control input-sm" placeholder="">';
      }else if(this.isTextarea()){
        txt='<textarea name="'+this.property+'" class="form-control input-sm" rows="3" placeholder=""></textarea>';
      }
      return txt;
    };




  };

  function Html(){

    this.template = function(col,opts){
      var txt='<div class="form-group">'
        +'<label for="'+col.property+'" class="col-sm-1 control-label '+(col.required?'requiredMask':'')+' input-sm">'+col.name+'</label>'
        +'<div class="col-sm-5">'
        +col.element()
        +'</div>'
      +'</div>';
      return txt;
    }

  };

  function Operations(){

    function _Opts(opts){
      var _opts={
        cols :[],
        opts :opts,
        $root : opts.$root
      };
      for(var i=0;i<opts.data.length;i++){
        var item=opts.data[i];
        var col=new Col();
        col.property=item.property;
        col.name=item.name;
        col.type=item.type;
        col.required=item.required;
        col.val=item.val;
        col.length=item.length?item.length:99999;
        _opts.cols.push(col);
      }
      return _opts;
    }

    this.view = function(opts){
      var _opts=new _Opts(opts);
      for(var i=0;i<_opts.cols.length;i++){
        var col=_opts.cols[i];
        var html=new Html();
        var template=html.template(col,_opts);
        console.log(template);
        var $root=_opts.$root;
        $root.append(template);
        if(col.isDate()){
          $root.find('[name="'+col.property+'"]').datetimepicker(
            {
              minView: "month",//设置只显示到月份
              format : "yyyy-mm-dd",//日期格式
              autoclose:true,//选中关闭
              todayBtn: true,//今日按钮
              forceParse : false
            }
          );
        }

      }
    },

    this.data=function(opts){
      var _opts=new _Opts(opts);
      for(var i=0;i<_opts.cols.length;i++){
        var col=_opts.cols[i];
        var $root=_opts.$root;
        $root.find('[name="'+col.property+'"]').val(col.val);
      }
    },
    this.rule=function(opts){

      var jqueyValidator={
        rules : {

        },
        submitHandler : function(form,event) {
          opts.submit(this.submitButton,form,event);
        }
      }
      var _opts=new _Opts(opts);
      for(var i=0;i<_opts.cols.length;i++){
        var col=_opts.cols[i];
        var _t={required : col.required};
        if(col.isTxt()){
          _t['maxlength']=col.length;
        }
        if(col.isInt()){
          _t['digits']=col.isInt();
        }else if(col.isNumber()){
          _t['number']=col.isNumber();
        }else if(col.isDate()){
          _t['cdate']=true;
        }
        jqueyValidator.rules[''+col.property+'']=_t;
      }
      console.log(jqueyValidator);
      opts.$form.validate(jqueyValidator);


    }





  };
  return {
    fill : function(opts){
      var operations=new Operations();
      operations.view(opts);
      operations.data(opts);
      operations.rule(opts);
    }
  };

}


/*
    var autoFill=new AutoFill();
    autoFill.fill({
      data : [
        {
          property : 'name',
          name : '名称',
          type : 'shorttext',
          required : true,
          val : 'LUS',
          length : 10
        },
        {
          property : 'age',
          name : '年龄',
          type : 'int',
          required : true,
          val : '2'
        },
        {
          property : 'height',
          name : '身高',
          type : 'number',
          required : true,
          val : '1.89'
        },
        {
          property : 'birthday',
          name : '出生时间',
          type : 'date',
          required : true,
          val : '1999-11-11'
        },
        {
          property : 'desc',
          name : '备注',
          type : 'longtext',
          required : true,
          val : '来自SHANGHAI',
          length : 30
        }
      ],
      $form : page.namespace.root.find('form'),
      $root : page.namespace.root.find('.box-body'),
      submit : function(){console.log('submit------>');}

    });
*/
