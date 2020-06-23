package cn.hot.admin.service;

import cn.hot.admin.model.Users;
import cn.hot.restApi.Result;

import java.util.List;

public interface UserService {

    //查询所有用户，用于添加项目人员
    Result queryAllUser(Users user);

    Result login(Users user);

    Result findUser(int pageSize, int pageNum, String dynamic, String userId, String userType, String appKey, String userAppKey);

    Result addUser(Users user, Long userId);

    Result editUser(Users user, Long userId);

    Result delUser(String userId);

    Result queryMessage(Users user, String pageSize, String pageNum);

    //添加或修改用户
    Result saveOrUpdateUser(Users newUser, Long uid);

    /**
     * 修改密码
     *
     * @param userId
     * @param password
     * @param newPassword
     * @return
     */
    Result updatePassword(String userId, String password, String newPassword);

    List<Users> getSelectUserByLoginUser(Users user);
    Users getById(Long userId);
}
