package org.user.controllor;

import bean.datatables.DatatableOrder;
import bean.datatables.DatatableRequest;
import bean.datatables.DatatableResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import common.JsonData;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    @RequestMapping("/main")
    public String main(){
        return "/common/main";
    }

    @RequestMapping("/index")
    public String index(){
        return "/common/index";
    }


    @RequestMapping("/getAllUserIndex")
    public Object getAllUserIndex(){

        return "/user/sysUser";
    }

    @RequestMapping("/getAllUser")
    @ResponseBody
    public Object getAllUser(String callback,@RequestBody DatatableRequest request){
//        PageHelper.startPage(1,2);
//        List<SysUser> sysUserList=this.userService.getAllUser();
//        PageInfo<SysUser> sysUserPageInfo = new PageInfo<>(sysUserList);
//        HashMap hashMap = new HashMap();
//        hashMap.put("data",sysUserList);

        DatatableResponse<SysUser> response = new DatatableResponse<SysUser>();
        response.setDraw(request.getDraw());
        //分页
        Integer start = request.getStart();
        Integer length = request.getLength();
        PageHelper.startPage(start, length);
        //对应数据库中的列名称
        String [] columnNames = {"id","loginname","password","login_mark"};

        //排序
        /*
         * request.getOrder()中的数据可能如下：
         * [DatatableOrder [column=0, dir=asc], DatatableOrder [column=2, dir=desc]]
         * 经过for循环处理后：
         * [DatatableOrder [column=0, dir=dept_id asc], DatatableOrder [column=2, dir=parent_id desc]]
         * 此时，orderBy = dept_id asc,dir=parent_id desc
         * 至此组成完整的sql语句：
         * select * from tableName
         * where condition
         * limit start, length
         * order by dept_id asc,dir=parent_id desc
         */
        for(DatatableOrder order : request.getOrder()) {
            order.setDir(StringUtils.join(Arrays.asList(columnNames[order.getColumn()], order.getDir()), " "));
        }

        String orderBy = StringUtils.join(request.getOrder().stream().map(DatatableOrder::getDir).toArray(), ",");
        PageHelper.orderBy(orderBy);

        List<SysUser> depts = userService.getAllUser(request.getParamMap());

        PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(depts);
        response.setRecordsTotal((int)pageInfo.getTotal());
        response.setRecordsFiltered((int)pageInfo.getTotal());
        response.setData(pageInfo.getList());


        String json = JsonMapper.obj2String(response);
        return callback.concat("(").concat(json).concat(")");

    }


}
