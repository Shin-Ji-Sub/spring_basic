package com.demoweb.dao;

import com.demoweb.dto.AppSettingDto;

public interface AppSettingsDao {

	AppSettingDto selectAppSetting(String settingName);

	void updateSettingName(AppSettingDto appSetting);

}