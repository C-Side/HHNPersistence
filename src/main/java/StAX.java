import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author Klinghammer, Lukas; eXXcellent solutions
 */
public class StAX {

  private Hashtable<String, Integer> tags;
  private Hashtable<String, String> values;
  private StringBuilder data;

  static public void main(String[] args) {
    StAX stAX = new StAX();
    stAX.readXML();
    stAX.print();
  }

  private void readXML() {
    tags = new Hashtable<>();
    values = new Hashtable<>();
    data = new StringBuilder();

    String fileName = "grey/rsa/RsaViolation.xml";

    XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
    try {

      XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));

      while (xmlEventReader.hasNext()) {

        XMLEvent xmlEvent = xmlEventReader.nextEvent();

        if (xmlEvent.isStartElement()) {
          String localName = xmlEvent.asStartElement().getName().getLocalPart();
          Integer value = tags.get(localName);

          if (value == null) {
            tags.put(localName, 1);
          }
          else {
            int count = value;
            count++;
            tags.put(localName, count);
          }
        } else if (xmlEvent.isCharacters()) {
          data.delete(0, data.length());
          data.append(xmlEvent.asCharacters());
        } else if (xmlEvent.isEndElement()) {
          values.put(xmlEvent.asEndElement().getName().getLocalPart(), data.toString());
        }
      }
    } catch (XMLStreamException | FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void print() {
    Enumeration e = tags.keys();
    while (e.hasMoreElements()) {
      String tag = (String)e.nextElement();
      int count = tags.get(tag);
      String value = values.get(tag);
      System.out.println("Local Name \"" + tag + "\" occurs "
          + count + " times. Has the following values: " + value);
    }
  }
}
