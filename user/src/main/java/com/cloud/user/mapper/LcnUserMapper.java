package com.cloud.user.mapper;

import org.apache.ibatis.annotations.Update;

public interface LcnUserMapper {

    @Update("insert into t_user(name) VALUES ('我是userrrrrrrrrrr');")
    void insetValue();
}
