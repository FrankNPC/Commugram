var host = "http://localhost:83/";

var client = {
	users: {},
	user: null,
	chats: {},
	messages: {},
	lastrenderid: 0,
	lastpollstartid: 0,
	lastpolltime: 0,
	register: function(){
		$("#label_register").html("register...");
		var register = {loginname: $("#loginname").val(), loginpassword: $("#loginpassword").val()};
		$.ajax({
		    url: host+"/user/register",
		    dataType: "json",
		    async: true,
		    cache: false,
			crossDomain: true,
		    data: register,
		    type: "POST",
		    success: function(responseText) {
		    	if (responseText.type!=USER){ $("#label_register").html("fail register.");return;}
		    	var user = responseText.content;
		    	var appendHtml = "<tr name=\"user\" id=\"userid_"+user.userid+"\"><td>"+user.userid+"</td>";
		    	appendHtml+="<td><input type=\"text\" id=\"loginname_"+user.userid+"\" value=\""+user.loginname+"\" /></td>";
		    	appendHtml+="<td><input type=\"text\" id=\"loginpassword_"+user.userid+"\" value=\""+user.loginpassword+"\" /></td>";
		    	appendHtml+="<td><input type=\"text\" id=\"logintoken_"+user.userid+"\" value=\""+user.logintoken+"\" /></td><td>";
		    	
		    	appendHtml+="<input type=\"button\" value=\"Reset\" onclick=\"client.reset("+user.userid+")\" />";
		    	appendHtml+="<input type=\"button\" value=\"Remove\" onclick=\"client.remove("+user.userid+")\" />";
		    	appendHtml+="<input type=\"button\" value=\"Login\" onclick=\"client.login("+user.userid+")\" />";
		    	appendHtml+="<input type=\"button\" value=\"Chat\" onclick=\"client.open("+user.userid+")\" />";
		    	
		    	appendHtml+="</td></tr>";

		    	$("#users_table").append(appendHtml);
		    	client.users[user.userid] = user;
				$("#label_register").html("done.");
		    },
		    beforeSend: function() {},
		    complete: function() {},
		    error: function() {}
		});
	},
	reset: function(userid){
		for(var uid in client.users){
			if (uid==userid){
				$("#label_register").html("reset...");
				var reset = {logintoken: $("#logintoken_"+uid).val()};
				$.ajax({
				    url: host+"/user/reset",
				    dataType: "json",
				    async: true,
				    cache: false,
					crossDomain: true,
				    data: reset,
				    type: "POST",
				    success: function(responseText) {
				    	if (responseText.type!=USER){ $("#label_register").html("fail reset.");return;}
    			    	client.users[uid] = responseText.content;
				    	$("#logintoken_"+uid).val(client.users[uid].logintoken);
						$("#label_register").html("done.");
				    }
				});
				break;
			}
		}
	},
	remove: function(userid){
		for(var uid in client.users){
			if (uid==userid){
				$("#label_register").html("remove...");
				var remove = {logintoken: $("#logintoken_"+uid).val()};
				$.ajax({
				    url: host+"/user/remove",
				    dataType: "json",
				    async: true,
				    cache: false,
					crossDomain: true,
				    data: remove,
				    type: "POST",
				    success: function(responseText) {
				    	if (responseText.type!=USER){ $("#label_register").html("fail remove.");return;}
    			    	client.users[uid] = responseText.content;
				    	$("#logintoken_"+uid).val(client.users[uid].logintoken);
    					$("#userid_"+uid).remove();
						$("#label_register").html("done.");
				    }
				});
				break;
			}
		}
	},
	login: function(userid){
		if (client.user&&$("#userid_"+client.user.userid)&&client.user.userid==userid){
	    	$(".border-red").each(function(){
    			var clazz = $(this).attr("class");
	    		clazz = clazz?clazz:"";
	    		$(this).attr("class", clazz.replace("border-red", ""));
	    	});
	    	
    		var clazz = $("#userid_"+client.user.userid).attr("class");
    		clazz = clazz?clazz:"";
	    	if (clazz.indexOf("border-red")<0){
    			$("#userid_"+client.user.userid).attr("class", clazz+" border-red");
	    	}
			return;
		}
		
		for(var uid in client.users){
			if (uid==userid){
				$("#label_register").html("login...");
				var login = {loginname: $("#loginname_"+userid).val(), loginpassword: $("#loginpassword_"+userid).val()};
				$.ajax({
				    url: host+"/user/login",
				    dataType: "json",
				    async: true,
				    cache: false,
					crossDomain: true,
				    data: login,
				    type: "POST",
				    success: function(responseText) {
				    	if (responseText.type!=USER){ $("#label_register").html("fail login.");return;}
    			    	var user = client.user = client.users[uid] = responseText.content;
				    	$("#logintoken_"+user.userid).val(user.logintoken);
				    	$("table[name='chat']").remove();
				    	client.chats = {};
				    	
		    			if (!client.messages[user.userid]){client.messages[user.userid]={};}
				    	
				    	$(".border-red").each(function(){
			    			var clazz = $(this).attr("class");
				    		clazz = clazz?clazz:"";
				    		$(this).attr("class", clazz.replace("border-red", ""));
				    	});
				    	
			    		var clazz = $("#userid_"+user.userid).attr("class");
			    		clazz = clazz?clazz:"";
				    	if (clazz.indexOf("border-red")<0){
			    			$("#userid_"+user.userid).attr("class", clazz+" border-red");
				    	}
				    	
						$("#label_register").html("done.");
				    }
				});
				break;
			}
		}
				
	},
	open: function(targetuserid){
		if (client.users[targetuserid]&&$("#chat_"+targetuserid).length==0){
			client.chats[targetuserid]=0;
			var targetuser = client.users[targetuserid];
	    	var appendHtml = "<table name=\"chat\" style=\"width:33%;height:100%;float:left;\" id=\"chat_"+targetuserid+"\">";
			appendHtml+="<tr style=\"height:5%\"><th ondblclick=\"client.close("+targetuser.userid+")\">"+targetuser.userid+"</th>";
			appendHtml+="</tr><tr style=\"height:90%;word-break: break-all;\"><td><dl class=\"messages\" id=\"message_"+targetuserid+"\">";
			
			appendHtml+="</dl></td></tr><tr style=\"height:5%\"><td><table style=\"width:100%\"><tr>";
			appendHtml+="<th style=\"width:85%\"><textarea style=\"width:100%;height:100%\" id=\"typing_"+targetuserid+"\"></textarea></th>";
			appendHtml+="<th style=\"width:15%\"><input type=\"button\" value=\"send\" onclick=\"client.send("+targetuserid+")\" /></th>";
			appendHtml+="</tr></table></td></tr></table>";
			
	    	$("#client").append(appendHtml);
	    	client.poll();
		}
	},
	render: function(){
		var userid=client.user.userid;
		
		for(var uid in client.chats){
			var messages = client.messages[userid][uid];
			for(var mIndex in messages){
				if (messages[mIndex].id>client.chats[uid]){
					client.chats[uid] = messages[mIndex].id;
					if (!$("#chat_"+uid)){
						client.open(uid);
					}
					
					if (messages[mIndex].userid==userid){
						var appendHtml="<dd class=\"right\"><div background=\"img/xx.jpg\"></div><div class=\"text\">"+messages[mIndex].content+"</div></dd>";
						$("#message_"+uid).append(appendHtml);
					}else{
						var appendHtml="<dd class=\" left\"><div background=\"img/xx.jpg\"></div><div class=\"text\">"+messages[mIndex].content+"</div></dd>";
						$("#message_"+uid).append(appendHtml);
					}
				}
			}
		}
	},
	close: function(targetuserid){
		for(var uid in client.chats){
			if (uid==targetuserid){
				delete client.chats[uid];
				$("#chat_"+targetuserid).remove();
				return;
			}
		}
	},
	send: function(targetuserid){
		$("#label_register").html("send...");
		var message = {
			userid: client.user.userid,
			targetid: targetuserid,
			type: MESSAGE_KEY,
			content: $("#typing_"+targetuserid).val()
		};
		var send = {logintoken: client.user.logintoken, messagestrings: JSON.stringify([message])};
		$.ajax({
		    url: host+"/message/send",
		    dataType: "json",
		    async: true,
		    cache: false,
			crossDomain: true,
		    data: send,
		    type: "POST",
		    success: function(responseText) {
		    	client.poll();
		    	if (responseText.type<MESSAGE){ $("#label_register").html("fail send.");return;}
		    	$("#typing_"+targetuserid).val("");
				$("#label_register").html("done.");
		    }
		});
	},
	poll: function(){
		if (!client.user){return;}
		var userid=client.user.userid;
		
		var poll = {logintoken: $("#logintoken_"+client.user.userid).val(),
					startid: client.lastpollstartid,
					//time: lastmessage==null?0:(lastmessage==null?0:lastmessage.createtime),
					count:0};
					
		$.ajax({
		    url: host+"/message/poll",
		    dataType: "json",
		    async: true,
			cache: false,
			crossDomain: true,
		    data: poll,
		    type: "POST",
		    success: function(responseText) {
				client.lastpolltime = Date.now();
		    	if (responseText.type<MESSAGE){ $("#label_register").html("fail poll.");return;}
		    	
		    	for(var index in responseText.content){
	    			if (responseText.content[index].userid==userid){
			    		if (!client.chats[responseText.content[index].userid]){
			    			client.chats[responseText.content[index].userid]=0;
		    			}
		    			if (!client.messages[userid][responseText.content[index].targetid]){
	    					client.messages[userid][responseText.content[index].targetid]=[];
		    			}
		    			var mIndex=0;
		    			for(; mIndex<client.messages[userid][responseText.content[index].targetid].length;mIndex+=1){
		    				if (responseText.content[index].id==client.messages[userid][responseText.content[index].targetid][mIndex].id){mIndex=-1;break;}
		    			}
		    			if (mIndex!=-1){
							client.messages[userid][responseText.content[index].targetid].push(responseText.content[index]);
		    			}
	    			}else{
			    		if (!client.chats[responseText.content[index].targetid]){
			    			client.chats[responseText.content[index].targetid]=0;
		    			}
		    			if (!client.messages[userid][responseText.content[index].userid]){
	    					client.messages[userid][responseText.content[index].userid]=[];
		    			}
		    			var mIndex=0;
		    			for(; mIndex<client.messages[userid][responseText.content[index].userid].length;mIndex+=1){
		    				if (responseText.content[index].id==client.messages[userid][responseText.content[index].userid][mIndex].id){mIndex=-1;break;}
		    			}
		    			if (mIndex!=-1){
							client.messages[userid][responseText.content[index].userid].push(responseText.content[index]);
		    			}
	    			}
		    	}
    			client.render();
		    }
		});
	},
	pollinterval: function(){
		if (Date.now()-client.lastpolltime<3000){return;}
		client.lastpolltime = Date.now();
		client.poll();
	}
};


$(document).ready(function(){
	setInterval(client.pollinterval, 3000);
	
	$("#Register").click(function(){
		client.register();
	});
	$("#Login").click(function(){
		for(var uid in client.users){
			if (client.users[uid].loginname==$("#loginname").val()){
		    	$(".border-red").each(function(){
	    			var clazz = $(this).attr("class");
		    		clazz = clazz?clazz:"";
		    		$(this).attr("class", clazz.replace("border-red", ""));
		    	});
		    	
	    		var clazz = $("#userid_"+uid).attr("class");
	    		clazz = clazz?clazz:"";
		    	if (clazz.indexOf("border-red")<0){
	    			$("#userid_"+uid).attr("class", clazz+" border-red");
		    	}
				return;
			}
		}
	
		$("#label_register").html("login...");
		var login = {loginname: $("#loginname").val(), loginpassword: $("#loginpassword").val()};
		$.ajax({
		    url: host+"/user/login",
		    dataType: "json",
		    async: true,
		    cache: false,
			crossDomain: true,
		    data: login,
		    type: "POST",
		    success: function(responseText) {
		    	if (responseText.type!=USER){ $("#label_register").html("fail login.");return;}
		    	var user = client.user = responseText.content;
		    	
		    	$(".border-red").each(function(){
	    			var clazz = $(this).attr("class");
		    		clazz = clazz?clazz:"";
		    		$(this).attr("class", clazz.replace("border-red", ""));
		    	});
				    	
		    	var appendHtml = "<tr class=\"border-red\" name=\"user\" id=\"userid_"+user.userid+"\"><td>"+user.userid+"</td>";
		    	appendHtml+="<td><input type=\"text\" id=\"loginname_"+user.userid+"\" value=\""+user.loginname+"\" /></td>";
		    	appendHtml+="<td><input type=\"text\" id=\"loginpassword_"+user.userid+"\" value=\""+user.loginpassword+"\" /></td>";
		    	appendHtml+="<td><input type=\"text\" id=\"logintoken_"+user.userid+"\" value=\""+user.logintoken+"\" /></td><td>";
		    	
		    	appendHtml+="<input type=\"button\" value=\"Reset\" onclick=\"client.reset("+user.userid+")\" />";
		    	appendHtml+="<input type=\"button\" value=\"Remove\" onclick=\"client.remove("+user.userid+")\" />";
		    	appendHtml+="<input type=\"button\" value=\"Login\" onclick=\"client.login("+user.userid+")\" />";
		    	appendHtml+="<input type=\"button\" value=\"Chat\" onclick=\"client.open("+user.userid+")\" />";
		    	
		    	appendHtml+="</td></tr>";

		    	$("table[name='chat']").remove();
		    	client.chats = {};
    			if (!client.messages[user.userid]){client.messages[user.userid]={};}
				    	
		    	$("#users_table").append(appendHtml);
		    	client.users[user.userid] = user;
				$("#label_register").html("done.");
		    }
		});
	});
});
