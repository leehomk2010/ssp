package com.es.ssp.interceptor;

import com.es.ssp.annotation.NeedFans;
import com.es.ssp.model.Fans;
import com.es.ssp.service.FansManager;
import com.es.ssp.wechatapi.WechatApi;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import weixin.popular.bean.sns.SnsToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Enumeration;

@Component
@EnableAsync
public class FansInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private FansManager fansManager;
    @Autowired
    private WechatApi wechatApi;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod))return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(handlerMethod.getBean().getClass().isAnnotationPresent(NeedFans.class)||handlerMethod.hasMethodAnnotation(NeedFans.class)){
            String openId=request.getParameter("openId");
            if(StringUtils.isEmpty(openId)){
                String code=request.getParameter("code");
                String basePath=request.getScheme() + "://"
                        + request.getServerName()+request.getContextPath()+"/";
                String uri=request.getRequestURI().replace(request.getContextPath()+"/","");
                Enumeration<String> e= request.getParameterNames();
                if(e!=null){
                    while (e.hasMoreElements()){
                        String name=request.getParameter(e.nextElement());
                        if(name.equals("code")||name.equals("state")){

                        }else{
                            if(uri.indexOf("?")==-1){
                                uri+="?";
                            }else{
                                uri+="&";
                            }
                            uri+=(name+"="+request.getParameter(name));
                        }
                    }
                }
                if(StringUtils.isEmpty(code)){
                    String url=wechatApi.snsInfo(basePath+uri);
                    response.sendRedirect(url);
                    return false;
                }else{
                    SnsToken snsToken = wechatApi.snsToken(code);
                    if("40029".equals(snsToken.getErrcode())||"40163".equals(snsToken.getErrcode())){
                        String url=wechatApi.snsInfo(basePath+uri);
                        response.sendRedirect(url);
                        return false;
                    }else{
                        Fans fans=fansManager.getUnknownFans(snsToken.getOpenid());
                        if(fans!=null){
                            if(StringUtils.isEmpty(fans.getNickName())){
                                fansManager.updateFansInfo(fans.getFansId(),snsToken.getAccess_token());
                            }
                        }
                        request.setAttribute("fans",fans);
                    }
                }
            }else{
                request.setAttribute("fans",fansManager.findByOpenId(openId));
            }
        }
        return true;
    }
}
