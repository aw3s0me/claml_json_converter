import elements.ClassKind;
import elements.Block;
import elements.Chapter;
import elements.Category;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by korovin on 12/9/2016.
 */
public class ClamlConverter implements IClamlConverter {
    private Document dom;
    private XPath xpath;
    private Map<String, Chapter> chapters;
    private Map<String, Block> blocks = new HashMap<>();
    private Map<String, Category> diseases = new HashMap<>();


    public ClamlConverter(Document dom) {
        this.dom = dom;
        XPathFactory factory = XPathFactory.newInstance();
        this.xpath = factory.newXPath();
    }

    public void convertToJson() throws XPathExpressionException, InstantiationException, IllegalAccessException {
        this.chapters = this.getClassKindInstances(Chapter.class);
        this.blocks = this.getClassKindInstances(Block.class);
        this.diseases = this.getClassKindInstances(Category.class);
    }

    private <T extends ClassKind> Map<String, T> getClassKindInstances(Class<T> classType) throws XPathExpressionException, IllegalAccessException, InstantiationException {
        String chapterExpression = String.format("/ClaML/Class[@kind='%1$s']", classType.getSimpleName().toLowerCase());
        NodeList classKindNodes = (NodeList) this.xpath.compile(chapterExpression).evaluate(this.dom, XPathConstants.NODESET);
        Map<String, T> chapters = new HashMap<>();
        for (int i = 0; i < classKindNodes.getLength(); i++) {
            if (classKindNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) classKindNodes.item(i);
                ClassKind classKind = ClassKindFactory.getClassKind(el);
                chapters.put(classKind.getCode(), classType.cast(classKind));
            }
        }
        return chapters;
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, IllegalAccessException, InstantiationException {
        FileLoader loader = new FileLoader();
        Document dom = loader.getDom("icd.claml.xml");
        ClamlConverter converter = new ClamlConverter(dom);
        converter.convertToJson();
    }
}
