import elements.classes.Block;
import elements.classes.Category;
import elements.classes.Chapter;
import elements.classes.ClassKind;
import org.w3c.dom.Element;

import javax.xml.xpath.XPathExpressionException;

/**
 * Created by korovin on 12/9/2016.
 */
public class ClassKindFactory {
    public static ClassKind getClassKind(Element el) throws XPathExpressionException {
        String type = el.getAttribute("kind");

        switch (type) {
            case "block":
                return new Block(el);
            case "chapter":
                return new Chapter(el);
            case "category":
                return new Category(el);
            default:
                throw new IllegalArgumentException("The type in factory is not found: " + type);
        }
    }
}
