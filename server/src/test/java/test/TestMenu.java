package test;

import com.system.dao.RoleDao;
import com.system.domain.vo.MenuVO;
import com.system.service.MenuService;
import com.system.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import top.hserver.core.ioc.annotation.Autowired;
import top.hserver.core.server.context.ConstConfig;
import top.hserver.core.test.HServerTest;

import java.util.List;

@RunWith(HServerTest.class)
public class TestMenu {

    @Autowired
    private MenuService menuService;

    @Test
    public void getMenu() throws Exception {
        List<MenuVO> list = menuService.list();
        String s = ConstConfig.JSON.writeValueAsString(list);
        System.out.println(s);
    }


    @Autowired
    private UserService userService;


    @Autowired
    private RoleDao roleDao;

    @Test
    public void rom(){

    }
}
