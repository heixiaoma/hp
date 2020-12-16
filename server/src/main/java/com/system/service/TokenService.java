package com.system.service;

import com.system.dao.TokenDao;
import com.system.domain.entity.TokenEntity;
import top.hserver.core.ioc.annotation.Autowired;
import top.hserver.core.ioc.annotation.Bean;

import java.util.Date;
import java.util.List;

/**
 * @author hxm
 */
@Bean
public class TokenService {

    @Autowired
    private TokenDao tokenDao;


    public void addToken(String token, Integer userId) {
        tokenDao.removeToken(userId);
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setUserId(userId);
        tokenEntity.setCreateTime(new Date());
        tokenDao.insert(tokenEntity);
    }

    public boolean removeToken(Integer userId) {
        return tokenDao.removeToken(userId);
    }

    public boolean exist(String token) {
        List<TokenEntity> select = tokenDao.createLambdaQuery().andEq(TokenEntity::getToken, token).select();
        if (select.isEmpty()) {
            return false;
        }
        return true;
    }


}
