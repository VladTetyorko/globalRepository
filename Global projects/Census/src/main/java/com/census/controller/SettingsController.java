package com.census.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.census.entities.Setting;
import com.census.entities.SettingValue;
import com.census.entities.User;
import com.census.forms.SettingValueForm;
import com.census.forms.SettingValueList;
import com.census.forms.formatters.SettingValueFormatter;
import com.census.services.CustomSettingService;
import com.census.services.GlobalSettingService;
import com.census.services.UserService;

@Controller
public class SettingsController {

	private CustomSettingService customSettingService;
	private GlobalSettingService globalSettingService;
	private UserService userService;
	private SettingValueFormatter settingFormatter;

	public SettingsController(CustomSettingService settingService, UserService userService,
			GlobalSettingService globalSettingService, SettingValueFormatter settingFormatter) {
		this.userService = userService;
		this.customSettingService = settingService;
		this.globalSettingService = globalSettingService;
		this.settingFormatter = settingFormatter;
	}

	@RequestMapping(value = "/me/settings")
	public String settings(Model model) {
		User user = getCurrentUser();
		Locale locale = LocaleContextHolder.getLocale();
		String language = locale.getLanguage();
		List<SettingValue> listOfSettings = customSettingService.findForUser(user);
		List<Setting> globalSettings = globalSettingService.findAll();
		List<SettingValueForm> listOfSettingForms = new ArrayList<SettingValueForm>();
		System.out.print(globalSettings);
		SettingValueList form = new SettingValueList();
		globalSettings.forEach(s -> {
			Optional<SettingValue> exsistedSetting = listOfSettings.stream().filter(c -> c.getGlobalSetting().equals(s))
					.findAny();
			if (exsistedSetting.isPresent())
				listOfSettingForms.add(SettingValueForm.format(exsistedSetting.get(), language));
			else
				listOfSettingForms.add(SettingValueForm.format(new SettingValue(s, user, 10), language));
		});
		form.setList(listOfSettingForms);
		model.addAttribute("form", form);
		return "settings";
	}

	@RequestMapping(value = "/settings/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = {
			MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public String saveSettings(@ModelAttribute SettingValueList form) {
		List<SettingValue> list = settingFormatter.formatToCustomSetting(form);
		list = customSettingService.save(list);
		return "redirect:/me/settings";
	}

	public User getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUserByUsername(((UserDetails) principal).getUsername()).get();
		return user;
	}

}
