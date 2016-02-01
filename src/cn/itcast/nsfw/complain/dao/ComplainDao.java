package cn.itcast.nsfw.complain.dao;

import java.util.List;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.complain.entity.Complain;

public interface ComplainDao extends BaseDao<Complain> {

	public List<Object[]> getAnnualStatisticDataByYear(int year);
	
	
}
