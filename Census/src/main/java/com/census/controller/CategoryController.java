package com.census.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.census.entities.Category;
import com.census.entities.Pair;
import com.census.forms.formatters.TableFormatter;
import com.census.reporters.AbstractReporter;
import com.census.services.CategoryService;
import com.census.services.CustomSettingService;

@Controller
public class CategoryController {

	private final CategoryService service;
	private final CustomSettingService customSettingService;
	private final UserConroller userController;
	private final AbstractReporter<Category> reporter;
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class.getName());

	public CategoryController(CategoryService service, CustomSettingService customSettingService,
			UserConroller userController, AbstractReporter<Category> reporter) {
		this.customSettingService = customSettingService;
		this.userController = userController;
		this.reporter = reporter;
		this.service = service;
	}

	@RequestMapping(value = "/categories/save", method = RequestMethod.POST)
	public String saveCategory(Model model, @Valid Category category, BindingResult bindingResult) {
		logger.info("Html page= /categories/save");
		if (bindingResult.hasErrors()) {
			logger.warn("Got errors {}", bindingResult.getAllErrors());
			return "editCategory";
		}
		try {
			service.save(category);
			return "redirect:/categories";
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getLocalizedMessage());
			model.addAttribute("category", category);
			return "editCategory";
		}

	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public String getAllCategories(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "namePart", required = false) String namePart,
			@RequestParam(value = "report", required = false) String report) {
		logger.info("Html page= /categories");
		List<Category> list = new ArrayList<Category>();
		int limitOnPage;
		try {
			limitOnPage = customSettingService.findForUser(userController.getCurrentUser()).stream()
					.filter(s -> s.getGlobalSetting().getName().equals("Limit on page")).findFirst().get().getValue();
		} catch (Exception e) {
			limitOnPage = 10;
		}
		if (namePart != null)
			list = service.findByPartOfName(namePart);
		else
			list = service.findAll();
		TableFormatter<Category> formatter = new TableFormatter<Category>();
		Pair<Integer, List<Category>> lengthAndCurrentList = formatter.getSublist(list, limitOnPage, page);
		model.addAttribute("namePart", namePart);
		model.addAttribute("categories", lengthAndCurrentList.list);
		model.addAttribute("pages", lengthAndCurrentList.size);
		model.addAttribute("currentPage", page);
		if (report != null) {
			File file;
			if (namePart != null)
				file = reporter.makeReport(lengthAndCurrentList.list);
			else
				file = reporter.makeReport(service.findAll());
			return "redirect:/report?filename=" + file.toPath();
		}
		return "categories";
	}

	@RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
	public String findCetegory(Model model, @PathVariable(name = "id") int id) {
		logger.info("Html page= /categories/{}", id);
		Optional<Category> neededCategory = service.find(id);
		if (neededCategory.isPresent()) {
			model.addAttribute("category", neededCategory);
		} else
			return "redirect:/categories";
		return "editCategory";
	}

	@RequestMapping(value = "/categories/create", method = RequestMethod.GET)
	public String createCategory(Model model) {
		logger.info("Html page= /categories/create");
		Category newCategory = new Category();
		model.addAttribute("category", newCategory);
		return "editCategory";
	}

	@RequestMapping(value = "/categories/delete/{id}", method = RequestMethod.GET)
	public String deleteCategory(Model model, @PathVariable(name = "id") int id) {
		logger.info("Html page= /categories/delete/{}", id);
		service.delete(id);
		return "redirect:/categories";
	}

}
