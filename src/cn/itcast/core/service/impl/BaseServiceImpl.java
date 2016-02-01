package cn.itcast.core.service.impl;

import java.io.Serializable;
import java.util.List;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.core.page.PageResult;
import cn.itcast.core.service.BaseService;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;

public class BaseServiceImpl<T> implements BaseService<T> {

	private BaseDao<T> baseDao;
	
	public void setBaseDao(BaseDao<T> baseDao) {
		
		this.baseDao = baseDao;
	}
	
	public List<T> findObjects(String hql, List<Object> parameters) {
		return baseDao.findObjects(hql,parameters);
	}

	public void save(T t) {
		baseDao.save(t);
		
	}
	
	public void update(T t) {
		baseDao.update(t);
		
	}

	
	public void delete(Serializable id) {
		baseDao.delete(id);
		
	}

	
	public T findObjectById(Serializable id) {
		
		return baseDao.findObjectById(id);
	}

	
	public List<T> findObjects() {
		
		return baseDao.findObjects();
	}
	
	public List<T> findObjects(QueryHelper queryHelper) {
		return baseDao.findObjects(queryHelper);
	}

	public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize) {
		return baseDao.getPageResult(queryHelper, pageNo, pageSize);
	}


	
	
	
}
