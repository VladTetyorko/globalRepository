package com.census.reporters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;

import com.census.entities.TableEntity;
import com.opencsv.CSVWriter;

@Component
public class AbstractReporter<T> {

	public File makeReport(List<T> list) {
		File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
		LocalDateTime now = LocalDateTime.now();
		File file = new File(tempDirectory.getAbsolutePath() + "/output"
				+ now.format(DateTimeFormatter.ofPattern("dd-mm-yyyy_hh-mm-ss")) + ".csv");
		try {
			file.createNewFile();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		try {
			FileWriter output = new FileWriter(file);
			CSVWriter write = new CSVWriter(output);
			if (!list.isEmpty())
				if (list.get(0) instanceof TableEntity) {
					String header[] = ((TableEntity) list.get(0)).getTableHeader();
					write.writeNext(header);
					list.forEach(entity -> {
						String arr[] = ((TableEntity) entity).toTableRow();
						write.writeNext(arr);
					});
					write.close();
				} else {
					String arr[] = { "No rows availeble" };
					write.writeNext(arr);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

}
