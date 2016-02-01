package cn.itcast.core.dao;

import java.io.Serializable;
import java.util.List;

import cn.itcast.core.page.PageResult;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;

public interface BaseDao<T> {
	
	//新增
	public void save(T entity);
	
	//更新
	public void update(T entity);
	//根据ID删除
	public void delete(Serializable id);
	//根据ID查找
	public T findObjectById(Serializable id);
	
	//查找列表
	public List<T> findObjects();
	//条件查询实体列表
	public List<T> findObjects(String hql, List<Object> parameters);
	
	//条件查询实体列表
	public List<T> findObjects(QueryHelper queryHelper);
	
	//分页查询
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize);
}
