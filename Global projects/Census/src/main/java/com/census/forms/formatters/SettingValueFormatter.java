package com.census.forms.formatters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.census.entities.SettingValue;
import com.census.forms.SettingValueList;
import com.census.services.GlobalSettingService;
import com.census.services.UserService;

@Component
public class SettingValueFormatter {
	private UserService userService;
	private GlobalSettingService globalSettingService;

	public SettingValueFormatter(UserService userService, GlobalSettingService globalSettingService) {
		this.userService = userService;
		this.globalSettingService = globalSettingService;
	}

	public List<SettingValue> formatToCustomSetting(SettingValueList form) {
		List<SettingValue> resultList = new ArrayList<SettingValue>();
		form.getList().forEach(s -> {
			resultList.add(new SettingValue(s.getId(), globalSettingService.find(s.getGlobalSettingId()).get(),
					userService.find(s.getUserId()).get(), s.getValue()));
		});
		return resultList;
	}

}
