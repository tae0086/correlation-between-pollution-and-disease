package univ.ust.tae.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class XMLParser {

	private Document doc;
	private String xml;

	public XMLParser(String xml) {
		this.xml = xml;
		this.doc = Jsoup.parse(xml);
	}

	public String getXML() {
		return this.xml;
	}

	public Elements getElements(String name) {
		return this.doc.select(name);
	}

}
