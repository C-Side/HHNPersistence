import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Klinghammer, Lukas; eXXcellent solutions
 */
@XmlRootElement(name = "requestStatusResponse", namespace = "http://audi.de/connect/rs")
public class RequestStatusResponse {

  @XmlElement(name = "vin")
  private String vin;
  @XmlElement(name = "status")
  private String status;

  public String getVin() {
    return vin;
  }

  public String getStatus() {
    return status;
  }
}
