/**
 * Created by chenliu on 2016/6/22.
 */
var mask = function(){
    var show =  function(){
        var thisPageContext = ""

        var back = $("<div class='backdrop loading_backDrop'></div>");
        $("body").append('<div class="backdrop_loading imgContainer">' +
                            '<div class="imgContainerBody overflow-hidden">' +
                                '<img src="../ui/dist/self/img/Loadingbackdrop.gif" />'+
                                '<div class="loading-font">Loading...</div>'+
                        '</div></div>')
                    .append(back)
    };

    var remove =  function(){
        $("body").children(".backdrop").remove();
        $("body").children(".backdrop_loading").remove();
    };

    return {
        show:show,
        remove:remove
    }
}()
