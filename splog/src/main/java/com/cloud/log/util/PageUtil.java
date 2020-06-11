package com.cloud.log.util;

import com.cloud.log.vo.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.List;

/**
 * @author: yangj
 * @date: Created in 2020/6/11
 */
public class PageUtil {

    public static <T> Page<T> getPage(List<T> list, Pageable pageable){
        PageableExecutionUtils.getPage(list,pageable,()->0);
        return null;
    }
}
