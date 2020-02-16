package com.cloud.user.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cloud_user")
public class User implements Serializable {
    private static final long serialVersionUID = 6176854761799123463L;

    @TableId(type = IdType.AUTO)
    private String id;
    private String name;
    private String phone;
    private String adress;

}
