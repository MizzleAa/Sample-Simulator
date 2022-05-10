$(function () {
    View();
});

function gridView(dashboard){
    $("#createdDate").html(alignCreateDate(dashboard.createdDate));
    $("#title").html(dashboard.title);
    $("#content").html(dashboard.content);
    $("#raw").html(dashboard.raw);
}

function gridFiles(files, url){
    
    const group = $(`<div class="w-full grid grid-cols-1 md:grid-cols-1 gap-1"></div>`);

    files.forEach( (element, key) => {
        const step = $(`<div id=`+element.id+` class="w-full flex justify-between items-center w-full text-center border-b cursor-pointer hover:border hover:bg-gray-100"></div>`);
        const id = $(`<div class="w-28 text-center px-4 py-2 text-xs text-gray-800 truncate">`+key+`</div>`);
        const path = $(`<div class="w-full text-center px-4 text-xs text-gray-800 truncate">`+element.path+`</div>`);
        const name = $(`<div class="w-full text-center px-4 text-xs text-gray-800 truncate">`+element.name+`</div>`);
        
        step.append(id);
        step.append(path);
        step.append(name);
        
        group.append(step);
        step.on("click", function (event) {
            Show(url+"/"+element.name);
        });
    });
    $("#files").html(group);
}

function Show(path){
    const message = $("#modal-message");
    const close = $("#modal-close");
    const active = $("#modal-active");
    
    active.hide();

    //
    message.children().remove();
    //const div = $(`<img class="rounded" src="http://192.168.0.11:8080/aidm/simulator/image/43/2022-05-02_04-53-29_4t2UDsgDGv.JPG" alt="image"/>`);
    const div = $(`<img class="rounded" src="`+path+`" alt="image"/>`);
    
    message.append(div);

    //modal-message
    //
    close.text("취소");
    close.on("click", function (event) {
        $("#modal").hide();
    });
    //
    $("#modal").show();
}

function View() {
    const id = $("#id").val();
    const url = "/api/dashboard/" + Number(id);
    
    $.ajax({
        url: url,
        type: "GET",
        success: function (result) {
            // console.log("success=\n",result);
        },
        error: function (error) {
            //console.log("error=\n",error);
        }
    })
    .done(function (fragment) {
        if(fragment.check){
            //console.info(fragment.information);
            gridView(fragment.information.dashboard);
            gridFiles(fragment.information.files, fragment.information.urlPath)
        }else{

        }
    });
}


function Start() {
    const message = $("#modal-message");
    const close = $("#modal-close");
    const active = $("#modal-active");
    active.show();
    //
    message.children().remove();
    
    const div = $(`<div class="w-full"></div>`);
    const sendInput = $(`<input id="send" type="text" class="p-2 my-2  w-full outline-none border border-gray-200 focus:outline-1 focus:border-gray-400 rounded" placeholder="url을 입력하세요." value="http://192.168.0.17:8000/ai/predict/single/"/>`);
    //const sendInput = $(`<input id="send" type="text" class="p-2 my-2  w-full outline-none border border-gray-200 focus:outline-1 focus:border-gray-400 rounded" placeholder="url을 입력하세요." value="http://192.168.0.11/api/websocket/receive"/>`);

    const delayInput = $(`<input id="delay" type="number" class="p-2 my-2  w-full outline-none border border-gray-200 focus:outline-1 focus:border-gray-400 rounded" placeholder="지연시간(ms)을 입력하세요." value="1000"/>`);

    div.append(sendInput);
    div.append(delayInput);

    message.append(div);
    //modal-message
    //
    close.text("취소");
    close.on("click", function (event) {
        $("#modal").hide();
    });
    //
    active.text("시작");
    active.on("click", function (event) {
        const send = $("#send").val();
        const delay = $("#delay").val();
        
        const id = $("#id").val();
        const url = "/api/dashboard/send/" + Number(id);
        
        $("#modal").hide();
        $("#loading").show();

        $.ajax({
            url: url,
            type: "POST",
            contentType:"application/json; charset=utf-8",
            data:JSON.stringify({
                url:send,
                method:"POST",
                delay:Number(delay)
            }),
            success: function (result) {
                //console.log("success=\n",result);
            },
            error: function (error) {
                //console.log("error=\n",error);
            }
        })
        .done(function (fragment) {
            //console.log("fragment=\n", fragment);
            //window.location.href = "/";
            $("#loading").hide();
            $("#modal").show();
        });
    });
    $("#modal").show();
}

function Delete() {
    const message = $("#modal-message");
    const close = $("#modal-close");
    const active = $("#modal-active");

    active.show();

    //
    message.text("해당 개시글을 삭제하시겠습니까?");
    //
    close.text("취소");
    close.on("click", function (event) {
        $("#modal").hide();
    });
    //
    active.text("삭제");
    active.on("click", function (event) {
        const id = $("#id").val();
    
        const url = "/api/dashboard/" + Number(id);

        $.ajax({
            url: url,
            type: "DELETE",
            success: function (result) {
                // console.log("success=\n",result);
            },
            error: function (error) {
                //console.log("error=\n",error);
            }
        })
        .done(function (fragment) {
            //console.log("fragment=\n", fragment);
            window.location.href = "/";
        });
    });
    $("#modal").show();

}



