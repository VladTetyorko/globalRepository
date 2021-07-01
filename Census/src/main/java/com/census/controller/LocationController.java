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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.census.entities.Location;
import com.census.entities.Pair;
import com.census.forms.formatters.TableFormatter;
import com.census.reporters.AbstractReporter;
import com.census.services.CustomSettingService;
import com.census.services.LocationService;

@Controller
public class LocationController {

	private final LocationService service;
	private final CustomSettingService customSettingService;
	private final UserConroller userController;
	private final AbstractReporter<Location> reporter;
	private static final Logger logger = LoggerFactory.getLogger(LocationController.class.getName());

	public LocationController(LocationService service, CustomSettingService customSettingService,
			UserConroller userController, AbstractReporter<Location> reporter) {
		this.customSettingService = customSettingService;
		this.userController = userController;
		this.reporter = reporter;
		this.service = service;
	}

	@RequestMapping(value = "/locations/save", method = RequestMethod.POST)
	public String saveCategory(Model model, @Valid @ModelAttribute(name = "location") Location location,
			BindingResult bindingResult) {
		logger.info("Html page= /locations/save");
		if (bindingResult.hasErrors()) {
			logger.warn("Got errors {}", bindingResult.getAllErrors());
			return "editLocation";
		}
		try {
			service.save(location);
			return "redirect:/locations";
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getLocalizedMessage());
			model.addAttribute("location", location);
			return "editLocation";
		}
	}

	@RequestMapping(value = "/locations", method = RequestMethod.GET)
	public String findLocations(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "namePart", required = false) String namePart,
			@RequestParam(value = "report", required = false) String report) {
		logger.info("Html page= /locations");
		List<Location> list = new ArrayList<Location>();
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
		TableFormatter<Location> formatter = new TableFormatter<Location>();
		Pair<Integer, List<Location>> lengthAndCurrentList = formatter.getSublist(list, limitOnPage, page);
		model.addAttribute("namePart", namePart);
		model.addAttribute("locations", lengthAndCurrentList.list);
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
		return "locations";
	}

	@RequestMapping(value = "/locations/{id}", method = RequestMethod.GET)
	public String findLocation(Model model, @PathVariable(name = "id") int id) {
		logger.info("Html page= /locations/{}", id);
		Optional<Location> neededLocation = service.find(id);
		if (neededLocation.isPresent()) {
			model.addAttribute("location", neededLocation);
		} else
			return "redirect:/locations";
		return "editLocation";
	}

	@RequestMapping(value = "/locations/create", method = RequestMethod.GET)
	public String createLocation(Model model) {
		logger.info("Html page= /locations/create");
		Location newLocation = new Location();
		model.addAttribute("location", newLocation);
		return "editLocation";
	}

	@RequestMapping(value = "/locations/delete/{id}", method = RequestMethod.GET)
	public String deleteLocation(Model model, @PathVariable(name = "id") int id) {
		logger.info("Html page= /locations/delete/{}", id);
		service.delete(id);
		return "redirect:/locations";
	}

}
