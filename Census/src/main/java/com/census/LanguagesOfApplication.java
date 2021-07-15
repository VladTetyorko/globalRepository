package com.census;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class LanguagesOfApplication {

	private List<String> languages;

	public LanguagesOfApplication() {
		languages = new ArrayList<String>();
		languages.add("en");
		languages.add("ua");
		languages.add("ru");
	}

	public List<String> getLanguageMap() {
		return languages;
	}

}
