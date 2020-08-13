
package mx.com.nmp.ms.sivad.cambiario.ws.diamantes.datatypes;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.com.nmp.ms.sivad.cambiario.ws.diamantes.datatypes package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ActualizacionesResponse_QNAME = new QName("http://nmp.com.mx/ms/sivad/cambiario/ws/diamantes/datatypes/", "actualizacionesResponse");
    private final static QName _Tipos_QNAME = new QName("http://nmp.com.mx/ms/sivad/cambiario/ws/diamantes/datatypes/", "tipos");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.com.nmp.ms.sivad.cambiario.ws.diamantes.datatypes
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Void }
     * 
     */
    public Void createVoid() {
        return new Void();
    }

    /**
     * Create an instance of {@link TiposType }
     * 
     */
    public TiposType createTiposType() {
        return new TiposType();
    }

    /**
     * Create an instance of {@link TipocambioType }
     * 
     */
    public TipocambioType createTipocambioType() {
        return new TipocambioType();
    }

    /**
     * Create an instance of {@link ActualizacionesType }
     * 
     */
    public ActualizacionesType createActualizacionesType() {
        return new ActualizacionesType();
    }

    /**
     * Create an instance of {@link SucursalesType }
     * 
     */
    public SucursalesType createSucursalesType() {
        return new SucursalesType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Void }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nmp.com.mx/ms/sivad/cambiario/ws/diamantes/datatypes/", name = "actualizacionesResponse")
    public JAXBElement<Void> createActualizacionesResponse(Void value) {
        return new JAXBElement<Void>(_ActualizacionesResponse_QNAME, Void.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TiposType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nmp.com.mx/ms/sivad/cambiario/ws/diamantes/datatypes/", name = "tipos")
    public JAXBElement<TiposType> createTipos(TiposType value) {
        return new JAXBElement<TiposType>(_Tipos_QNAME, TiposType.class, null, value);
    }

}
