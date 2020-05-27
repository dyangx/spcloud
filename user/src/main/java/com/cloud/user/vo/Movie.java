package com.cloud.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Movie implements Serializable {
    private static final long serialVersionUID = 3945198704181334503L;

    private String id;
    private String name;
    private String discrption;
    private Date date;

}
