package cn.itcast.nsfw.role.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.core.service.impl.BaseServiceImpl;
import cn.itcast.nsfw.role.dao.RoleDao;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.service.RoleService;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
	
	
	private RoleDao roleDao;
	@Resource
	public void setRoleDao(RoleDao roleDao) {
		super.setBaseDao(roleDao);
		this.roleDao = roleDao;
	}

	public void update(Role role) {
		// TODO 自动生成的方法存根
		
		//删除这个角色对应的权限
		roleDao.deleteRolePrivilegesByRoleId(role.getRoleId());
		
		roleDao.update(role);
	}

}
