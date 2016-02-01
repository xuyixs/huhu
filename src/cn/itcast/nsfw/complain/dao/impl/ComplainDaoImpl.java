package cn.itcast.nsfw.complain.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SQLQuery;

import cn.itcast.core.dao.impl.BaseDaoImpl;
import cn.itcast.core.page.PageResult;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.complain.dao.ComplainDao;
import cn.itcast.nsfw.complain.entity.Complain;

public class ComplainDaoImpl extends BaseDaoImpl<Complain> implements ComplainDao {

	public List<Object[]> getAnnualStatisticDataByYear(int year) {
		
		StringBuffer sb = new StringBuffer();
		
		//使用SQL语句
		sb.append("SELECT imonth, COUNT(comp_id)")
		.append(" FROM t_month LEFT JOIN complain ON imonth=MONTH(comp_time)")
		.append(" AND YEAR(comp_time)=?")
		.append(" GROUP BY imonth ")
		.append(" ORDER BY imonth");
		
		SQLQuery sqlQuery = getSession().createSQLQuery(sb.toString());
		sqlQuery.setParameter(0, year);
		return sqlQuery.list();
	}


}
