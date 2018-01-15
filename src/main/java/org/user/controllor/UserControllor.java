package org.user.controllor;

import com.google.common.collect.Maps;
import common.JsonData;
import org.apache.commons.collections.MapUtils;
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

    @RequestMapping(value = "/checkLoginName",method= RequestMethod.POST)
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
    public Object checkLoginNameAndPassoword(SysUser sysUser,String count){
        HashMap<Object, Object> objectObjectHashMap = Maps.newHashMap();
        BeanValidator.check(sysUser);
        SysUser sys=this.userService.checkLoginNameAndPassoword(sysUser);
        if(null==sys){
            return JsonData.success();
        }
        return JsonData.fail("用户名或密码不存在");
    }


}
