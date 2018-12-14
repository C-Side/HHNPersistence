import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Klinghammer, Lukas; eXXcellent solutions
 */
public class DOM {
  private Hashtable<String, Integer> tags;
  private Hashtable<String, String> values;
  private StringBuilder data;

  static public void main(String[] args) throws Exception {
    DOM dom = new DOM();
    dom.readXML();
    dom.print();
  }

  private void readXML() throws Exception {
    tags = new Hashtable<>();
    values = new Hashtable<>();
    data = new StringBuilder();

    String fileName = "grey/rsa/RsaViolation.xml";

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setNamespaceAware(true);
    Document doc = dbf.newDocumentBuilder().parse(new File(fileName));

    NodeList allNodes = doc.getElementsByTagName("*");
    for (int i = 0, len = allNodes.getLength(); i < len; i++) {
      Node node = allNodes.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        String localName = node.getLocalName();
        Integer tagValue = tags.get(localName);

        if (tagValue == null) {
          tags.put(localName, 1);
        } else {
          int count = tagValue;
          count++;
          tags.put(localName, count);
        }

        data.delete(0, data.length());
        data.append(node.getTextContent());
        values.put(node.getLocalName(), data.toString());
      }
    }
  }

  private void print() {
    Enumeration e = tags.keys();
    while (e.hasMoreElements()) {
      String tag = (String) e.nextElement();
      int count = tags.get(tag);
      String value = values.get(tag);
      System.out.println("Local Name \"" + tag + "\" occurs "
          + count + " times. Has the following values: " + value);
    }
  }
}
