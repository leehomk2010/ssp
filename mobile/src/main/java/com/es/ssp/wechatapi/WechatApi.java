package com.es.ssp.wechatapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;


@Component
public class WechatApi {

    @Value("${wechat.app-id}")
    private  String appID ;//这里是AppId,我放到配置文件中了,也可以在这里写,直接定义全局变量,下面的开发者密码一样
    @Value("${wechat.app-secret}")
    private String appSecret;//AppSecret,开发者密码

    /**
     * 网页授权base
     * @param url
     * @return
     */
    public String snsBase(String url){
        return SnsAPI.connectOauth2Authorize(appID,url,false,"STATE");
    }

    /**
     * 网页授权userinfo
     * @param url
     * @return
     */
    public String snsInfo(String url){
        return SnsAPI.connectOauth2Authorize(appID,url,true,"STATE");
    }

    /**
     * 获取snsToken
     * @param code
     * @return
     */
    public SnsToken snsToken(String code){
        return SnsAPI.oauth2AccessToken(appID,appSecret,code);
    }

    /**
     * 网页授权获取用户信息
     * @param token
     * @param openId
     * @return
     */
    public User userinfo(String token, String openId){
        return SnsAPI.userinfo(token,openId,"zh_CN");
    }
}
