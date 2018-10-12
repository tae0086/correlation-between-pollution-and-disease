package univ.ust.tae.utils;

import java.util.stream.IntStream;

public class ArrayUtils {

	public static String[] replace(String[] arr, String oldChar, String newChar) {
		return IntStream.range(0, arr.length).mapToObj(i -> arr[i]).map(s -> s.replace(oldChar, newChar))
				.toArray(String[]::new);
	}

	public static int[] toIntegerArray(String[] arr) {
		return IntStream.range(0, arr.length).mapToObj(i -> arr[i]).mapToInt(Integer::parseInt).toArray();
	}

	public static String[] subArray(String[] arr, int startIndex, int endIndex) {
		return IntStream.range(0, arr.length).skip(startIndex).mapToObj(i -> arr[i]).limit(endIndex - startIndex)
				.toArray(String[]::new);
	}

}