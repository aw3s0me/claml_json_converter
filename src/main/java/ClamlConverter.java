import elements.Block;
import elements.Chapter;
import elements.Disease;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by korovin on 12/9/2016.
 */
public class ClamlConverter implements IClamlConverter {
    private Document xmlData;
    private XPath xpath;
    private Map<String, Chapter> chapters = new HashMap<String, Chapter>();
    private Map<String, Block> blocks = new HashMap<String, Block>();
    private Map<String, Disease> diseases = new HashMap<String, Disease>();


    public ClamlConverter(Document xmlData) {
        this.xmlData = xmlData;
        XPathFactory factory = XPathFactory.newInstance();
        this.xpath = factory.newXPath();
    }

    public void convertToJson() {
        this.chapters = getChapters();
        this.blocks = initBlocks();
        this.diseases = getDiseases();
    }

    private Map<String, Chapter> getChapters() {
        return null;
    }

    private Map<String, Block> initBlocks() { return null; }

    private Map<String, Disease> getDiseases() {
        return null;
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        FileLoader loader = new FileLoader();
        Document dom = loader.getDom("icd.claml.xml");
        System.out.println(dom.getDocumentElement());
    }
}
