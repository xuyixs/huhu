package cn.itcast.core.page;

import java.util.ArrayList;
import java.util.List;

public class PageResult {
	
	//记录总数
	private long totalCount;
	//当前页号
	private int pageNo;
	//总页数
	private int totalPageCount;
	//页大小
	private int pageSize;
	//列表记录
	private List items;
	
	
	
	//计算总页数
	public PageResult(long totalCount, int pageNo, int pageSize, List items) {
		
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.items = items==null ? new ArrayList() : items;
		if(totalCount != 0){
			int tem = (int) (totalCount/pageSize);
			this.totalPageCount = (int) (totalCount % pageSize)==0 ? tem : (tem + 1);
			this.pageNo = pageNo;
		}else{
			this.pageNo = 0;
		}
	}
	
	
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List getItems() {
		return items;
	}
	public void setItems(List items) {
		this.items = items;
	}
	
	
}
