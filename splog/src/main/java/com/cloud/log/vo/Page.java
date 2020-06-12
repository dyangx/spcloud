package com.cloud.log.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @author: yangj
 * @date: Created in 2020/6/10
 */
@Data
@NoArgsConstructor
public class Page<T> {

    private int current = 1;

    private int size = 10;

    private long total = 0;

    private List<T> record = Collections.EMPTY_LIST;

    @JsonIgnore
    private boolean searchCount = Boolean.TRUE;

    public Page(List<T> record){
        this.record = record;
        this.total = this.record.size();
    }
}
