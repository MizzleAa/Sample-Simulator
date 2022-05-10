function register(){
    const url = "/api/dashboard";

    const title = $("#title").val();
    const content = $("#content").val();
    const path = $("#path").val();
    const raw = $("#raw").val();

    $("#loading").show();

    $.ajax({
        url: url,
        type: "POST",
        contentType: "application/json; charset=UTF-8",
        data:JSON.stringify({
            title:title,
            content:content,
            path:path,
            raw:raw
        }),
        success: function(result){
            // console.log("success=\n",result);
        },
        error: function(error){
            //console.log("error=\n",error);
        }
    })
    .done(function (fragment) {
        //console.log(fragment);
        if(fragment.check){
            window.location.href = "/";
        }
        $("#loading").hide();

    });

}