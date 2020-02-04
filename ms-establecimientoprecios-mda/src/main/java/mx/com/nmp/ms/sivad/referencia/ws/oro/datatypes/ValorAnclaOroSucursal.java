
package mx.com.nmp.ms.sivad.referencia.ws.oro.datatypes;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ValorAnclaOroSucursal complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ValorAnclaOroSucursal">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="calidad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="precio" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="sucursal" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValorAnclaOroSucursal", propOrder = {
    "calidad",
    "precio",
    "sucursal"
})
public class ValorAnclaOroSucursal {

    @XmlElement(required = true)
    protected String calidad;
    @XmlElement(required = true)
    protected BigDecimal precio;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer sucursal;

    /**
     * Obtiene el valor de la propiedad calidad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalidad() {
        return calidad;
    }

    /**
     * Define el valor de la propiedad calidad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalidad(String value) {
        this.calidad = value;
    }

    /**
     * Obtiene el valor de la propiedad precio.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * Define el valor de la propiedad precio.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPrecio(BigDecimal value) {
        this.precio = value;
    }

    /**
     * Obtiene el valor de la propiedad sucursal.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSucursal() {
        return sucursal;
    }

    /**
     * Define el valor de la propiedad sucursal.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSucursal(Integer value) {
        this.sucursal = value;
    }

}
