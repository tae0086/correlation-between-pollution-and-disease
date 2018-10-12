package univ.ust.tae.core;

import univ.ust.tae.data.AirPollution;

public class Main {

	public static void main(String[] args) {
//		MainHandler.getPollutionData();
//		MainHandler.getDiseaseData("성홍열");
		double coefficient = MainHandler.calculatePearsonCorrelation(AirPollution::getPm10, "풍진");
		System.out.println("PM10과 풍진의 상관계수: " + coefficient);
	}

}
