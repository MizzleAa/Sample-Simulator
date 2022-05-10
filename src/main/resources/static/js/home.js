let lastScrollTopList = [0];

let page = 0;
let size = 20;

$(function () {
    load();
});

$(document).on("mousewheel", function (event, delta) {

    let scrollTop = $(this).scrollTop();

    lastScrollTopList.push(scrollTop);
    if(lastScrollTopList.length > 3){
        lastScrollTopList.shift();
        if(lastScrollTopList[0] === lastScrollTopList[2] && lastScrollTopList[0] > 0){
            getBoardList();
        }
    }
});

function gridBoardList(dashboards){
    dashboards.forEach( (value, key) => {
        const div = dashBoardCard(key, value);
        $("#dashboards").append(div);
    });
}

function dashBoardCard(key, value){

    const div = $(`<div id=`+value.id+` class="cursor-pointer w-full h-68 p-2 bg-white rounded-md shadow hover:ring hover:ring-gray-300"></div>`);

    const title = $(`<div class="px-2 pt-2 font-semibold text-xl text-center rounded-md truncate">`+value.title+`</div>`)

    const content = $(`<div class="py-1 text-center text-md text-gray-400 truncate border-b">`+value.content+`</div>`);

    const raw = $(`<div class="py-1 text-center text-md text-gray-400 truncate">`+value.raw+`</div>`);

    const createdDate = $(`<div class="text-right py-2 text-xs text-gray-400">`+alignCreateDate(value.createdDate)+`</div>`);
    
    div.on("click", function() {
        //console.log($(this).attr("id") );
        viewer($(this).attr("id"));
    });

    div.append(createdDate);
    div.append(title);
    div.append(content);
    div.append(raw);
        
    return div;
}


function load(){
    const url = "/api/dashboard";

    $.ajax({
        url: url,
        type: "GET",
        contentType: "application/json; charset=UTF-8",
        data:{
            page:page,
            size:size
        },
        success: function(result){
            // console.log("success=\n",result);
        },
        error: function(error){
            //console.log("error=\n",error);
        }
    })
    .done(function (fragment) {
        if(fragment.check){
            //console.log(fragment.information);
            gridBoardList(fragment.information);
        }
    });
}

function viewer(id){
    window.location.href = "/dashboard/view/"+id;
}

