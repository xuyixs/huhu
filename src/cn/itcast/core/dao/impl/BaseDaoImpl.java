package cn.itcast.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.core.page.PageResult;
import cn.itcast.core.util.QueryHelper;

public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl(){
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz =(Class<T>) pt.getActualTypeArguments()[0];
	}
	
	
	@Override
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize) {
		//查询列表
		Query query = getSession().createQuery(queryHelper.getQueryListHql());
		List<Object> parameters = queryHelper.getParameters();
		if(parameters != null){
			for(int i = 0; i < parameters.size(); i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		if(pageNo < 1) pageNo = 1;
		query.setFirstResult((pageNo - 1)*pageSize);//设置数据起始索引号
		query.setMaxResults(pageSize);
		List items = query.list();
		
		//获取总记录数
		Query queryCount = getSession().createQuery(queryHelper.getQueryCountHql());
		if(parameters != null){
			for(int i = 0; i < parameters.size(); i++){
				queryCount.setParameter(i, parameters.get(i));
			}
		}
		long totalCount = (long) queryCount.uniqueResult();
		
		return new PageResult(totalCount, pageNo, pageSize, items);
	}


	public List<T> findObjects(String hql, List<Object> parameters) {
		Query query = getSession().createQuery("from Info where title like ? and state=? order by createTime ");  //session为Hibernate自己的事
		if(parameters != null){
			for(int i = 0; i < parameters.size(); i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		
		return query.list();
	}
	
	public List<T> findObjects(QueryHelper queryHelper) {
		Query query = getSession().createQuery(queryHelper.getQueryListHql());
		List<Object> parameters = queryHelper.getParameters();
		if(parameters != null){
			for(int i = 0; i < parameters.size(); i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		
		return query.list();
	}
	
	public void save(T entity) {
		getHibernateTemplate().save(entity);
		
	}

	
	public void update(T entity) {
		getHibernateTemplate().update(entity);
		
	}

	
	public void delete(Serializable id) {
		getHibernateTemplate().delete(findObjectById(id));
		
	}

	
	public T findObjectById(Serializable id) {
		
		return getHibernateTemplate().get(clazz, id);
	}

	
	public List<T> findObjects() {
		Query query = getSession().createQuery("from " + clazz.getSimpleName());  //session为Hibernate自己的事
		return query.list();
	}
	
	
}
