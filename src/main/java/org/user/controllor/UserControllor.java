package org.user.controllor;

import com.google.common.collect.Maps;
import common.JsonData;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.user.model.CacheKeyConstants;
import org.user.model.SysUser;
import org.user.service.SysCacheService;
import org.user.service.UserService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import util.BeanValidator;
import util.JsonMapper;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("/user")
public class UserControllor {

    @Autowired
    private UserService userService;

    @Autowired
    private SysCacheService sysCacheService;

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

            //通过redis来操作
           String value= sysCacheService.getFromCache(CacheKeyConstants.USER_NAME,sysUser.getLoginname());
           if(StringUtils.isNotBlank(value)){
               SysUser sys = JsonMapper.string2Obj(value, new TypeReference<SysUser>() {});

               if(sys.getErroCount()>5){
                   long freezingTime = (new Date().getTime() - sys.getErroDate().getTime())/1000;
                   map.put("flag",false);
                   map.put("erroCount",sysUser.getErroCount());
                   map.put("message","账号被冻结,还差"+(120-freezingTime)+"秒解冻");
                   return map;
               }


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
            sysCacheService.delKey(CacheKeyConstants.USER_NAME,sysUser.getLoginname());
            return map;
        }

        map.put("erroCount",sysUser.getErroCount()+1);
        map.put("flag",false);

        if(StringUtils.isBlank(value)){
            sysCacheService.saveCache(JsonMapper.obj2String(SysUser.builder().erroCount(0).build()),120, CacheKeyConstants.USER_NAME,sysUser.getLoginname());
        }else{
            sysCacheService.incre(CacheKeyConstants.USER_NAME,sysUser.getLoginname());
        }

        map.put("message","用户名或密码错误");
        return map;
    }

    @RequestMapping("/del")
    @ResponseBody
    public void delOne(){
        this.sysCacheService.delKey(CacheKeyConstants.USER_NAME,"admin");
    }


}
