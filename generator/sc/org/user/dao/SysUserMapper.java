package org.user.dao;

import org.user.model.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);


    SysUser checkLoginName(String  loginname);

    SysUser checkLoginNameAndPassoword(SysUser sysUser);

    List<SysUser> getAllUser(Map map);
}