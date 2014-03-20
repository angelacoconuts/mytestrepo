import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class test2 {

	static Logger logger = Logger.getLogger(mainApp.class.getName());
	static MaxentTagger tagger = new MaxentTagger(
			"tagger/english-left3words-distsim.tagger");

	public static void main(String[] args) {

		File input = new File(
				"data/Paris travel guide - Wikitravel.htm");
		String text = "";

		try {

			long startTime = System.currentTimeMillis();

			Document doc = Jsoup.parse(input, "UTF-8");

			test2.logger.info("Parse time: "
					+ (System.currentTimeMillis() - startTime));

			Elements hTags = doc.select("title, h1, h2, h3, h4, h5, h6");

			// Elements pTags = doc.select("p, a[href]");
			// Elements pTags = doc.select("p, li, h1, h2, h3, h4, h5, h6");
			Elements pTags = doc.select("p, h1, h2, h3, h4, h5, h6");

			text = doc.select("title").text();
			
			text += ", " + doc.select("b").text();
			
			text += ", " + doc.select("meta[name=keywords]").attr("content");
			
			/*
			Elements anchors = doc.select("a[href]");
			
			for (Element link : anchors)
				text += ", " + link.text(); */

			test2.logger.info("Get text time: "
					+ (System.currentTimeMillis() - startTime));

			test2.logger.info("Text: "+text);
			test2.logger.info("Desp: "+doc.select("meta[name=keywords]").attr("description"));
	//		test2.logger.info("header:" + hTags.text());

	//		standfordNLP(text);
			
			standfordNLP(pTags.text());

			test2.logger.info("POS tag time: "
					+ (System.currentTimeMillis() - startTime));

			test2.logger.info("Pre filter token size: " + text.length());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void standfordNLP(String testStr) {

		int position = 0;
		int sum = 0;
		String[] NounTags = { "NN", "NNP", "NNPS", "NNS" };
		String[] NounPropTags = { "DT", "FW", "IN", "JJ", "NN", "NNP", "NNPS",
				"NNS" };
		// String[] VerbTags = { "VB", "VBD", "VBG", "VBN", "VBP" };
		String[] AdjTags = { "JJ", "JJR", "JJS" };

		long startTime = System.currentTimeMillis();

		List<List<HasWord>> sentences = MaxentTagger
				.tokenizeText(new StringReader(testStr));
		
		test2.logger.info("Number of sentences: "
				+ sentences.size());

		// test2.logger.info("Tokenize time: "+(System.currentTimeMillis() -
		// startTime));

		for (List<HasWord> sentence : sentences) {

			// test2.logger.info("Sentence " + (i++) + " : " +
			// Sentence.listToString(sentence, false));

			// startTime = System.currentTimeMillis();

			ArrayList<TaggedWord> taggedSent = tagger.tagSentence(sentence);
			List<String> tokens = new ArrayList<String>();
			int j = 0;

			// test2.logger.info("Tag sentence time: "+(System.currentTimeMillis()
			// - startTime));

		//	test2.logger.info(Sentence.listToString(taggedSent, false));

			while (j < taggedSent.size()) {

				if (Arrays.binarySearch(NounTags, taggedSent.get(j).tag()) >= 0) {

					tokens.clear();
					tokens.add(taggedSent.get(j++).word());

					while (j < taggedSent.size()
							&& Arrays.binarySearch(NounPropTags, taggedSent
									.get(j).tag()) >= 0)
						tokens.add(taggedSent.get(j++).word());

		//			test2.logger.info("Noun: " + tokens.toString());

					sum += tokens.size();
					position++;

				} else if (Arrays
						.binarySearch(AdjTags, taggedSent.get(j).tag()) >= 0) {
					// taggedSent.get(j++).word();
					test2.logger.info("Adj: " + taggedSent.get(j++).word());

				} else if (taggedSent.get(j).tag().equals("CD")) {

					tokens.clear();
					tokens.add(taggedSent.get(j++).word());

					if (j < taggedSent.size()
							&& Arrays.binarySearch(NounTags, taggedSent.get(j)
									.tag()) >= 0)
						tokens.add(taggedSent.get(j++).word());
					
					test2.logger.info("Number: " + tokens.toString());
					
				} else
					j++;

				/*
				 * tokens.clear(); while (j<taggedSent.size() &&
				 * Arrays.binarySearch(VerbTags, taggedSent.get(j).tag())>0)
				 * tokens.add(taggedSent.get(j++).word()); if(tokens.size()>0)
				 * test2.logger.info("Verb: "+tokens.toString());
				 */

				/*
				 * tokens.clear(); while (j<taggedSent.size() &&
				 * Arrays.binarySearch(AdjTags, taggedSent.get(j).tag())>=0)
				 * tokens.add(taggedSent.get(j++).word()); if(tokens.size()>0)
				 * test2.logger.info("Adj: "+tokens.toString()); else j++;
				 */

			}

			// test2.logger.info("Parse tags time: "+(System.currentTimeMillis()
			// - startTime));
		}

		test2.logger.info("Total POS tag time: "
				+ (System.currentTimeMillis() - startTime));

		test2.logger.info("Total tag produced: " + sum);

		test2.logger.info("Total number of noun phrase: " + position);

	}

}
