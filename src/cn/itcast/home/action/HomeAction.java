package cn.itcast.home.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.management.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.itcast.core.constant.Constant;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.complain.entity.Complain;
import cn.itcast.nsfw.complain.service.ComplainService;
import cn.itcast.nsfw.info.entity.Info;
import cn.itcast.nsfw.info.service.InfoService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;
import net.sf.json.JSONObject;

public class HomeAction extends ActionSupport {
	
	@Resource
	private UserService userService;
	@Resource
	private ComplainService complainService;
	@Resource
	private InfoService infoService;
	private Complain comp;
	
	private Map<String,Object> return_map;
	private Info info;
	
	//查看信息
	public String infoViewUI(){
		//加载分类集合
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		if(info != null){
			info = infoService.findObjectById(info.getInfoId());
			
		}
		
		return "infoViewUI";
	}
	//查看投诉
	public String complainViewUI(){
		//加载分类集合
		ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		if(comp != null){
			comp = complainService.findObjectById(comp.getCompId());
			
		}
		
		return "complainViewUI";
	}
	
	//保存投诉
	public void complainAdd(){
		try {
			if(comp !=null){
				//设置默认投诉状态为匿名
				comp.setState(Complain.COMPLAIN_STATE_UNDONE);
				comp.setCompTime(new Timestamp(new Date().getTime()));
				complainService.save(comp);
				
				//输出投诉结果
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write("success".getBytes("utf-8"));
				outputStream.close();
				
			}
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	
	//跳转到首页
	public String execute(){
		//加载信息列表
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		QueryHelper queryHelper1 = new QueryHelper(Info.class, "i");
		queryHelper1.addOrderByProperty("i.createTime", QueryHelper.ORDER_BY_DESC);
		List<Info> infoList = infoService.getPageResult(queryHelper1, 1, 5).getItems();
		ActionContext.getContext().getContextMap().put("infoList", infoList);
		
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute(Constant.USER);
		
		//加载我的投诉列表
		ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		QueryHelper queryHelper2 = new QueryHelper(Complain.class, "c");
		queryHelper2.addCondition("c.compName = ?", user.getName());
		//时间升序排
		queryHelper2.addOrderByProperty("c.compTime", QueryHelper.ORDER_BY_ASC);
		//状态降序排
		queryHelper2.addOrderByProperty("c.state", QueryHelper.ORDER_BY_DESC);
		
		List<Complain> complainList = complainService.getPageResult(queryHelper2, 1, 5).getItems();
		ActionContext.getContext().getContextMap().put("complainList", complainList);
		return "home";
	}
	
	//跳转到我要投诉
	public String complainAddUI(){
		
		return "complainAddUI";
	}
	
	public void getUserJson(){
		//获取部门
		String dept = ServletActionContext.getRequest().getParameter("dept");
		if(StringUtils.isNotBlank(dept)){
			QueryHelper queryHelper = new QueryHelper(User.class, "u");
			queryHelper.addCondition("u.dept = ?","%"+ dept);
			//根据部门查询用户列表
			List<User> userList = userService.findObjects(queryHelper);
			
			//创建Json对象
			JSONObject jso = new JSONObject();
			jso.put("msg", "success");
			jso.accumulate("userList", userList);
			
			try {
				//输出更新结果
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(jso.toString().getBytes("utf-8"));
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public String getUserJson2(){
		//获取部门
		String dept = ServletActionContext.getRequest().getParameter("dept");
		if(StringUtils.isNotBlank(dept)){
			QueryHelper queryHelper = new QueryHelper(User.class, "u");
			queryHelper.addCondition("u.dept like ?","%"+ dept);
			//2、根据部门查询用户列表
			return_map = new HashMap<String, Object>();
			return_map.put("msg", "success");
			return_map.put("userList", userService.findObjects(queryHelper));
		}
		return SUCCESS;
	}

	public Map<String, Object> getReturn_map() {
		return return_map;
	}

	public void setReturn_map(Map<String, Object> return_map) {
		this.return_map = return_map;
	}


	public Complain getComp() {
		return comp;
	}


	public void setComp(Complain comp) {
		this.comp = comp;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}
}
