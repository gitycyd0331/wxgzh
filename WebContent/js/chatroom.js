$(document).ready(function(e) {
	$("#message_box").append(   '<div class="msg_item fn-clear">'
               + '   <div class="uface"><img src="../images/53f44283a4347.jpg" width="40" height="40"  alt=""/></div>'
		       + '   <div class="item_left" >'
		       + '     <div class="msg">' + "我什么都知道" + '</div>'
		       + '     <div class="name_time">' + "<span style='color:#57B869'>灵图</span>" + ' ' +getNowFormatDate()+'</div>'
		       + '   </div>'
		       + '</div>')
	$('#message_box').scrollTop($("#message_box")[0].scrollHeight + 20);
	$('.uname').hover(
	    function(){
		    $('.managerbox').stop(true, true).slideDown(100);
	    },
		function(){
		    $('.managerbox').stop(true, true).slideUp(100);
		}
	);
	
	var fromname = $('#fromname').val();
	var to_uid   = 0; // 默认为0,表示发送给所有用户
	var to_uname = '';
	$('.user_list > li').dblclick(function(){
		to_uname = $(this).find('em').text();
		to_uid   = $(this).attr('data-id');
		if(to_uname == fromname){
		    alert('您不能和自己聊天!');
			return false;
		}
		if(to_uname == '所有用户'){
		    $("#toname").val('');
			$('#chat_type').text('群聊');
		}else{
		    $("#toname").val(to_uid);
			$('#chat_type').text('您正和 ' + to_uname + ' 聊天');
		}
		$(this).addClass('selected').siblings().removeClass('selected');
	    $('#message').focus().attr("placeholder", "您对"+to_uname+"说：");
	});
	
	$('.sub_but').click(function(event){
	    sendMessage(event, fromname, to_uid, to_uname);
	});
	
	/*按下按钮或键盘按键*/
	$("#message").keydown(function(event){
		var e = window.event || event;
        var k = e.keyCode || e.which || e.charCode;
		//按下ctrl+enter发送消息
		if((event.ctrlKey && (k == 13 || k == 10) )){
			sendMessage(event, fromname, to_uid, to_uname);
		}
	});
});
//得到当前时间
function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var seperator2 = ":";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	var hours = date.getHours()
	var seconds = date.getSeconds();
	var minutes = date.getMinutes();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	if(hours >= 0 && hours <= 9){
		hours = "0" + hours;
	}
	if(minutes >= 0 && minutes <= 9){84
		minutes = "0" + minutes;
	}
	if(seconds >= 0 && seconds <= 9){
		seconds = "0" + seconds;
	}
	var currentdate = date.getFullYear()  +seperator1+ month  +seperator1+ strDate+"  "+ hours + ":"
     + minutes + ":" + seconds;
			
	return currentdate;
}
function sendMessage(event, from_name, to_uid, to_uname){
    var msg = $("#message").val();
	if(to_uname != ''){
	    msg = '您对 ' + to_uname + ' 说： ' + msg;
	}
	var time = getNowFormatDate(); 
	var htmlData =   '<div class="msg_item fn-clear">'
                   + '   <div class="uface right_10"><img src="../images/hetu.jpg" width="40" height="40"  alt=""/></div>'
			       + '   <div class="margin_right55_float_right" >'
			       + '     <div class="mymsg own">' + msg + '</div>'
			       + '     <div class="name_time text_align_right padding_right_10">' + "<span style='color:#57B869'>"+from_name+"</span>" + ' ' +getNowFormatDate()+'</div>'
			       + '   </div>'
			       + '</div>';
	$("#message_box").append(htmlData);
	$('#message_box').scrollTop($("#message_box")[0].scrollHeight + 20);
	$("#message").val('');
	$.ajax({
		type:"post",
		url:"/wxgzh/"+msg+"/robot.wx",
		dataType: "json",
		success:function(data){
			$("#message_box").append(   '<div class="msg_item fn-clear">'
				   + '<div class="uface"><img src="../images/53f44283a4347.jpg" width="40" height="40"  alt=""/></div>'
			       + '   <div class="item_left" >'
			       + '     <div class="msg">' + data.text + '</div>'
			       + '     <div class="name_time">' + "<span style='color:#57B869'>灵图</span>" + ' ' +getNowFormatDate()+'</div>'
			       + '   </div>'
			       + '</div>')
			$('#message_box').scrollTop($("#message_box")[0].scrollHeight + 20);//自动滚动
			if(data.url != null){
				window.open(data.url);
			}
		},
		error:function(er){
			alert("网络连接异常");
		}
	});
}