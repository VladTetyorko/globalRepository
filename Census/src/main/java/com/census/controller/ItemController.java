package com.census.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.census.entities.Category;
import com.census.entities.Item;
import com.census.entities.Location;
import com.census.entities.Pair;
import com.census.entities.SettingValue;
import com.census.entities.User;
import com.census.exceptions.SQLUnexpectedException;
import com.census.forms.ItemForm;
import com.census.forms.formatters.ItemFormFormatter;
import com.census.forms.formatters.TableFormatter;
import com.census.reporters.AbstractReporter;
import com.census.services.CategoryService;
import com.census.services.ItemService;
import com.census.services.LocationService;
import com.census.services.UserService;

@Controller
public class ItemController {

	private final UserService userService;
	private final LocationService locationService;
	private final ItemService itemService;
	private final CategoryService categoryService;
	private final ItemFormFormatter formatter;
	private final AbstractReporter<Item> reporter;
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class.getName());

	public ItemController(UserService userService, LocationService locationService, ItemService itemService,
			CategoryService categoryService, ItemFormFormatter formatter, AbstractReporter<Item> reporter) {
		this.categoryService = categoryService;
		this.locationService = locationService;
		this.userService = userService;
		this.itemService = itemService;
		this.formatter = formatter;
		this.reporter = reporter;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getAllItems(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "namePart", required = false) String namePart,
			@RequestParam(value = "byDesc", required = false) String byDesk,
			@RequestParam(value = "report", required = false) String report) {
		logger.info("Html page= /");
		List<Item> list = new ArrayList<Item>();
		User user = getCurrentUser();
		if (namePart == null)
			list = itemService.findAll();
		else if (byDesk == null) {
			list = itemService.findByPartOfName(namePart);
		} else
			list = itemService.findByPartOfNameAndDesc(namePart);
		List<SettingValue> settings = user.getSettings();
		int limitOnPage = getParameterOfPage(settings);
		TableFormatter<Item> formatter = new TableFormatter<Item>();
		Pair<Integer, List<Item>> lengthAndCurrentList = formatter.getSublist(list, limitOnPage, page);
		model.addAttribute("namePart", namePart);
		model.addAttribute("items", lengthAndCurrentList.list);
		model.addAttribute("pages", lengthAndCurrentList.size);
		model.addAttribute("currentPage", page);
		if (report != null) {
			File file;
			if (namePart != null)
				file = reporter.makeReport(lengthAndCurrentList.list);
			else
				file = reporter.makeReport(list);
			return "redirect:/report?filename=" + file.toPath();
		}
		return "items";
	}

	@RequestMapping(value = "/items/my", method = RequestMethod.GET)
	public String getItemsForUser(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "namePart", required = false) String namePart,
			@RequestParam(value = "byDesc", required = false) String byDesk,
			@RequestParam(value = "report", required = false) String report) {
		logger.info("Html page= /items/my");
		List<Item> list = new ArrayList<Item>();
		User user = getCurrentUser();
		if (namePart == null)
			list = itemService.findAll();
		else if (byDesk == null) {
			list = itemService.findByPartOfNameForUser(user, namePart);
		} else
			list = itemService.findByPartOfNameAndDescForUser(namePart, user);
		List<SettingValue> settings = user.getSettings();
		int limitOnPage = getParameterOfPage(settings);
		TableFormatter<Item> formatter = new TableFormatter<Item>();
		Pair<Integer, List<Item>> lengthAndCurrentList = formatter.getSublist(list, limitOnPage, page);
		Locale locale = LocaleContextHolder.getLocale();
		String language = locale.getLanguage();
		model.addAttribute("currentLanguage", language);
		model.addAttribute("namePart", namePart);
		model.addAttribute("items", lengthAndCurrentList.list);
		model.addAttribute("pages", lengthAndCurrentList.size);
		model.addAttribute("currentPage", page);
		if (report != null) {
			File file;
			if (namePart != null)
				file = reporter.makeReport(lengthAndCurrentList.list);
			else
				file = reporter.makeReport(itemService.findForUser(user));
			return "redirect:/report?filename=" + file.toPath();
		}
		return "items";
	}

	@RequestMapping(value = "/items/locations/{name}", method = RequestMethod.GET)
	public String getItemsForLocation(Model model, @PathVariable(name = "name") String name,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "namePart", required = false) String namePart,
			@RequestParam(value = "report", required = false) String report) {
		List<Item> list = new ArrayList<Item>();
		logger.info("Html page= /items/locations/{}", name);
		Optional<Location> neededLocation = locationService.findByName(name);
		List<SettingValue> settings = getCurrentUser().getSettings();
		int limitOnPage = getParameterOfPage(settings);
		if (namePart == null)
			list = itemService.findForLocation(neededLocation.get());
		else
			list = itemService.findByPartOfNameInLocation(neededLocation.get(), namePart);
		TableFormatter<Item> formatter = new TableFormatter<Item>();
		Pair<Integer, List<Item>> lengthAndCurrentList = formatter.getSublist(list, limitOnPage, page);
		model.addAttribute("namePart", namePart);
		model.addAttribute("items", lengthAndCurrentList.list);
		model.addAttribute("pages", lengthAndCurrentList.size);
		model.addAttribute("currentPage", page);
		if (report != null) {
			File file;
			if (namePart != null)
				file = reporter.makeReport(lengthAndCurrentList.list);
			else
				file = reporter.makeReport(itemService.findForLocation(neededLocation.get()));
			return "redirect:/report?filename=" + file.toPath();
		}
		return "items";
	}

	@RequestMapping(value = "/items/categories/{name}", method = RequestMethod.GET)
	public String getItemsForCategory(Model model, @PathVariable(name = "name") String name,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "namePart", required = false) String namePart,
			@RequestParam(value = "report", required = false) String report) {
		logger.info("Html page= /items/categories/{}", name);
		Optional<Category> neededCategory = categoryService.findByName(name);
		List<Item> list = new ArrayList<Item>();
		List<SettingValue> settings = getCurrentUser().getSettings();
		int limitOnPage = getParameterOfPage(settings);
		if (namePart == null)
			list = itemService.findForCategory(neededCategory.get());
		else
			list = itemService.findByPartOfNameInCategory(neededCategory.get(), namePart);
		TableFormatter<Item> formatter = new TableFormatter<Item>();
		Pair<Integer, List<Item>> lengthAndCurrentList = formatter.getSublist(list, limitOnPage, page);
		model.addAttribute("namePart", namePart);
		model.addAttribute("items", lengthAndCurrentList.list);
		model.addAttribute("pages", lengthAndCurrentList.size);
		model.addAttribute("currentPage", page);
		if (report != null) {
			File file;
			if (namePart != null)
				file = reporter.makeReport(lengthAndCurrentList.list);
			else
				file = reporter.makeReport(itemService.findForCategory(neededCategory.get()));
			return "redirect:/report?filename=" + file.toPath();
		}
		return "items";
	}

	@RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
	public String editItem(Model model, @PathVariable(name = "id") int id,
			@RequestParam(name = "LocationNamePart", required = false) String locationNamePart,
			@RequestParam(name = "CategoryNamePart", required = false) String categoryNamePart) {
		logger.info("Html page= /items/{}", id);
		Locale locale = LocaleContextHolder.getLocale();
		String language = locale.getLanguage();
		Optional<Item> neededItem = itemService.find(id);
		ItemForm form = ItemForm.format(neededItem.get(), language);
		model.addAttribute("currentLang", language);
		if (neededItem.isPresent()) {
			model.addAttribute("item", form);
			if (locationNamePart == null)
				model.addAttribute("locations",
						locationService.findByPartOfName(neededItem.get().getLocation().getName()));
			if (categoryNamePart == null)
				model.addAttribute("categories",
						categoryService.findByPartOfName(neededItem.get().getCategory().getName()));
			model.addAttribute("users", userService.findAll());
			List<SettingValue> settings = getCurrentUser().getSettings();
			if (locationNamePart != null) {
				model.addAttribute("locPart", locationNamePart);
			}
			if (categoryNamePart != null) {
				model.addAttribute("catPart", categoryNamePart);
			}
			addParametersOnList(model, settings, locationNamePart, categoryNamePart);
		} else
			return "redirect:/items/my";
		return "editItem";
	}

	@RequestMapping(value = "/items/add", method = RequestMethod.GET)
	public String addItem(Model model,
			@RequestParam(name = "LocationNamePart", required = false) String locationNamePart,
			@RequestParam(name = "CategoryNamePart", required = false) String categoryNamePart) {
		logger.info("Html page= /items/add");
		User user = getCurrentUser();
		ItemForm form = ItemForm.formatEmpty();
		model.addAttribute("item", form);
		List<SettingValue> settings = user.getSettings();
		addParametersOnList(model, settings, locationNamePart, categoryNamePart);
		model.addAttribute("users", userService.findAll());
		model.addAttribute("locPart", locationNamePart);
		model.addAttribute("catPart", categoryNamePart);
		return "editItem";
	}

	@RequestMapping(value = "/items/categories/{name}/add", method = RequestMethod.GET)
	public String addItemInCategory(Model model, @PathVariable(name = "name") String name,
			@RequestParam(name = "LocationNamePart", required = false) String locationNamePart,
			@RequestParam(name = "CategoryNamePart", required = false) String categoryNamePart) {
		logger.info("Html page= /items/add");
		ItemForm form = ItemForm.formatEmpty();
		Category category = categoryService.findByName(name).get();
		User user = getCurrentUser();
		form.setOwnerId(user.getId());
		form.setCategoryId(category.getId());
		model.addAttribute("item", form);
		List<SettingValue> settings = user.getSettings();
		addParametersOnList(model, settings, locationNamePart, categoryNamePart);
		model.addAttribute("users", userService.findAll());
		model.addAttribute("locPart", locationNamePart);
		model.addAttribute("catPart", categoryNamePart);
		return "editItem";
	}

	@RequestMapping(value = "/items/locations/{name}/add", method = RequestMethod.GET)
	public String addItemInLocation(Model model, @PathVariable(name = "name") String name,
			@RequestParam(name = "LocationNamePart", required = false) String locationNamePart,
			@RequestParam(name = "CategoryNamePart", required = false) String categoryNamePart) {
		logger.info("Html page= /items/add");
		ItemForm form = ItemForm.formatEmpty();
		Location location = locationService.findByName(name).get();
		User user = getCurrentUser();
		form.setOwnerId(user.getId());
		form.setLocationId(location.getId());
		model.addAttribute("item", form);
		List<SettingValue> settings = user.getSettings();
		addParametersOnList(model, settings, locationNamePart, categoryNamePart);
		if (locationNamePart == null)
			model.addAttribute("locations", locationService.findByPartOfName(name));
		model.addAttribute("users", userService.findAll());
		model.addAttribute("locPart", locationNamePart);
		model.addAttribute("catPart", categoryNamePart);
		return "editItem";
	}

	@RequestMapping(value = "/items/save", method = RequestMethod.POST)
	public String saveItem(Model model, @Valid @ModelAttribute("item") ItemForm form, BindingResult bindingResult) {
		logger.info("Html page= /locations/save");
		if (bindingResult.hasErrors()) {
			model.addAttribute("locations", locationService.findAll());
			model.addAttribute("categories", categoryService.findAll());
			model.addAttribute("users", userService.findAll());
			logger.warn("Got errors {}", bindingResult.getAllErrors());
			return "editItem";
		}
		try {
			Locale locale = LocaleContextHolder.getLocale();
			String language = locale.getLanguage();
			Item item = formatter.format(form, language);
			itemService.save(item);
		} catch (SQLUnexpectedException e) {
			model.addAttribute("errorMessage", e.getLocalizedMessage());
			model.addAttribute("locations", locationService.findAll());
			model.addAttribute("categories", categoryService.findAll());
			model.addAttribute("users", userService.findAll());
			logger.warn("Got unexpected errors {}", e.getLocalizedMessage());
			return "editItem";
		}
		return "redirect:/items/my";
	}

	@RequestMapping(value = "/items/delete/{id}", method = RequestMethod.GET)
	public String deleteItem(@PathVariable(name = "id") int id) {
		logger.info("Html page= /items/delete/{}", id);
		itemService.delete(id);
		return "redirect:/items/my";
	}

	private int getParameterOfPage(List<SettingValue> settings) {
		int limitOnPage = 10;
		if (!settings.isEmpty()) {
			Optional<SettingValue> optional = settings.stream()
					.filter(s -> s.getGlobalSetting().getName().equals("Limit on page")).findAny();
			if (optional.isPresent())
				if (optional.get().getValue() != 0)
					limitOnPage = optional.get().getValue();
		}
		return limitOnPage;
	}

	private void addParametersOnList(Model model, List<SettingValue> settings, String locationNamePart,
			String categoryNamePart) {
		int limitOnList = 10;
		if (settings != null)
			if (!settings.isEmpty()) {
				Optional<SettingValue> optional = settings.stream()
						.filter(s -> s.getGlobalSetting().getName().equals("Limit on list")).findAny();
				if (optional.isPresent())
					if (optional.get().getValue() != 0)
						limitOnList = optional.get().getValue();
			}
		if (locationNamePart != null)
			locationFiller(model, locationNamePart, limitOnList);

		if (categoryNamePart != null)
			categoryFiller(model, categoryNamePart, limitOnList);

	}

	private void locationFiller(Model model, String locNamePart, int limit) {
		if (locNamePart.equals("All"))
			model.addAttribute("locations", locationService.findAll());
		else
			model.addAttribute("locations", locationService.searchByNameAndLimitCustomQuerry(locNamePart, limit));
	}

	private void categoryFiller(Model model, String catNamePart, int limit) {
		if (catNamePart.equals("All"))
			model.addAttribute("categories", categoryService.findAll());
		else
			model.addAttribute("categories", categoryService.searchByNameAndLimitCustomQuerry(catNamePart, limit));
	}

	private User getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.findUserByUsername(((UserDetails) principal).getUsername()).get();
		return user;
	}

}
