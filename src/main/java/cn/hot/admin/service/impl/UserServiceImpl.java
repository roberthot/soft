package cn.hot.admin.service.impl;

import cn.hot.admin.mapper.UsersMapper;
import cn.hot.admin.model.Users;
import cn.hot.admin.model.UsersExample;
import cn.hot.admin.service.UserService;
import cn.hot.restApi.ErrorEnum;
import cn.hot.restApi.Result;
import cn.hot.util.DateUtil;
import cn.hot.util.StringUtil;
import cn.hot.util.encrypt.MD5;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    UsersMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result queryAllUser(Users user) {
        Result result;
        try {
            UsersExample userExample = new UsersExample();
            UsersExample.Criteria userCri = userExample.createCriteria();
            if ("2".equals(user.getType())) {
                userCri.andAppKeyEqualTo(user.getAppKey());
            } else if ("3".equals(user.getType())) {
                userCri.andAppKeyEqualTo(user.getAppKey());
                userCri.andAddUserEqualTo(user.getId());
            }
            result = new Result(200, "查询所有项目人员成功");
            result.setData(userMapper.selectByExample(userExample));
        } catch (Exception e) {
            result = new Result(500, "查询所有项目人员失败");
            logger.info("查询所有项目人员失败，错误：" + e);
        }
        return result;
    }

    @Override
    public Result login(Users user) {
        Result result = new Result();
        UsersExample example = new UsersExample();
        UsersExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(user.getAccount());
        example.or().andPhoneEqualTo(user.getAccount());
        List<Users> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0 || list.size() > 1) {
            result.setStatus(0);
            result.setMsg(ErrorEnum.LOGIN_ACCOUNT_ERROR.getErrMsg());
        } else {
            if (MD5.encode(user.getPassword()).equals(list.get(0).getPassword())) {
                result.setData(list.get(0));
                result.setStatus(1);
                result.setMsg(ErrorEnum.LOGIN_SUCCESS.getErrMsg());
            } else {
                result.setStatus(0);
                result.setMsg(ErrorEnum.LOGIN_PASS_ERROR.getErrMsg());
            }
        }

        return result;
    }

    @Override
    public Result findUser(int pageSize, int pageNum, String dynamic, String userId, String userType, String appKey, String userAppKey) {
        Result result = new Result(200, "查询数据成功");
        Map<String, Object> map = new HashMap<>();
        map.put("condition", dynamic);
        map.put("userId", userId);
        map.put("type", userType);
        map.put("appKey", appKey);
        map.put("userAppKey", userAppKey);
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = userMapper.findUser(map);
        result.setData(new PageInfo<>(list));
        return result;
    }

    @Override
    public Result addUser(Users user, Long userId) {
        Result result;
        if (StringUtil.isBlank(user.getAccount()) || StringUtil.isBlank(user.getName()) || StringUtil.isBlank(user.getPassword())) {
            result = new Result(501, "参数不完整");
            return result;
        }
        try {
            UsersExample userExample = new UsersExample();
            userExample.or().andAccountEqualTo(user.getAccount());
            userExample.or().andPhoneEqualTo(user.getPhone());
            int num = userMapper.countByExample(userExample);
            if (num > 0) {
                return new Result(501, "手机号或编号重复");
            }
            user.setAddUser(userId);
            user.setPassword(MD5.encode(user.getPassword()));
            user.setAddTime(DateUtil.getDate());
            userMapper.insertSelective(user);
            result = new Result(200, "添加成功");
        } catch (Exception e) {
            logger.info("添加失败");
            result = new Result(500, "添加失败");
            logger.info("错误：" + e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
        }
        return result;
    }

    @Override
    public Result editUser(Users user, Long userId) {
        Result result;
        if (user.getId() == null) {
            result = new Result(501, "参数不完整");
            return result;
        }
        try {
            UsersExample userExample = new UsersExample();
            userExample.or().andAccountEqualTo(user.getAccount()).andIdEqualTo(user.getId()).andIdNotEqualTo(user.getId());
            userExample.or().andPhoneEqualTo(user.getPhone()).andIdEqualTo(user.getId()).andIdNotEqualTo(user.getId());
            int num = userMapper.countByExample(userExample);
            if (num > 0) {
                return new Result(501, "手机号或编号重复");
            }
            user.setUpdateUser(userId);
            user.setUpdateTime(DateUtil.getDate());
            userMapper.updateByPrimaryKeySelective(user);
            result = new Result(200, "修改成功");
        } catch (Exception e) {
            logger.info("添加失败");
            logger.info("错误：" + e);
            result = new Result(500, "修改失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
        }
        return result;
    }

    @Override
    public Result delUser(String userId) {
        Result result;
        if (StringUtil.isBlank(userId)) {
            result = new Result(501, "参数不完整");
            return result;
        }
        try {
            String[] str = userId.split(",");
            for (String s : str) {
                userMapper.deleteByKey(Long.valueOf(s));
            }
            result = new Result(200, "删除成功");
        } catch (Exception e) {
            logger.info("删除失败");
            result = new Result(500, "删除失败");
            logger.info("错误：" + e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
        }
        return result;
    }

    @Override
    public Result queryMessage(Users user, String pageSize, String pageNum) {
        Result result;
        try {
            Long id = user.getId();
            result = new Result(200, "查询系统消息成功");
            PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
            result.setData(new PageInfo(userMapper.queryMessage(id)));
        } catch (Exception e) {
            result = new Result(500, "查询系统消息失败");
            logger.info("查询系统消息失败，错误：" + e);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveOrUpdateUser(Users newUser, Long uid) {
        Result result;
        try {
            if (newUser.getId() == 0 || newUser.getId() == null) {
                newUser.setAddUser(uid);
                newUser.setAddTime(DateUtil.getDate());
                result = new Result(200, "添加用户成功");
                userMapper.insertSelective(newUser);
            } else {
                newUser.setUpdateUser(uid);
                newUser.setUpdateTime(DateUtil.getDate());
                result = new Result(200, "修改用户成功");
                userMapper.updateByPrimaryKeySelective(newUser);
            }
        } catch (Exception e) {
            result = new Result(500, "添加或修改用户失败");
            logger.info("添加或修改用户失败，错误：" + e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updatePassword(String userId, String password, String newPassword) {
        // MD5.encode(password)
        Result result;
        try {
            Users user = userMapper.selectByPrimaryKey(Long.parseLong(userId));
            String oldPassword = user.getPassword();
            if (!oldPassword.equals(MD5.encode(password))) {
                result = new Result(200, "原密码错误");
                return result;
            }
            user.setPassword(MD5.encode(newPassword));
            userMapper.updateByPrimaryKeySelective(user);
            result = new Result(200, "修改密码成功");
            return result;
        } catch (Exception e) {
            result = new Result(500, "修改密码失败");
            logger.info("修改密码失败，错误：" + e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
        }
        return result;
    }

    @Override
    public List<Users> getSelectUserByLoginUser(Users user) {
        Map<String, Object> map = new HashMap<>();
        map.put("appkey", user.getAppKey());
        map.put("id", user.getId());
        map.put("type", user.getType());
        return userMapper.queryUserByType(map);
    }

    @Override
    public Users getById(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
