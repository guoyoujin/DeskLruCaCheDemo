wxToken = 'guoyoujin123456';
wxAccessToken = '';
wxAppID = 'wxe7f45fbe1dbb47b5';
wxSecret = '4aec7a1a8f0c2c4ed69e2b5d58f2ff59';
//服务器配置 安全模式 服务器验证
// HTTP.methods({
//     '/wx': {
//       get: function(data) {
//       	console.log('wx get data=====');
//       	console.log(data);
//       	console.log('this wx get=====');
//       	console.log(data);
//       },
//       post:function(data){
//       	console.log('wx post data=====');
//       	console.log(data);
//       	console.log('this wx post=====');
//       	console.log(this);
//       }

//     }
//   });

var load = function (stream, callback) {
    var buffers = [];
    stream.on('data', function (trunk) {
        buffers.push(trunk);
    });
    stream.on('end', function () {
        callback(null, Buffer.concat(buffers));
    });
    stream.once('error', callback);
};

Router.route('/wx', {where: 'server', layoutTemplate: ''})
  .get(function () {
	    this.response.statusCode = 200;
		this.response.setHeader("Content-Type", "application/json");

		var timestamp = this.params.query.timestamp;
		// console.log('timestamp' + timestamp);
		var nonce = this.params.query.nonce;
		// console.log('nonce' + nonce);
		var joinStr = [wxToken,timestamp, nonce].sort().join('');
		// console.log('joinStr' + joinStr);
		var verifyStr = CryptoJS.SHA1(joinStr).toString();
		// console.log('verifyStr' + verifyStr);
		// console.log('signature' + this.params.query.signature);

		if (this.params.query.signature === verifyStr) {

			console.log('微信get数据================== ');
			console.log(this.params.query);

			this.response.end(this.params.query.echostr);
		}else{
			this.response.end('false');
		}
  })
  .post(function (req, res, next) {
  		this.response.statusCode = 200;
		this.response.setHeader("Content-Type", "application/xml");

		var timestamp = req.query.timestamp;
		// console.log('timestamp' + timestamp);
		var nonce = req.query.nonce;
		// console.log('nonce' + nonce);
		var joinStr = [wxToken,timestamp, nonce].sort().join('');
		// console.log('joinStr' + joinStr);
		var verifyStr = CryptoJS.SHA1(joinStr).toString();
		// console.log('verifyStr' + verifyStr);
		// console.log('signature' + this.params.query.signature);
        console.log('post数据来了=======');
        console.log('req.query:' + req.query);
		if (req.query.signature === verifyStr) {

            console.log('验证通过=======');

            load(req, function (err, buf) {
                if (err) {
                    console.log('error');
                    return next(err);
                }
                var xml = buf.toString('utf-8');
                console.log('xml laile ==========');
                console.log(xml);
                if (!xml) {
                    var emptyErr = new Error('body is empty');
                    emptyErr.name = 'Wechat';
                    return next(emptyErr);
                }
                var json = xml2js.parseStringSync(xml);
                console.log('json=====');
                console.log(json);

            });
	  		this.response.end('');


		}else{
			this.response.end('false');
		}


  });


//获取微信access_token  需要修改appid和secret的参数
Meteor.startup(function () {
    // code to run on server at startup
    var http = 'https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=' + wxAppID + '&secret=' + wxSecret;
    Meteor.http.get(http, function (error, result) {
		if(error) {
		    console.log('获取微信access_token FAILED!');
		} else {
		    console.log('获取微信access_token SUCCES');
		    if (result.statusCode === 200) {
		        console.log('Status code = 200!');
		        // console.log(result.content);
		        wxAccessToken = EJSON.parse(result.content).access_token;
		        //console.log(wxAccessToken);
		        //获取微信ip_list
		        get_wx_ip_list();
                
		        create_group('sensensen');
                
		        get_wx_group_list();
                
		        get_wx_group_with_openID('op82Ot-3nzBI2u1jxwNWJ_ohSr0g');
                
		        modify_wx_group_name('118','未分组modify');
                
		        remove_openID_to_group('op82Ot-3nzBI2u1jxwNWJ_ohSr0g','118');
                
		        remove_openIDList_to_group(['op82Ot-3nzBI2u1jxwNWJ_ohSr0g'],'2');
                
		        create_menu();
                
		        get_menu();

		    }
		}

	});
 });
//获取微信服务器ip列表
function get_wx_ip_list(){
	var http = 'https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=' + wxAccessToken;
	console.log(http);
	Meteor.http.get(http, function (error, result) {
		if(error) {
		    console.log('获取微信服务器ip列表 FAILED!');
		} else {
		    console.log('获取微信服务器ip列表 SUCCES');
		    if (result.statusCode === 200) {
		        console.log('Status code = 200!');
		        console.log(result.content);
		     }
		}
	});
}
//创建微信分组
function create_group(name){
	var http = 'https://api.weixin.qq.com/cgi-bin/groups/create?access_token=' + wxAccessToken;
	HTTP.call("POST", http,
          {
          	data: {
          		"group":{"name": name}
          	}
      	  },
          function (error, result) {
            if (error) {
              console.log('create group FAILED!');
              console.log(error);
            }
            else{
            	if (result.statusCode === 200) {
		        	console.log('create group SUCCES!');
            		console.log(result.content);
		     	}
            }
          });

}
//查询微信所有分组
function get_wx_group_list(){
	var http = 'https://api.weixin.qq.com/cgi-bin/groups/get?access_token=' + wxAccessToken;
	console.log(http);
	Meteor.http.get(http, function (error, result) {
		if(error) {
		    console.log('查询微信所有分组 FAILED!');
		} else {
		    console.log('查询微信所有分组 SUCCES');
		    if (result.statusCode === 200) {
		        console.log('Status code = 200!');
		        console.log(result.content);
		     }
		}
	});
}

//通过用户的OpenID查询其所在的GroupID
function get_wx_group_with_openID(openID){
	var http = 'https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=' + wxAccessToken;
	HTTP.call("POST", http,
          {
          	data: {
          		"openid": openID
          	}
      	  },
          function (error, result) {
            if (error) {
              console.log('通过用户的OpenID查询其所在的GroupID FAILED!');
              console.log(error);
            }
            else{
            	if (result.statusCode === 200) {
		        	console.log('通过用户的OpenID查询其所在的GroupID SUCCES!');
            		console.log(result.content);
		     	}
            }
          });
}

//微信修改分组名称
function modify_wx_group_name(group_id, modify_name){
	var http = 'https://api.weixin.qq.com/cgi-bin/groups/update?access_token=' + wxAccessToken;
	HTTP.call("POST", http,
          {
          	data: {
          		"group":{"id":group_id,"name":modify_name}
          	}
      	  },
          function (error, result) {
            if (error) {
              console.log('微信修改分组名称 FAILED!');
              console.log(error);
            }
            else{
            	if (result.statusCode === 200) {
		        	console.log('微信修改分组名称 SUCCES!');
            		console.log(result.content);
		     	}
            }
          });
}

//移动用户分组
function remove_openID_to_group(openID, to_groupid){
	var http = 'https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=' + wxAccessToken;
	HTTP.call("POST", http,
          {
          	data: {
				"openid": openID,"to_groupid": to_groupid
          	}
      	  },
          function (error, result) {
            if (error) {
              console.log('移动用户分组 FAILED!');
              console.log(error);
            }
            else{
            	if (result.statusCode === 200) {
		        	console.log('移动用户分组 SUCCES!');
            		console.log(result.content);
		     	}
            }
          });
}

//批量移动用户分组
function remove_openIDList_to_group(openid_list, to_groupid){
	var http = 'https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=' + wxAccessToken;
	HTTP.call("POST", http,
          {
          	data: {
				"openid_list":openid_list,"to_groupid":to_groupid
          	}
      	  },
          function (error, result) {
            if (error) {
              console.log('批量移动用户分组 FAILED!');
              console.log(error);
            }
            else{
            	if (result.statusCode === 200) {
		        	console.log('批量移动用户分组 SUCCES!');
            		console.log(result.content);
		     	}
            }
          });
}

//创建菜单目录
function create_menu(){
	var http = 'https://api.weixin.qq.com/cgi-bin/menu/create?access_token=' + wxAccessToken;
	HTTP.call("POST", http,
          {
          	data: {
				"button":[
					{
					  "name":"美文特辑",
					  'sub_button':[
					  {
					  	"type":"view",
					  	"name":"情感",
					  	"url":"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+wxAppID+"&redirect_uri=http://guoyoujinlove123.meteor.com&response_type=code&scope=snsapi_base&state=123456#wechat_redirect"
					  },
					  {
					  	"type":"view",
					  	"name":"生活",
					  	"url":'http://itrydo.meteor.com'
					  }
					  ]
					},
					{
					   "name":"逗比特辑",
					   "sub_button":[
					   {
					       "type":"view",
					       "name":"呵呵~咯咯~",
					       "url":"http://www.baidu.com"
					    },
					    {
					       "type":"view",
					       "name":"趣味谜语",
					       "url":"http://itrydo.meteor.com"
					    }]
					}]
          	}
      	  },
          function (error, result) {
            if (error) {
              console.log('创建菜单目录 FAILED!');
              console.log(error);
            }
            else{
            	if (result.statusCode === 200) {
		        	console.log('创建菜单目录 SUCCES!');
            		console.log(result.content);
		     	}
            }
          });
}

//查询菜单
function get_menu(){
	var http = 'https://api.weixin.qq.com/cgi-bin/menu/get?access_token=' + wxAccessToken;
	console.log(http);
	Meteor.http.get(http, function (error, result) {
		if(error) {
		    console.log('查询菜单 FAILED!');
		} else {
		    console.log('查询菜单 SUCCES');
		    if (result.statusCode === 200) {
		        console.log('Status code = 200!');
		        console.log(result.content);
		     }
		}
	});
}




