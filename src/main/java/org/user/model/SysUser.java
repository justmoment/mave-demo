package org.user.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SysUser {
    private Integer id;

    @NotNull(message = "不能为空")
    private String loginname;
    @NotNull
    private String password;
    @Min(value = 1,message = "最小要大于1")
    private String loginMark;

    private String checkValiCode;

    private String valiCode;

    private Integer erroCount;

    private Date erroDate;

    private Date createtime;


}