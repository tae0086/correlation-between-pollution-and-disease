package univ.ust.tae.collector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class AirPollutionDataCollector {

	public static String getAirPollutionData(YearMonth date) {
		StringBuilder builder = null;
		try {
			builder = new StringBuilder(
					"http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnStatsSvc/getDatePollutnStatInfo");
			// 1. 서비스키
			builder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
					+ "=NNiO9KkpsI5PDVMtRhVGYilHaRcbYJrT1HfHCuMVIUzu48GdzC4QEAxkCndcOZRJnG%2FoT%2FyTdtmsnQXj74p6aQ%3D%3D");
			// 2. 한 페이지 결과 수
//			builder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
			// 3. 페이지 번호
//			builder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "="
//					+ URLEncoder.encode(String.valueOf(pageNo), "UTF-8"));
			// 4. 조회 날짜
			builder.append("&" + URLEncoder.encode("searchDataTime", "UTF-8") + "="
					+ URLEncoder.encode(date.format(DateTimeFormatter.ofPattern("yyyy-MM")), "UTF-8"));
			// 5. 측정망 정보 (도시대기)
			builder.append("&" + URLEncoder.encode("statArticleCondition", "UTF-8") + "="
					+ URLEncoder.encode("도시대기", "UTF-8"));

			URL url = new URL(builder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");

			BufferedReader reader = null;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}

			builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			reader.close();
			conn.disconnect();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return builder.toString();
	}

}
