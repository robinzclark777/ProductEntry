package org.rzc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AddProductController {

	private static AtomicInteger ID_GENERATOR = new AtomicInteger(1000);

	@RequestMapping("/addProduct")
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ParseException, FileNotFoundException, org.json.simple.parser.ParseException {
		String title = request.getParameter("title");
		String summary = request.getParameter("summary");
		String theDate = request.getParameter("collectionDate");
		String targetId = request.getParameter("targetId");
		String keywords = request.getParameter("keywords");
		String url = request.getParameter("url");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		ClassLoader classloader = getClass().getClassLoader();
		URL resource = classloader.getResource("MASINT-product-template.json");
		File template;
		if (resource == null) {
			throw new IllegalArgumentException("file is not found!");
		} else {
			template = new File(resource.getFile());
		}

		JSONParser jsonParser = new JSONParser();

		int productId = ID_GENERATOR.getAndIncrement();
		JSONObject json = null;

		try (FileReader reader = new FileReader(template)) {
			// Read JSON file
			json = (JSONObject) jsonParser.parse(reader);
			JSONObject envelope = (JSONObject) json.get("envelope");
			JSONObject instance = (JSONObject) envelope.get("instance");
			JSONObject mpproduct = (JSONObject) instance.get("MPProduct");

			mpproduct.put("collDate", theDate);
			mpproduct.put("targetId", targetId);
			mpproduct.put("keyword", keywords);
			mpproduct.put("summary", summary);
			mpproduct.put("title", title);
			mpproduct.put("url", url);
			mpproduct.put("productId", productId);
			
			
			JSONObject locations = (JSONObject) mpproduct.get("locations");
			JSONArray point = (JSONArray) locations.get("Point");
			point.clear();
			JSONObject pointObj = new JSONObject();
			pointObj.put("latitude", latitude);
			pointObj.put("longitude", longitude);
			point.add(0, pointObj);   

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String outFile = "/" + productId + ".json";
		System.out.println("output file is named..." + outFile);
		
		try  {
			String contextPath = request.getContextPath();
			System.out.println("contextPath is..." + contextPath);
			
			File file = new File(productId + ".json");
			file.setWritable(true);
			file.setReadable(true);
			FileWriter fw = new FileWriter(file);
			System.out.println("absolutePath..." + file.getAbsolutePath());
			System.out.println("canonicalPath..." + file.getCanonicalPath());
			System.out.println("Path..." + file.getPath());
			fw.write(json.toJSONString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return "index.jsp";
	}

}
