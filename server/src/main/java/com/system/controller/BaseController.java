package com.system.controller;


import com.system.jwt.TokenUtil;
import top.hserver.core.interfaces.HttpRequest;

/**
 *
 * 对于获取Token，或者通过Token获取用户信息的，封装在这里，然后需要的就自己集成就可以了
 *
 * @author hxm
 */
public class BaseController {

    public Integer getUserId(HttpRequest request){
        String token = request.getHeader("X-Token");
        return TokenUtil.getToken(token).getUserId();
    }

    public Integer[] getRoleIds(HttpRequest request){
        String token = request.getHeader("X-Token");
        return TokenUtil.getToken(token).getRoleIds();
    }

}
