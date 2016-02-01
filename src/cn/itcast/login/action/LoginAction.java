package cn.itcast.login.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.itcast.core.constant.Constant;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;

public class LoginAction extends ActionSupport {
	
	@Resource
	private UserService userService;
	private String loginResult;
	private User user;
	
	public String toLoginUI(){
		return "loginUI";
	}
	
	//登录
	public String login(){
		if(user != null){
			if(StringUtils.isNoneBlank(user.getAccount()) && StringUtils.isNotBlank(user.getPassword())){
				//根据用户的帐号和密码查询用户列表
				List<User> list = userService.findUserByAccountAndPass(user.getAccount(),user.getPassword());
				if(list != null && list.size() > 0){
					//登录成功
					User user = list.get(0);
					//2.1.1、根据用户id查询该用户的所有角色
					user.setUserRoles(userService.getUserRolesByUserId(user.getId()));
					//保存到session
					ServletActionContext.getRequest().getSession().setAttribute(Constant.USER, user);
					//将
					Log log = LogFactory.getLog(getClass());
					log.info("用户名称为："+user.getName()+" 的用户登录了系统。");
					//重定向跳转到首页
					return "home";
					
				}else{
					setLoginResult("帐号或密码错误");
				}
			}else{
				setLoginResult("请输入帐号和密码");
			}
		}else{
			setLoginResult("请输入帐号和密码");
		}
		return toLoginUI();
	}
	
	//退出、注销
	public String logout(){
		ServletActionContext.getRequest().getSession().removeAttribute(Constant.USER);
		return toLoginUI();
	}
	
	//跳转到没有权限提示页面
	public String toNoPermissionUI(){
		return "noPermissionUI";
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLoginResult() {
		return loginResult;
	}

	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}
	
	
	
	
}
