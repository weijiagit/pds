/**
 * Created by chenliu on 2016/7/15.
 */
var alertTool = function() {
    var error = function(head,text){
        $.toast({
            heading: head,
            text:text,
            icon:'error',
            position: 'top-right'
        })
    };

    var info = function(head,text){
        $.toast({
            heading: head,
            text:text,
            icon:'info',
            position: 'top-right'
        })
    };

    var success = function(head,text){
        $.toast({
            heading: head,
            text:text,
            icon:'success',
            position: 'top-right'
        })
    };

    var warning = function(head,text){
        $.toast({
            heading: head,
            text:text,
            icon:'warning',
            position: 'top-right'
        })
    };

    return{
        error:error,
        success:success,
        info:info,
        warning:warning
    }
}()