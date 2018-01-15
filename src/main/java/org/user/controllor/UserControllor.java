package org.user.controllor;

import com.google.common.collect.Maps;
import common.JsonData;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.user.model.SysUser;
import org.user.service.UserService;
import util.BeanValidator;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("/user")
public class UserControllor {

    @Autowired
    private UserService userService;

    @RequestMapping("json.json")
    @ResponseBody
    public SysUser json(SysUser sysUser){
        BeanValidator.check(sysUser);


        return this.userService.json();

    }

    @RequestMapping(value = "/checkLoginName",method= RequestMethod.GET)
    public void checkLoginName(@Param("loginname") String loginname, HttpServletResponse response) throws Exception{
        SysUser sys=this.userService.checkLoginName(loginname);
        if(null==sys){
            response.getWriter().print(false);
        }else{
            response.getWriter().print(true);
        }
    }

    @RequestMapping(value = "/checkLoginNameAndPassoword",method= RequestMethod.POST)
    @ResponseBody
    public Object checkLoginNameAndPassoword(SysUser sysUser){
        HashMap<Object, Object> map = Maps.newHashMap();


        //失败次数太多，冻结账户
        if(sysUser.getErroCount()>5){
            //通过redis来操作


        }

        if(sysUser.getErroCount()>2){
            if(!sysUser.getValiCode().equalsIgnoreCase(sysUser.getCheckValiCode())){
                map.put("flag",false);
                map.put("erroCount",sysUser.getErroCount());
                map.put("message","验证码错误");
                return map;
            }
        }

        if(StringUtils.isBlank(sysUser.getLoginname())||StringUtils.isBlank(sysUser.getPassword())){
            map.put("flag",false);
            map.put("erroCount",sysUser.getErroCount()+1);
            map.put("message","用户名或密码不能为空");
            return map;
        }

        SysUser sys=this.userService.checkLoginNameAndPassoword(sysUser);
        if(null!=sys){
            map.put("flag",true);
            return map;
        }
        map.put("erroCount",sysUser.getErroCount()+1);
        map.put("flag",false);
        map.put("message","用户名或密码错误");
        return map;
    }


}
