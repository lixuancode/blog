//获取question的id 与comment的内容通过ajax传递到comment层
//提交回复
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
   comment2target(questionId,1,content);
}
function comment2target(targetId,type,content) {
    if (!content) {
        alert("不能回复空内容！");
        return
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var confirm1 = confirm(response.message);
                    if (confirm1) {
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.7b3265842a4b3ab9&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closeBle", true);
                    }
                } else {
                    alert(response.message)
                }
            }
            console.log(response)
        },
        dataType: "json"

    });
}

//二级评论
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content);
}

//展开二级评论
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);
    //获取二级评论的状态
    var attribute = e.getAttribute("data-collapse");
    //当状态为展开就将二级评论关闭
    if (attribute){
        //删除class属性
        comments.removeClass("in");
        //修改展开状态
        e.removeAttribute("data-collapse")
        e.classList.remove("active")
    } else {//状态为关闭就将二级评论展开
        var subCommentContainer =  $("#comment-" + id);
        if (subCommentContainer.children().length != 1){
            //添加class属性
            comments.addClass("in");
            //修改展开状态
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        }else {
            $.getJSON( "/comment/" + id, function( data ) {
                $.each( data.data.reverse(), function( index,comment) {
                    var mediaLeftElement = $("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>/",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>/",{
                        "class":"media-body",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"menu",
                    })).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD')
                    }));

                    var mediaElement = $("<div/>",{
                        "class":"media"
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement);

                    var commentElement = $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
                //添加class属性
                comments.addClass("in");
                //修改展开状态
                e.setAttribute("data-collapse","in");
                e.classList.add("active");
            });
        }
    }
}

//添加标签

function selectTag(e) {
    var values = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(values)==-1){

        if (previous){
            $("#tag").val(previous+","+values);
        }else {
            $("#tag").val(values);
        }
    }
}
function showSelectTag() {
    $("#select-tag").show();
}