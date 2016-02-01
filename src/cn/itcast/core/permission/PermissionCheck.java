package cn.itcast.core.permission;

import cn.itcast.nsfw.user.entity.User;

public interface PermissionCheck {
	
	public boolean isAccessible(User user,String code);
}
