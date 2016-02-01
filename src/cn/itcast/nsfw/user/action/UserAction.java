package cn.itcast.nsfw.user.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import cn.itcast.core.action.BaseAction;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.role.service.RoleService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.service.UserService;

public class UserAction extends BaseAction {
	
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	private List<User> userList;
	private User user;
	private String[] selectedRow;
	
	private File headImg;
	private String headImgContentType;
	private String headImgFileName;
	private String[] userRoleIds;
	
	private File userExcel;
	private String userExcelContentType;
	private String userExcelFileName;
	
	private String strName;
	
	//校验用户帐号唯一
	public void verifyAccount() throws IOException{
		//获取帐号
		try {
			if(user != null && StringUtils.isNotBlank(user.getAccount())){
				//检验是否有这个帐号
				List<User> list = userService.findUserByAccountAndId(user.getId(),user.getAccount());
				String strResult = "true";
				if(list != null && list.size() > 0){
					//说明帐号存在
					strResult = "false";
				}
				//输出 
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				
				ServletOutputStream outputStream = response.getOutputStream();
				
				outputStream.write(strResult.getBytes());
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	//导入用户列表
	public String importExcel(){
		//获取Excel文件
		if(userExcel != null){
			if(userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
				//导入
				userService.importExcel(userExcel,userExcelFileName);
				
			}
		}
		
		return "list";
	}
	
	
	//导出用户列表
	public void exportExcel(){
		try {
			//查找用户列表
			userList = userService.findObjects();
			//导出
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/x-execl");
			response.setHeader("Content-Disposition", "attachment;filename="+new String("用户列表.xls".getBytes(),"ISO-8859-1"));
			
			ServletOutputStream outputStream = response.getOutputStream();
			
			userService.exportExcel(userList,outputStream);
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	
		//列表页面
		public String listUI(){
			QueryHelper queryHelper = new QueryHelper(User.class, "u");
			
			try {
				if(user != null){
					if(StringUtils.isNoneBlank(user.getName())){
						user.setName(URLDecoder.decode(user.getName(), "utf-8"));
						queryHelper.addCondition("u.name like ?", "%" + user.getName() + "%");
					}
				}
				pageResult = userService.getPageResult(queryHelper,getPageNo(),getPageSize());
			} catch (UnsupportedEncodingException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			return "listUI";
		}
		
		//跳转到新增页面
		public String addUI(){
			//加载角色列表
			ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
			
			return "addUI";
		}
		
		//保存新增
		public String add(){
			try {
				if(user != null){
					
					//处理头像
					if(headImg != null){
						//保存到upload/user
						String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
						String fileName = UUID.randomUUID().toString().replaceAll("-", "")+headImgFileName.substring(headImgFileName.lastIndexOf("."));
						//复制文件
						FileUtils.copyFile(headImg, new File(filePath,fileName));
						//设置用户头像路径
						user.setHeadImg("user/" + fileName);
					}
					
					userService.saveUserAndRole(user,userRoleIds);
				}
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			return "list";
		}
		
		//跳转到编辑页面
		public String editUI(){
			ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
			//测试参数拦截器是否会自动封装传过来的Id为对象
			if(user != null && user.getId() != null){
				strName = user.getName();
				user = userService.findObjectById(user.getId());
				//处理角色回显
				List<UserRole> list = userService.getUserRolesByUserId(user.getId());
				if(list!= null && list.size() > 0){
					userRoleIds = new String[list.size()];
					for(int i = 0; i < list.size(); i++){
						userRoleIds[i] = list.get(i).getId().getRole().getRoleId();
					}
				}
				
			}
			
			return "editUI";
		}
		
		//保存编辑
		public String edit(){
			try {
				if(user != null){
					
					//处理头像
					if(headImg != null){
						//保存到upload/user
						String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
						String fileName = UUID.randomUUID().toString().replaceAll("-", "")+headImgFileName.substring(headImgFileName.lastIndexOf("."));
						//复制文件
						FileUtils.copyFile(headImg, new File(filePath,fileName));
						//设置用户头像路径
						user.setHeadImg("user/" + fileName);
					}
					
					userService.updateUserAndRole(user,userRoleIds);
				}
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			return "list";
		}
		
		//删除
		public String delete(){
			if(user != null){
				userService.delete(user.getId());
			}
			return "list";
		}
		
		//批量删除
		public String deleteSelected(){
			if(selectedRow != null){
				for (String id : selectedRow) {
					userService.delete(id);
				}
			}
			return "list";
		}
		
		public File getHeadImg() {
		return headImg;
	}

	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}

	public String getHeadImgContentType() {
		return headImgContentType;
	}

	public void setHeadImgContentType(String headImgContentType) {
		this.headImgContentType = headImgContentType;
	}

	public String getHeadImgFileName() {
		return headImgFileName;
	}

	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}

		public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

		public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

		public String[] getSelectedRow() {
			return selectedRow;
		}

		public void setSelectedRow(String[] selectedRow) {
			this.selectedRow = selectedRow;
		}

		public String[] getUserRoleIds() {
			return userRoleIds;
		}

		public void setUserRoleIds(String[] userRoleIds) {
			this.userRoleIds = userRoleIds;
		}
		public File getUserExcel() {
			return userExcel;
		}

		public void setUserExcel(File userExcel) {
			this.userExcel = userExcel;
		}

		public String getUserExcelContentType() {
			return userExcelContentType;
		}

		public void setUserExcelContentType(String userExcelContentType) {
			this.userExcelContentType = userExcelContentType;
		}

		public String getUserExcelFileName() {
			return userExcelFileName;
		}

		public void setUserExcelFileName(String userExcelFileName) {
			this.userExcelFileName = userExcelFileName;
		}

		public String getStrName() {
			return strName;
		}

		public void setStrName(String strName) {
			this.strName = strName;
		}
}
