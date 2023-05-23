package net.hserver.hp.proxy.domian.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author hxm
 */
@Data
public class UserVo {
    private String id;

    private String username;

    private String password;

    //-1 封号
    private Integer type;

    /**
     * 自定义的域名
     */
    private String customDomain;

    /**
     * 开通的端口号
     */
    private List<Integer> ports;

}
