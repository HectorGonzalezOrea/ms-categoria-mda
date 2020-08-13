
package mx.com.nmp.ms.sivad.cambiario.ws.diamantes.datatypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para tiposType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="tiposType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="actualizaciones" type="{http://nmp.com.mx/ms/sivad/cambiario/ws/diamantes/datatypes/}actualizacionesType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tiposType", propOrder = {
    "fecha",
    "actualizaciones"
})
public class TiposType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fecha;
    @XmlElement(required = true)
    protected ActualizacionesType actualizaciones;

    /**
     * Obtiene el valor de la propiedad fecha.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Define el valor de la propiedad fecha.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Obtiene el valor de la propiedad actualizaciones.
     * 
     * @return
     *     possible object is
     *     {@link ActualizacionesType }
     *     
     */
    public ActualizacionesType getActualizaciones() {
        return actualizaciones;
    }

    /**
     * Define el valor de la propiedad actualizaciones.
     * 
     * @param value
     *     allowed object is
     *     {@link ActualizacionesType }
     *     
     */
    public void setActualizaciones(ActualizacionesType value) {
        this.actualizaciones = value;
    }

}
