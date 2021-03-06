package org.user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.user.dao.SysUserMapper;
import org.user.model.SysUser;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public SysUser json(){
        return this.sysUserMapper.selectByPrimaryKey(1);

    }

    public SysUser checkLoginName(String  loginname) {
        return this.sysUserMapper.checkLoginName(loginname);
    }

    public SysUser checkLoginNameAndPassoword(SysUser sysUser) {
        return this.sysUserMapper.checkLoginNameAndPassoword(sysUser);
    }

    public List<SysUser> getAllUser(Map map) {
        return this.sysUserMapper.getAllUser(map);
    }
}
