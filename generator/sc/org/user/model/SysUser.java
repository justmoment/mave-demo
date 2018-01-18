package org.user.model;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
import java.util.Date;

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