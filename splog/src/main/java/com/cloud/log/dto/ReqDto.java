package com.cloud.log.dto;

import com.cloud.log.vo.Page;
import com.cloud.log.vo.RequestVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author: yangj
 * @date: Created in 2020/6/10
 */
@Data
public class ReqDto {

    private Page<RequestVO> page;

    private RequestVO requestVO;

    private String method;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}
