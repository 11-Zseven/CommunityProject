/**
 * 评论问题
 */
function comment(){
    let questionId = $("#questionId").val()
    let content = $("#commentContent").val()
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            parentId: questionId,
            comment: content,
            type:1,//对问题的回复
        }),
        dataType: "json",
        success: function (res){//返回的是一个json对象(后端直接将对象转为json类型返回)
            // console.log(typeof res)//object
            // console.log(JSON.stringify(res));//转为json格式的字符串
            // console.log(typeof  JSON.stringify(res))//string
            if(res.code==200){
                $("#commentPanel").hide();
                location.reload();
            }
            else if(res.code==1001){//表示未登录不允许评论
                //如果一个页面正准备回复，一个页面已经退出登录，就导致无法回复-->跳转到登录页面登录。
                //需要前端跳转页面的原因：ajax请求是异步刷新，后端无法重定向/转发，因此需要前端手动修改浏览器地址 (也可以从后端获取到要跳转的地址，前端进行跳转。由于本项目是单点登录，需要先和github建立链接，所以直接写死)
                //【+1】https://blog.csdn.net/Nidson_IT/article/details/79338663
                //【ajax->重定向无效】https://www.cnblogs.com/lgjlife/p/10445483.html
                let conf=confirm(res.message)
                if(conf){
                    window.open("https://github.com/login/oauth/authorize?client_id=Iv1.1417ff976c0c76e4&redirect_uri=http://localhost:8080/loginForGit&state=1")
                    //因为windwo.open()会再打开一个页面，而不会回到异步刷新的页面(也就是回复的页面（要实现的就是：点击回复，如果未登录提示登录，登陆后回到该回复页面并且回显已经写好的内容）)
                    //因此，给index页面加上一个判断：在加载完页后判断一下，如果某个值存在或者怎样的话，就关闭新的页面  =>(从而间接实现了，没登录->登录->但是还在原页面)
                    window.localStorage.setItem("isAjax",true);
                }
            }
            else if(res.code==1002){
                alert("评论内容不能为空哦~")
            }
        },
    })
}

/**
 * 二级评论面板点击显示与隐藏
 *
 * onclick="commentCollapse(this)": 传入元素本身
 */
function commentCollapse(e){
    // debugger
    // console.log(e)
    let id = e.getAttribute("data-id");//获取到这个元素的data-id属性值
    // console.log(id)
    // let attr = $("#comment-"+id).attr("class");//获取到class属性值
    // console.log(attr)
    //toggleClass:https://blog.csdn.net/weixin_42557996/article/details/84333481
    //如果类 in 存在就删除，不存在就添加。
    //可以传入两个值，("a b")  如果a存在就切换成b..（未验证）
    $("#comment-"+id).toggleClass("in")
    // e.classList.toggle("active")
    if($("#comment-"+id).hasClass("in")){
        e.classList.add("activeC")
    }else {
        e.classList.remove("activeC")
    }
}

/**
 * 评论 评论
 */
function subComment(e){
    let cId = e.getAttribute("data-cId");
    let qID = e.getAttribute("data-qId");
    let subComm = $("#comm2-"+cId).val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            parentId: cId,
            questionId: qID,
            comment: subComm,
            type:2,//对评论的回复
        }),
        dataType: "json",
        success: function (res){//返回的是一个json对象(后端直接将对象转为json类型返回)
            // console.log(typeof res)//object
            // console.log(JSON.stringify(res));//转为json格式的字符串
            // console.log(typeof  JSON.stringify(res))//string
            if(res.code==200){
                // $("#commentPanel").hide();
                location.reload();
            }
            else if(res.code==1001){//表示未登录不允许评论
                //如果一个页面正准备回复，一个页面已经退出登录，就导致无法回复-->跳转到登录页面登录。
                //需要前端跳转页面的原因：ajax请求是异步刷新，后端无法重定向/转发，因此需要前端手动修改浏览器地址 (也可以从后端获取到要跳转的地址，前端进行跳转。由于本项目是单点登录，需要先和github建立链接，所以直接写死)
                //【+1】https://blog.csdn.net/Nidson_IT/article/details/79338663
                //【ajax->重定向无效】https://www.cnblogs.com/lgjlife/p/10445483.html
                let conf=confirm(res.message)
                if(conf){
                    window.open("https://github.com/login/oauth/authorize?client_id=Iv1.1417ff976c0c76e4&redirect_uri=http://localhost:8080/loginForGit&state=1")
                    //因为windwo.open()会再打开一个页面，而不会回到异步刷新的页面(也就是回复的页面（要实现的就是：点击回复，如果未登录提示登录，登陆后回到该回复页面并且回显已经写好的内容）)
                    //因此，给index页面加上一个判断：在加载完页后判断一下，如果某个值存在或者怎样的话，就关闭新的页面  =>(从而间接实现了，没登录->登录->但是还在原页面)
                    window.localStorage.setItem("isAjax",true);
                }
            }
            else if(res.code==1002){
                alert("评论内容不能为空哦~")
            }
        },
    })
}