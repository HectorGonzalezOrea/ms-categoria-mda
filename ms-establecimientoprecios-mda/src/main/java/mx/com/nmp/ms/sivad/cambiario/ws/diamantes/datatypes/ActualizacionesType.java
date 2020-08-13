
package mx.com.nmp.ms.sivad.cambiario.ws.diamantes.datatypes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para actualizacionesType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="actualizacionesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipocambio" type="{http://nmp.com.mx/ms/sivad/cambiario/ws/diamantes/datatypes/}tipocambioType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "actualizacionesType", propOrder = {
    "tipocambio"
})
public class ActualizacionesType {

    @XmlElement(required = true)
    protected List<TipocambioType> tipocambio;

    /**
     * Gets the value of the tipocambio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tipocambio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTipocambio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipocambioType }
     * 
     * 
     */
    public List<TipocambioType> getTipocambio() {
        if (tipocambio == null) {
            tipocambio = new ArrayList<>();
        }
        return this.tipocambio;
    }

}
