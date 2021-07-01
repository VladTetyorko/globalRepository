package com.census.controller;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

	public MainController() {

	}

	@RequestMapping(value = { "/index", "/" })
	public String index(Model model) {
		return "redirect:me";
	}

	@RequestMapping(value = "/report")
	public void doGet(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "filename") String filePath) {
		String name = filePath.split("/")[2];
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
		File file = new File(filePath);
		try {
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(Files.readAllBytes(file.toPath()));
			outputStream.flush();
			outputStream.close();
			file.delete();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
