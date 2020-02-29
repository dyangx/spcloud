package com.cloud.movie.mapper.test2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("testMapper2")
public interface TestMapper {
    void insertValue2();
}
