package com.system.jwt;

import com.system.domain.vo.UserInfoVO;
import com.system.service.RoleService;
import com.system.service.TokenService;
import com.system.service.UserService;
import top.hserver.core.interfaces.PermissionAdapter;
import top.hserver.core.ioc.annotation.*;
import top.hserver.core.server.context.Webkit;
import top.hserver.core.server.util.JsonResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hxm
 */
@Bean
public class PermissionCheck implements PermissionAdapter {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Value("token.exp")
    private Long exp;

    @Override
    public void requiresPermissions(RequiresPermissions requiresPermissions, Webkit webkit) throws Exception {
        try {
            Map<Integer, List<String>> rolePermission = roleService.getRolePermission();
            String token = webkit.httpRequest.getHeader("X-Token");
            if (token == null) {
                webkit.httpResponse.sendJson(JsonResult.error(-2, "缺少 X-Token"));
                return;
            }
            //查询token 是否存在不存在则主动失效.
            //校验token 是否合规
            if (!tokenService.exist(token)) {
                webkit.httpResponse.sendJson(JsonResult.error(-3, "token不存在，请重新登录"));
                return;
            }
            UserInfoVO userInfo = userService.getUserInfo(TokenUtil.getToken(token).getUserId());
            if (userInfo == null) {
                webkit.httpResponse.sendJson(JsonResult.error(-3, "用户不存在"));
                return;
            }
            List<Integer> rolesId = userInfo.getRolesId();
            Integer[] integers = rolesId.toArray(new Integer[rolesId.size()]);
            if (!TokenUtil.verify(token, userInfo.getUsername(), userInfo.getUserId(), integers,exp)) {
                webkit.httpResponse.sendJson(JsonResult.error(-3, "token 过期"));
                return;
            }
            //校验是否有权限
            List<String> permission = new ArrayList<>();
            for (Integer integer : rolesId) {
                List<String> list = rolePermission.get(integer);
                if (list != null) {
                    permission.addAll(list);
                }
            }
            for (String s : requiresPermissions.value()) {
                if (requiresPermissions.logical().equals(Logical.AND)) {
                    if (!permission.contains(s)) {
                        webkit.httpResponse.sendJson(JsonResult.error(-4, "权限不足"));
                        return;
                    }
                } else {
                    if (permission.contains(s)) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            webkit.httpResponse.sendJson(JsonResult.error(-5, "权限校验异常"));
        }
    }

    @Override
    public void requiresRoles(RequiresRoles requiresRoles, Webkit webkit) throws Exception {

    }

    @Override
    public void sign(Sign sign, Webkit webkit) throws Exception {

    }
}
