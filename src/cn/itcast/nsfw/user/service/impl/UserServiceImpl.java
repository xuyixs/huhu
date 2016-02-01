package cn.itcast.nsfw.user.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import cn.itcast.core.service.impl.BaseServiceImpl;
import cn.itcast.core.util.ExcelUtil;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.user.dao.UserDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.entity.UserRoleId;
import cn.itcast.nsfw.user.service.UserService;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	
	private UserDao userDao;
	@Resource 
	public void setUserDao(UserDao userDao) {
		super.setBaseDao(userDao);
		this.userDao = userDao;
	}

	public void deleUser(Serializable id) {
		
		userDao.delete(id);
		//删除用户对应的所有权限
		userDao.deleteUserRoleByUserId(id);
		
	}

	public void exportExcel(List<User> userList, ServletOutputStream outputStream) {
		ExcelUtil.exportUserExcel(userList, outputStream);
		
	}

	public void importExcel(File userExcel, String userExcelFileName) {
		try {
			FileInputStream fileInputStream = new FileInputStream(userExcel);
			
			boolean is03Excel = userExcelFileName.matches("^.+\\.(?i)(xls)$");
			
			Workbook workbook = is03Excel ? new HSSFWorkbook(fileInputStream):new XSSFWorkbook(fileInputStream);
			
			Sheet sheet = workbook.getSheetAt(0);
			
			//读取行
			if(sheet.getPhysicalNumberOfRows() > 2){
				User user = null;
				for(int k =2; k < sheet.getPhysicalNumberOfRows(); k++){
					//读取单元格
					Row row = sheet.getRow(k);
					user = new User();
					//用户名
					Cell cell1 = row.getCell(0);
					user.setName(cell1.getStringCellValue());
					
					//帐号
					Cell cell2 = row.getCell(1);
					user.setAccount(cell2.getStringCellValue());
					
					//所属部门
					Cell cell3 = row.getCell(2);
					user.setDept(cell3.getStringCellValue());
					
					//性别
					Cell cell4 = row.getCell(3);
					user.setGender(cell4.getStringCellValue().equals("男"));
					
					//手机号
					String mobile = "";
					Cell cell5 = row.getCell(4);
					try {
						mobile = cell5.getStringCellValue();
					} catch (Exception e) {
						double dMobile = cell5.getNumericCellValue();
						mobile = BigDecimal.valueOf(dMobile).toString();
					}
					user.setMobile(mobile);
					
					//电子邮箱
					Cell cell6 = row.getCell(5);
					user.setEmail(cell6.getStringCellValue());
					
					//生日
					Cell cell7 = row.getCell(6);
					if(cell7.getDateCellValue() != null){
						user.setBirthday(cell7.getDateCellValue());
					}
					
					user.setPassword("123456");
					user.setState(User.USER_STATE_VSLID);
					
					save(user);
				}
				
			}
			workbook.close();
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	//根据ID查询
	public List<User> findUserByAccountAndId(String id, String account) {
		
		return userDao.findUserByAccountAndId(id, account);
	}

	@Override
	public void saveUserAndRole(User user, String... roleIds) {
		//保存用户
		save(user);
		//保存用户对应的角色
		if(roleIds != null){
			for(String roleId : roleIds){
				userDao.saveUserRole(new UserRole(new UserRoleId(new Role(roleId),user.getId())));
			}
		}
		
	}

	public void updateUserAndRole(User user, String... roleIds) {
		//根据用户删除这个用户的所有角色
		userDao.deleteUserRoleByUserId(user.getId());
		//更新用户
		update(user);
		//保存用户
		if(roleIds != null){
			for(String roleId : roleIds){
				userDao.saveUserRole(new UserRole(new UserRoleId(new Role(roleId),user.getId())));
			}
		}
	}

	@Override
	public List<UserRole> getUserRolesByUserId(String id) {
		// TODO 自动生成的方法存根
		return userDao.getUserRolesByUserId(id);
	}

	@Override
	public List<User> findUserByAccountAndPass(String account, String password) {
		// TODO 自动生成的方法存根
		return userDao.findUsersByAccountAndPass(account,password);
	}
}
