package cn.itcast.nsfw.role.dao.impl;

import org.hibernate.Query;

import cn.itcast.core.dao.impl.BaseDaoImpl;
import cn.itcast.nsfw.role.dao.RoleDao;
import cn.itcast.nsfw.role.entity.Role;

public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

	@Override
	public void deleteRolePrivilegesByRoleId(String roleId) {
		
		Query query = getSession().createQuery("delete from RolePrivileges where id.role.roleId=?");
		query.setParameter(0, roleId);
		query.executeUpdate();
	}

	
}
