package com.cloud.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 6176854761799123463L;

    private String id;
    private String name;
    private String phone;
    private String adress;

}
