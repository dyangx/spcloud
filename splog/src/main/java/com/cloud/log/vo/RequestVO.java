package com.cloud.log.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

/**
 * @author: yangjie
 * @date: Created in 2020/6/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "req_c")
public class RequestVO implements Serializable {

    private static final long serialVersionUID=1L;

    private String _id;

    private String req_url;

    private String method;

    private String method_description;

    private String req_param;

    private String res_param;

    // 出参格式化
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    // 入参格式化
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Field(targetType = FieldType.DATE_TIME)
    private Date time;

    private Long exec_time;

    /**是否异常 1:是  0：否 */
    private Byte exception;

    private String exception_description;

}
