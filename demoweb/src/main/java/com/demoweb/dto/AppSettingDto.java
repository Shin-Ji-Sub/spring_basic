package com.demoweb.dto;

public class AppSettingDto {

	private String settingName;
	private String settingValue;
	
	
	public AppSettingDto() {
		// TODO Auto-generated constructor stub
	}

	public AppSettingDto(String settingName, String settingValue) {
		this.settingName = settingName;
		this.settingValue = settingValue;
	}
	
	public String getSettingName() {
		return settingName;
	}
	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}
	public String getSettingValue() {
		return settingValue;
	}
	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}
	
	
}
