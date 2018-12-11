import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
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

  private Hashtable tags;

  public void startDocument() {
    tags = new Hashtable();
  }

  public void startElement(String namespaceURI,
                           String localName,
                           String qName,
                           Attributes atts) {

    Object value = tags.get(localName);

    if (value == null) {
      tags.put(localName, 1);
    }
    else {
      int count = (Integer) value;
      count++;
      tags.put(localName, count);
    }
  }

  public void endDocument() {
    Enumeration e = tags.keys();
    while (e.hasMoreElements()) {
      String tag = (String)e.nextElement();
      int count = (Integer) tags.get(tag);
      System.out.println("Local Name \"" + tag + "\" occurs "
          + count + " times");
    }
  }

  static public void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
    String filename = "grey/rsa/RsaViolation.xml";

    SAXParserFactory spf = SAXParserFactory.newInstance();
    spf.setNamespaceAware(true);
    SAXParser saxParser = spf.newSAXParser();
    XMLReader xmlReader = saxParser.getXMLReader();
    xmlReader.setContentHandler(new SAX());
    xmlReader.parse(filename);
  }
}
