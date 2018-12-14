import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Klinghammer, Lukas; eXXcellent solutions
 */
public class SAX extends DefaultHandler {

  private Hashtable<String, Integer> tags;
  private Hashtable<String, String> values;
  private StringBuilder data;

  @Override
  public void startDocument() {
    tags = new Hashtable<>();
    values = new Hashtable<>();
    data = new StringBuilder();
  }

  @Override
  public void startElement(String namespaceURI,
                           String localName,
                           String qName,
                           Attributes atts) {

    Integer value = tags.get(localName);

    if (value == null) {
      tags.put(localName, 1);
    }
    else {
      int count = value;
      count++;
      tags.put(localName, count);
    }
  }

  @Override
  public void endElement (String uri, String localName, String qName) {
    values.put(localName, data.toString());
  }

  @Override
  public void endDocument() {
    Enumeration e = tags.keys();
    while (e.hasMoreElements()) {
      String tag = (String)e.nextElement();
      int count = tags.get(tag);
      String value = values.get(tag);
      System.out.println("Local Name \"" + tag + "\" occurs "
          + count + " times. Has the following values: " + value);
    }
  }

  @Override
  public void characters(char ch[], int start, int length) {
    data.delete(0, data.length());
    data.append(new String(ch, start, length));
  }

  static public void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
    String fileName = "grey/rsa/RsaViolation.xml";

    SAXParserFactory spf = SAXParserFactory.newInstance();
    spf.setNamespaceAware(true);
    XMLReader xmlReader = spf.newSAXParser().getXMLReader();
    xmlReader.setContentHandler(new SAX());
    xmlReader.parse(fileName);
  }
}
