const alignCreateDate = (createDate) => {
    if(createDate == undefined){
        return "";
    }else{
        let result = createDate.replace("T", " ");
        return result.split(".")[0];
    }
};