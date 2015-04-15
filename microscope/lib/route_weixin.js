Meteor.methods({
    getWxAccessToken: function(code){
        var url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wxAppID + "&secret=" + wxSecret + "&code=" + code + "&grant_type=authorization_code";
        var result = Meteor.http.get(url, {timeout:30000});
        if(result.statusCode==200) {
            return result.content;
        } else {
            console.log("Response issue: ", result.statusCode);
            var errorJson = JSON.parse(result.content);
            throw new Meteor.Error(result.statusCode, errorJson.error);
        }
    },
    getWxUserInfo: function(code) {
        var result = Meteor.call("getWxAccessToken",code);
        var respJson = JSON.parse(result);
        var url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + respJson.access_token + "&openid=" + respJson.openid + "&lang=zh_CN";
        var result = Meteor.http.get(url, {timeout:30000});
        if(result.statusCode==200) {
            var respJson = JSON.parse(result.content);
            console.log("response received.");
            console.log(respJson);
            return result.content;
        }else{
            console.log("Response issue: ", result.statusCode);
            var errorJson = JSON.parse(result.content);
            throw new Meteor.Error(result.statusCode, errorJson.error);
        }
    }
});