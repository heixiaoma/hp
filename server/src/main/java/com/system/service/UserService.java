package com.system.service;

import com.system.dao.UserDao;
import com.system.dao.UserRoleDao;
import com.system.domain.dto.LoginDTO;
import com.system.domain.dto.UserDTO;
import com.system.domain.entity.UserEntity;
import com.system.domain.entity.UserRoleEntity;
import com.system.domain.vo.LoginVO;
import com.system.domain.vo.UserInfoVO;
import com.system.domain.vo.UserVO;
import com.system.jwt.TokenUtil;
import com.system.utils.MD5;
import org.beetl.sql.core.page.PageResult;
import top.hserver.core.ioc.annotation.Autowired;
import top.hserver.core.ioc.annotation.Bean;
import top.hserver.core.ioc.annotation.Value;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author hxm
 */
@Bean
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private TokenService tokenService;

    @Value("token.exp")
    private Long exp;

    public UserInfoVO getUserInfo(Integer userId) {
        UserEntity userByUserId = userDao.findUserByUserId(userId);
        if (userByUserId == null) {
            return null;
        }
        List<UserVO.UserRoleVO> userRoleVOS = userDao.selectRole(userId);
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setAvatar(userByUserId.getAvatar());
        userInfoVO.setUserId(userByUserId.getId());
        userInfoVO.setUsername(userByUserId.getUsername());
        userInfoVO.setName(userByUserId.getNickName());
        userInfoVO.setIntroduction("铁憨憨一个");

        List<String> collect = userRoleVOS.stream().map(UserVO.UserRoleVO::getName).collect(Collectors.toList());
        List<Integer> collect1 = userRoleVOS.stream().map(UserVO.UserRoleVO::getId).collect(Collectors.toList());
        userInfoVO.setRoles(collect);
        userInfoVO.setRolesId(collect1);
        return userInfoVO;
    }


    public LoginVO login(LoginDTO loginDTO) {
        UserEntity userEntity = userDao.findUserByUsername(loginDTO.getUsername());
        if (userEntity == null) {
            return null;
        }
        String md5 = MD5.getMd5(loginDTO.getPassword());
        if (md5 == null) {
            return null;
        }
        if (md5.equalsIgnoreCase(userEntity.getPassword())) {
            List<UserRoleEntity> select = userRoleDao.createLambdaQuery().andEq(UserRoleEntity::getUserId, userEntity.getId()).select();
            if (!select.isEmpty()) {
                Integer[] ints = new Integer[select.size()];
                for (int i = 0; i < ints.length; i++) {
                    ints[i] = select.get(i).getRoleId();
                }
                String token = TokenUtil.token(userEntity.getId(), userEntity.getUsername(), ints, exp);
                tokenService.addToken(token, userEntity.getId());
                return new LoginVO(token, userEntity.getUsername(), userEntity.getId(), userEntity.getNickName(), userEntity.getAvatar(), ints);
            }
        }
        return null;
    }


    public PageResult<UserVO> list(long pageNumber, long pageSize) {
        return userDao.list(pageNumber, pageSize);
    }

    public void edit(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setAvatar(userDTO.getAvatar());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setNickName(userDTO.getNickName());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setState(userDTO.getState());
        userEntity.setUpdateTime(new Date());
        userDao.updateById(userEntity);
        userRoleDao.deleteUserId(userDTO.getId());
        List<Integer> roleIds = userDTO.getRoleIds();
        for (Integer roleId : roleIds) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setRoleId(roleId);
            userRoleEntity.setUserId(userDTO.getId());
            userRoleDao.insert(userRoleEntity);
        }
    }

    public void add(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setAvatar(userDTO.getAvatar());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(MD5.getMd5(userDTO.getPassword()));
        userEntity.setNickName(userDTO.getNickName());
        userEntity.setState(userDTO.getState());
        userDao.insert(userEntity);
        UserEntity userByUsername = userDao.findUserByUsername(userDTO.getUsername());
        List<Integer> roleIds = userDTO.getRoleIds();
        for (Integer roleId : roleIds) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setRoleId(roleId);
            userRoleEntity.setUserId(userByUsername.getId());
            userRoleDao.insert(userRoleEntity);
        }
    }


    public void delete(Integer id) {
        userDao.deleteById(id);
        userRoleDao.deleteUserId(id);
    }

    public void multiDelete(List<Integer> ids) {
        for (Integer id : ids) {
            userDao.deleteById(id);
            userRoleDao.deleteUserId(id);
        }
    }

}
