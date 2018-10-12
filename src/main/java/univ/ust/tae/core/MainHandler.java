package univ.ust.tae.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.nodes.Element;

import univ.ust.tae.collector.AirPollutionDataCollector;
import univ.ust.tae.collector.DiseaseDataCollector;
import univ.ust.tae.data.AirPollution;
import univ.ust.tae.data.Disease;
import univ.ust.tae.parser.XMLParser;
import univ.ust.tae.utils.ArrayUtils;
import univ.ust.tae.utils.HibernateUtils;

public class MainHandler {

	public static void getPollutionData() {
		Session session = HibernateUtils.openSession();

		for (YearMonth date = YearMonth.of(2017, Month.JANUARY); date
				.isBefore(YearMonth.of(2018, Month.OCTOBER)); date = date.plusMonths(1)) {
			String xml = AirPollutionDataCollector.getAirPollutionData(date);
			XMLParser parser = new XMLParser(xml);
			System.out.println("* " + date.toString());
			for (Element item : parser.getElements("item")) {
				Transaction tx = null;
				try {
					tx = session.beginTransaction();

					AirPollution pollution = new AirPollution();
					pollution.setDataTime(new SimpleDateFormat("yyyy-MM").parse(item.selectFirst("dataTime").text()));
					pollution.setSidoName(item.selectFirst("sidoName").text());
					pollution.setSo2(Double.parseDouble(item.selectFirst("so2Avg").text()));
					pollution.setCo(Double.parseDouble(item.selectFirst("coAvg").text()));
					pollution.setO3(Double.parseDouble(item.selectFirst("o3Avg").text()));
					pollution.setNo2(Double.parseDouble(item.selectFirst("no2Avg").text()));
					pollution.setPm10(Double.parseDouble(item.selectFirst("pm10Avg").text()));

					session.saveOrUpdate(pollution);
					tx.commit();
				} catch (ParseException e) {
					tx.rollback();
					e.printStackTrace();
				}
			}
		}

		session.close();
	}

	public static void getDiseaseData(String name) {
		Session session = HibernateUtils.openSession();

		for (int year = 2006; year < 2017; year++) {
			Transaction tx = null;
			try {
				List<String[]> data = DiseaseDataCollector.getDiseaseData(name + "/" + year + ".csv", "UTF-8");
				String[] sido = data.stream().map(arr -> arr[0]).toArray(String[]::new);

				tx = session.beginTransaction();

				for (int i = 1; i < data.size(); i++) {
					int[] countPerMonth = ArrayUtils
							.toIntegerArray(ArrayUtils.subArray(data.get(i), 1, data.get(i).length));
					for (int j = 1; j < countPerMonth.length; j++) {
						Disease mers = new Disease();
						mers.setName(name);
						mers.setDate(new SimpleDateFormat("yyyy-MM").parse(year + "-0" + j));
						mers.setSido(sido[i]);
						mers.setCount(countPerMonth[j]);
						System.out.println(mers);
						session.saveOrUpdate(mers);
					}
				}

				tx.commit();
			} catch (ParseException e) {
				tx.rollback();
				e.printStackTrace();
			}

		}

		session.close();
	}

	public static double calculatePearsonCorrelation(ToDoubleFunction<AirPollution> function, String disease) {
		PearsonsCorrelation cor = new PearsonsCorrelation();
		double coefficient = cor.correlation(getAirPollutionTimeSeries(function), getDiseaseTimeSeries(disease));
		return coefficient;
	}

	private static double[] getAirPollutionTimeSeries(ToDoubleFunction<AirPollution> function) {
		// 1. Get data from database
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();

		CriteriaQuery<AirPollution> query = session.getCriteriaBuilder().createQuery(AirPollution.class);
		query.select(query.from(AirPollution.class));
		List<AirPollution> data = session.createQuery(query).getResultList();

		tx.commit();
		session.close();

		// 2. Convert to time series
		Map<Date, List<AirPollution>> groupByDate = data.stream()
				.collect(Collectors.groupingBy(AirPollution::getDataTime));
		double[] series = groupByDate.entrySet().stream().map(Entry::getValue)
				.mapToDouble(list -> list.stream().mapToDouble(function).average().getAsDouble()).toArray();
		return series;
	}

	private static double[] getDiseaseTimeSeries(String name) {
		// 1. Get data from database
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Disease> query = builder.createQuery(Disease.class);
		Root<Disease> root = query.from(Disease.class);
		query.select(root).where(builder.equal(root.get("name"), name));
		List<Disease> data = session.createQuery(query).getResultList();

		tx.commit();
		session.close();
		HibernateUtils.closeSessionFactory();

		// 2. Convert to time series
		Map<Date, List<Disease>> groupBydate = data.stream().collect(Collectors.groupingBy(Disease::getDate));
		int[] series = groupBydate.entrySet().stream().map(Entry::getValue)
				.mapToInt(list -> list.stream().mapToInt(Disease::getCount).sum()).toArray();
		return Arrays.stream(series).mapToDouble(s -> s).toArray();
	}

}
