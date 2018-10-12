package univ.ust.tae.collector;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import univ.ust.tae.utils.ArrayUtils;

public class DiseaseDataCollector {

	public static List<String[]> getDiseaseData(String path, String enc) {
		List<String[]> data = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), enc))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				values = ArrayUtils.replace(values, "-", "0");
				data.add(values);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

}
