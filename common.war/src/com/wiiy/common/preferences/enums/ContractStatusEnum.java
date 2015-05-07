package com.wiiy.common.preferences.enums;

public enum ContractStatusEnum {
		SIGN("签约"),NONEXECUTION("未执行"),START("开始"),RUNNING("执行"),TERMINATION("终止"),ABANDON("废弃"),END("结束");
		
		private String title;

		ContractStatusEnum(String title) {
			this.title = title;
		}

		public String getTitle() {
			return title;
		}
}
