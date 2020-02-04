
package mx.com.nmp.ms.sivad.referencia.ws.oro.datatypes;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.com.nmp.ms.sivad.referencia.ws.oro.datatypes package. 
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

    private final static QName _ValorAnclaOroSucursalResponse_QNAME = new QName("http://nmp.com.mx/ms/sivad/referencia/ws/oro/datatypes/", "valorAnclaOroSucursalResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.com.nmp.ms.sivad.referencia.ws.oro.datatypes
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
     * Create an instance of {@link ValorAnclaOroSucursalRequest }
     * 
     */
    public ValorAnclaOroSucursalRequest createValorAnclaOroSucursalRequest() {
        return new ValorAnclaOroSucursalRequest();
    }

    /**
     * Create an instance of {@link ValorAnclaOroSucursal }
     * 
     */
    public ValorAnclaOroSucursal createValorAnclaOroSucursal() {
        return new ValorAnclaOroSucursal();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Void }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://nmp.com.mx/ms/sivad/referencia/ws/oro/datatypes/", name = "valorAnclaOroSucursalResponse")
    public JAXBElement<Void> createValorAnclaOroSucursalResponse(Void value) {
        return new JAXBElement<Void>(_ValorAnclaOroSucursalResponse_QNAME, Void.class, null, value);
    }

}
