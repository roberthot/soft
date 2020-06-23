package cn.hot.admin.mapper;

import cn.hot.admin.model.Users;
import cn.hot.admin.model.UsersExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UsersMapper {
    int countByExample(UsersExample example);

    int deleteByExample(UsersExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Users record);

    int insertSelective(Users record);

    List<Users> selectByExample(UsersExample example);

    Users selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Users record, @Param("example") UsersExample example);

    int updateByExample(@Param("record") Users record, @Param("example") UsersExample example);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    List<Map<String, Object>> findUser(Map<String, Object> map);

    List<Map<String, Object>> queryMessage(@Param("id") Long id);

    int deleteByKey(Long id);

    List<Users> queryUserByType(Map<String, Object> map);
}