import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class App {

	private static String matchResult;
	private static String next_matchResult;
	private static String[] dictionary = { "Hong Kong Disneyland",
			"Hong Kong Museum of Art", "Hong Kong", "Hong Hum Station",
			"Philippines", "El Nido", "Palawan", "Boracay", "White Beach",
			"Bohol", "Bantayan Island", "Filipino Island" };

	public static void main(String[] args) {

		String[] tokens = "White Beach is with Boracay the the Philippines".split(" ");
		List<String> NEList = new ArrayList<String>();

		int threshold = 1;
		long start_time = System.currentTimeMillis();

		int pointer = 0;
		while (pointer < tokens.length) {

			String query = tokens[pointer];
			int distance = findDistanceToMostSimilarStr(query);
			int i = 1;
			int next_distance = 0;
			boolean shouldTakeInNextToken = true;
			matchResult = next_matchResult;

			while (pointer + i < tokens.length && shouldTakeInNextToken) {

				String next_query = query + " " + tokens[pointer + i];
				next_distance = findDistanceToMostSimilarStr(next_query);

				if (next_distance > distance || pointer + i >= tokens.length)
					shouldTakeInNextToken = false;

				else{
					// Accept current token
					distance = next_distance;
					query = next_query;
					matchResult = next_matchResult;
					// Continue take in next token
					i++;
				}
				
			}

			if (distance <= threshold) {
				// Return previous token
				NEList.add(matchResult);
				pointer += i;
			} else
				pointer++;

		}

		System.out.println("Process time: "
				+ (System.currentTimeMillis() - start_time));
		System.out.println(NEList.toString());

	}

	private static int findDistanceToMostSimilarStr(String query) {

		int distance = 500;

		for (String candidate : findCandidateResultSet(query)) {

			int next_distance = StringUtils.getLevenshteinDistance(query,
					candidate);

			if (next_distance < distance) {
				distance = next_distance;
				next_matchResult = candidate;
			}

		}

		return distance;

	}

	private static String[] findCandidateResultSet(String query) {

		return dictionary;

	}
}
