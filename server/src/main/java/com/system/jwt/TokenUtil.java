package com.system.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

import java.util.Date;

/**
 * @author hxm
 */
public class TokenUtil {


    private static final String SECRET = "9527-hserver";

    public static String token(Integer userId, String username, Integer[] roleIds, long exp) {
        Date date = new Date(System.currentTimeMillis() + exp);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTCreator.Builder builder = JWT.create()
                .withClaim("username", username)
                .withClaim("userId", userId)
                .withArrayClaim("roleIds", roleIds);
        if (exp > 0) {
            return builder.withExpiresAt(date)
                    .sign(algorithm);
        }
        return builder.sign(algorithm);
    }

    public static boolean verify(String token, String username, Integer userId, Integer[] roleIds, long exp) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            Verification verification = JWT.require(algorithm)
                    .withClaim("username", username)
                    .withClaim("userId", userId)
                    .withArrayClaim("roleIds", roleIds);
            JWTVerifier verifier;
            if (exp > 0) {
                verifier = verification.acceptIssuedAt(System.currentTimeMillis())
                        .build();
            } else {
                verifier = verification.build();
            }
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static Token getToken(String token) {
        DecodedJWT decode = JWT.decode(token);
        Token token1 = new Token();
        token1.setExp(decode.getClaim("exp").asLong());
        token1.setUsername(decode.getClaim("username").asString());
        token1.setUserId(decode.getClaim("userId").asInt());
        token1.setRoleIds(decode.getClaim("roleIds").asArray(Integer.class));
        token1.setTokenStr(token);
        return token1;
    }
}
