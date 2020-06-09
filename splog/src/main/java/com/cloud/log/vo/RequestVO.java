package com.cloud.log.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: yangjie
 * @date: Created in 2020/6/9 10:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestVO {

    private String reqtUrl;

    private String method;

    private String methodDescription;

    private String reqParam;

    private String resParam;

    private Date startTime;

    private Long execTime;

    /**是否异常 1:是  0：否 */
    private Integer exception;

    private String exceptionDescription;

}
