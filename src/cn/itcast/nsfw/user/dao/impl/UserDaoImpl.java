package cn.itcast.nsfw.user.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;

import cn.itcast.core.dao.impl.BaseDaoImpl;
import cn.itcast.nsfw.user.dao.UserDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
	


public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public List<User> findUserByAccountAndId(String id, String account) {
		
		String hql = "from User where account=?";
		if(StringUtils.isNotBlank(id)){
			hql += " and id!=?";
		}
		Query query = getSession().createQuery(hql);
		query.setParameter(0, account);
		if(StringUtils.isNoneBlank(id)){
			query.setParameter(1, id);
		}
		
		return query.list();
	}

	public void saveUserRole(UserRole userRole) {
		
		getHibernateTemplate().save(userRole);
		
	}

	public void deleteUserRoleByUserId(Serializable id) {
		
		//delete from user_role where user_id=id
		Query query = getSession().createQuery("delete from UserRole where id.userId=?");
		query.setParameter(0, id);
		query.executeUpdate();
		
	}

	public List<UserRole> getUserRolesByUserId(String id) {
		Query query = getSession().createQuery("from UserRole where id.userId=?");
		query.setParameter(0, id);
		return query.list();
	}

	@Override
	public List<User> findUsersByAccountAndPass(String account, String password) {
		Query query = getSession().createQuery("from User where account=? and password=?");
		query.setParameter(0, account);
		query.setParameter(1, password);
		return query.list();
	}

}
