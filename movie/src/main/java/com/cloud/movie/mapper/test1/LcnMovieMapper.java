package com.cloud.movie.mapper.test1;

import org.apache.ibatis.annotations.Update;

public interface LcnMovieMapper {

    @Update("insert into t_movie (name) VALUES ('我是movieeeeeeeeeeeeeee')")
    void insert();
}
