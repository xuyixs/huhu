package cn.itcast.nsfw.role.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.constant.Constant;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.entity.RolePrivileges;
import cn.itcast.nsfw.role.entity.RolePrivilegesId;
import cn.itcast.nsfw.role.service.RoleService;

public class RoleAction extends BaseAction {
	
	@Resource
	private RoleService roleService;
	private Role role;
	private String[] privilegesIds;
	private String strName;
	
	//保存新增
	public String add(){
		try {
			if(role != null){
				//处理权限保存
				if(privilegesIds != null){
					HashSet<RolePrivileges> set = new HashSet<RolePrivileges>();
					for(int i = 0; i < privilegesIds.length; i++){
						set.add(new RolePrivileges(new RolePrivilegesId(role, privilegesIds[i])));
					}
					role.setRolePrivileges(set);
				}
				roleService.save(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
		//列表页面
		public String listUI(){
			ActionContext.getContext().getContextMap().put("privilegesMap", Constant.PRIVILEGE_MAP);
			QueryHelper queryHelper = new QueryHelper(Role.class, "i");
			
			try {
				if(role != null){
					if(StringUtils.isNoneBlank(role.getName())){
						role.setName(URLDecoder.decode(role.getName(), "utf-8"));
						queryHelper.addCondition("i.name like ?", "%" + role.getName() + "%");
					}
				}
				pageResult = roleService.getPageResult(queryHelper,getPageNo(),getPageSize());
			} catch (UnsupportedEncodingException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			return "listUI";
		}
		
		//跳转到新增页面
		public String addUI(){
			//加载权限集合
			ActionContext.getContext().getContextMap().put("privilegesMap", Constant.PRIVILEGE_MAP);
			return "addUI";
		}
		
		
		//跳转到编辑页面
		public String editUI(){
			ActionContext.getContext().getContextMap().put("privilegesMap", Constant.PRIVILEGE_MAP);
			
			
			if(role != null && role.getRoleId() != null){
				strName = role.getName();
				role = roleService.findObjectById(role.getRoleId());
				
				//处理权限回显
				if(role.getRolePrivileges() != null){
					privilegesIds = new String[role.getRolePrivileges().size()];
					int i = 0;
					for (RolePrivileges rp : role.getRolePrivileges()) {
						privilegesIds[i++] = rp.getId().getCode();
					}
				}
			}
			
			return "editUI";
		}
		
		//保存编辑
		public String edit(){
			try {
				if(role != null){
					//处理权限保存
					if(privilegesIds != null){
						HashSet<RolePrivileges> set = new HashSet<RolePrivileges>();
						for(int i = 0; i < privilegesIds.length; i++){
							set.add(new RolePrivileges(new RolePrivilegesId(role, privilegesIds[i])));
						}
						role.setRolePrivileges(set);
					}
					roleService.update(role);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "list";
		}
		
		//删除
		public String delete(){
			if(role != null){
				roleService.delete(role.getRoleId());
			}
			return "list";
		}
		
		//批量删除
		public String deleteSelected(){
			if(selectedRow != null){
				for (String id : selectedRow) {
					roleService.delete(id);
				}
			}
			return "list";
		}

		public String[] getSelectedRow() {
			return selectedRow;
		}

		public void setSelectedRow(String[] selectedRow) {
			this.selectedRow = selectedRow;
		}

		public String[] getPrivilegesIds() {
			return privilegesIds;
		}

		public void setPrivilegesIds(String[] privilegesIds) {
			this.privilegesIds = privilegesIds;
		}

		public Role getRole() {
			return role;
		}

		public void setRole(Role role) {
			this.role = role;
		}

		public String getStrName() {
			return strName;
		}

		public void setStrName(String strName) {
			this.strName = strName;
		}

}
