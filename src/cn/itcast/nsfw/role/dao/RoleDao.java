package cn.itcast.nsfw.role.dao;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.role.entity.Role;

public interface RoleDao extends BaseDao<Role> {

	public void  deleteRolePrivilegesByRoleId(String roleId);
		
}
