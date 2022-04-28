package com.cloud.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.user.vo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

    List<User> selectUser();

    @Select("select * from cloud_user where id = 1")
    User getOneUser();

}
