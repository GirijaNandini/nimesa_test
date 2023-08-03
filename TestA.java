package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TestA {
	private static final String countries = "London, us";
	private static final String url = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=%S&appid=b6907d289e10d714a6e88b30761fae22"
			.formatted(countries);

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> result = restTemplate.getForEntity(url, Map.class);
		Map<String, List<Map>> body = result.getBody();
		List<Map> ls = body.get("list");
		System.out.println("Enter the Date in yyyy-MM-dd");
		Scanner sc = new Scanner(System.in);
		String userDate = sc.nextLine();
		for (Map all : ls) {
			String jsonDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(Long.parseLong(all.get("dt").toString()) * 1000));
			if (userDate.equals(jsonDate)) {
				System.out.println("matched requested date");
				process(all);
				break;
			}
		}

	}

	private static void process(Map all) {
		Scanner sc = new Scanner(System.in);
		boolean Exit = true;
		while (Exit) {
			System.out.println("Enter The Choice:\n 1. Get weather\n 2.Get Wind Speed\n 3.Get Pressure\n 0.Exit");
			int choice = sc.nextInt();
			switch (choice) {
			case 1:
				Map<String, Double> mainMap = (Map<String, Double>) all.get("main");
				System.out.println("Temperature :" + mainMap.get("temp"));
				break;
			case 2:
				Map<String, Double> mp = (Map<String, Double>) all.get("wind");
				System.out.println("Wind Speed :" + mp.get("speed"));
				break;
			case 3:

				Map<String, Double> m = (Map<String, Double>) all.get("main");
				System.out.println("Pressure  :" + m.get("pressure"));
				break;
			case 0:
				Exit = false;
				break;
			default:
				System.out.println("Invalid Choice!!");
			}
		}
	}

}
