package com.cloud.movie.mapper.test1;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface LcnMovieMapper {

    @Update("insert into t_movie (name) VALUES ('我是movieeeeeeeeeeeeeee')")
    void insert();
}
