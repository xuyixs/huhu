package cn.itcast.nsfw.user.service;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.itcast.core.service.BaseService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public interface UserService extends BaseService<User>{
	

	public void exportExcel(List<User> userList, ServletOutputStream outputStream);

	public void importExcel(File userExcel, String userExcelFileName);

	public List<User> findUserByAccountAndId(String id, String account);
	
	//保存用户及其对应的角色
	public void saveUserAndRole(User user, String... roleIds);
	//保存用户及其对应的角色
	public void updateUserAndRole(User user, String... roleIds);
	//根据用户ID获取对应的所有用户角色
	public List<UserRole> getUserRolesByUserId(String id);

	public List<User> findUserByAccountAndPass(String account, String password);
}
