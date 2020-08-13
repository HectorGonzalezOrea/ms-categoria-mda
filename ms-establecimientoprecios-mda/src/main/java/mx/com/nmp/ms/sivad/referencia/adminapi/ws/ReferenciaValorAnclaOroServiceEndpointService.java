
package mx.com.nmp.ms.sivad.referencia.adminapi.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;




/**
 * 
 * 
 */
@WebServiceClient(name = "ReferenciaValorAnclaOroServiceEndpointService", targetNamespace = "http://ws.adminapi.referencia.sivad.ms.nmp.com.mx/", wsdlLocation = "https://dev1775-mda-tablas-de-referencia.mybluemix.net/soap-api/ReferenciaValorAnclaOroService?WSDL")
public class ReferenciaValorAnclaOroServiceEndpointService extends Service {

    private final static URL REFERENCIAVALORANCLAOROSERVICEENDPOINTSERVICE_WSDL_LOCATION;
    private final static WebServiceException REFERENCIAVALORANCLAOROSERVICEENDPOINTSERVICE_EXCEPTION;
    private final static QName REFERENCIAVALORANCLAOROSERVICEENDPOINTSERVICE_QNAME = new QName("http://ws.adminapi.referencia.sivad.ms.nmp.com.mx/", "ReferenciaValorAnclaOroServiceEndpointService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://dev1775-mda-tablas-de-referencia.mybluemix.net/soap-api/ReferenciaValorAnclaOroService?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        REFERENCIAVALORANCLAOROSERVICEENDPOINTSERVICE_WSDL_LOCATION = url;
        REFERENCIAVALORANCLAOROSERVICEENDPOINTSERVICE_EXCEPTION = e;
    }

    public ReferenciaValorAnclaOroServiceEndpointService() {
        super(__getWsdlLocation(), REFERENCIAVALORANCLAOROSERVICEENDPOINTSERVICE_QNAME);
    }

    public ReferenciaValorAnclaOroServiceEndpointService(WebServiceFeature... features) {
        super(__getWsdlLocation(), REFERENCIAVALORANCLAOROSERVICEENDPOINTSERVICE_QNAME, features);
    }

    public ReferenciaValorAnclaOroServiceEndpointService(URL wsdlLocation) {
        super(wsdlLocation, REFERENCIAVALORANCLAOROSERVICEENDPOINTSERVICE_QNAME);
    }

    public ReferenciaValorAnclaOroServiceEndpointService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, REFERENCIAVALORANCLAOROSERVICEENDPOINTSERVICE_QNAME, features);
    }

    public ReferenciaValorAnclaOroServiceEndpointService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ReferenciaValorAnclaOroServiceEndpointService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ReferenciaValorAnclaOroService
     */
    @WebEndpoint(name = "ReferenciaValorAnclaOroServiceEndpointPort")
    public ReferenciaValorAnclaOroService getReferenciaValorAnclaOroServiceEndpointPort() {
        return super.getPort(new QName("http://ws.adminapi.referencia.sivad.ms.nmp.com.mx/", "ReferenciaValorAnclaOroServiceEndpointPort"), ReferenciaValorAnclaOroService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ReferenciaValorAnclaOroService
     */
    @WebEndpoint(name = "ReferenciaValorAnclaOroServiceEndpointPort")
    public ReferenciaValorAnclaOroService getReferenciaValorAnclaOroServiceEndpointPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.adminapi.referencia.sivad.ms.nmp.com.mx/", "ReferenciaValorAnclaOroServiceEndpointPort"), ReferenciaValorAnclaOroService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (REFERENCIAVALORANCLAOROSERVICEENDPOINTSERVICE_EXCEPTION!= null) {
            throw REFERENCIAVALORANCLAOROSERVICEENDPOINTSERVICE_EXCEPTION;
        }
        return REFERENCIAVALORANCLAOROSERVICEENDPOINTSERVICE_WSDL_LOCATION;
    }

}
