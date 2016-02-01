package cn.itcast.core.exception;

public class SysException extends Exception {

		private String errorMsg;
		
		

		public SysException(String message, Throwable cause) {
			super(message, cause);
			// TODO 自动生成的构造函数存根
		}

		public SysException(Throwable cause) {
			super(cause);
			// TODO 自动生成的构造函数存根
		}

		public SysException(String message) {
			super(message);
			// TODO 自动生成的构造函数存根
		}

		public SysException() {
			super();
		}

		public String getErrorMsg() {
			return errorMsg;
		}

		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
}
