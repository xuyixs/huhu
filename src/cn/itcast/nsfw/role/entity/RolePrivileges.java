package cn.itcast.nsfw.role.entity;

import java.io.Serializable;

public class RolePrivileges implements Serializable {

		private RolePrivilegesId id;
		
		public RolePrivileges(RolePrivilegesId id) {
			super();
			this.id = id;
		}

		public RolePrivileges() {
			super();
		}

		public RolePrivilegesId getId() {
			return id;
		}

		public void setId(RolePrivilegesId id) {
			this.id = id;
		}
		
}
