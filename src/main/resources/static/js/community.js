//获取question的id 与comment的内容通过ajax传递到comment层
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    if (!content) {
        alert("不能回复空内容！");
        return
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success: function (response) {
            if (response.code==200){
               window.location.reload();
            } else {
                if (response.code==2003){
                    var confirm1 = confirm(response.message);
                    if (confirm1){
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.7b3265842a4b3ab9&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closeBle",true);
                    }
                }else {
                    alert(response.message)
                }
            }
            console.log(response)
        },
        dataType: "json"
    });
}