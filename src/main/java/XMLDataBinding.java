import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import com.sun.org.apache.regexp.internal.RE;

/**
 * @author Klinghammer, Lukas; eXXcellent solutions
 */
public class XMLDataBinding {

  static public void main(String[] args) throws Exception {
    XMLDataBinding xmlDataBinding = new XMLDataBinding();
    xmlDataBinding.readXML();
  }

  private void readXML() throws Exception {
    JAXBContext jc = JAXBContext.newInstance(RequestStatusResponse.class);
    Unmarshaller u = jc.createUnmarshaller();
    RequestStatusResponse statusResponse = (RequestStatusResponse)
        u.unmarshal(new File("grey/rah/rahRequestStatusResponse.xml"));

    System.out.println(statusResponse.getVin());
    System.out.println(statusResponse.getStatus());
  }
}
