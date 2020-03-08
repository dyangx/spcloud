package com.cloud.shop.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
public interface LcnShopMapper {

    @Update("insert into t_shop (name) VALUES ('我是shopppppppppppppp');")
    void insert();
}
