
package mx.com.nmp.ms.sivad.cambiario.api.ws;

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
@WebServiceClient(name = "TipoCambiarioEndpointService", targetNamespace = "http://ws.api.cambiario.sivad.ms.nmp.com.mx/", wsdlLocation = "https://dev1775-mda-tipo-cambio.mybluemix.net/soap-api/TipoCambiario?WSDL")
public class TipoCambiarioEndpointService extends Service {

    private final static URL TIPOCAMBIARIOENDPOINTSERVICE_WSDL_LOCATION;
    private final static WebServiceException TIPOCAMBIARIOENDPOINTSERVICE_EXCEPTION;
    private final static QName TIPOCAMBIARIOENDPOINTSERVICE_QNAME = new QName("http://ws.api.cambiario.sivad.ms.nmp.com.mx/", "TipoCambiarioEndpointService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://dev1775-mda-tipo-cambio.mybluemix.net/soap-api/TipoCambiario?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        TIPOCAMBIARIOENDPOINTSERVICE_WSDL_LOCATION = url;
        TIPOCAMBIARIOENDPOINTSERVICE_EXCEPTION = e;
    }

    public TipoCambiarioEndpointService() {
        super(__getWsdlLocation(), TIPOCAMBIARIOENDPOINTSERVICE_QNAME);
    }

    public TipoCambiarioEndpointService(WebServiceFeature... features) {
        super(__getWsdlLocation(), TIPOCAMBIARIOENDPOINTSERVICE_QNAME, features);
    }

    public TipoCambiarioEndpointService(URL wsdlLocation) {
        super(wsdlLocation, TIPOCAMBIARIOENDPOINTSERVICE_QNAME);
    }

    public TipoCambiarioEndpointService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, TIPOCAMBIARIOENDPOINTSERVICE_QNAME, features);
    }

    public TipoCambiarioEndpointService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TipoCambiarioEndpointService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns TipoCambiarioService
     */
    @WebEndpoint(name = "TipoCambiarioEndpointPort")
    public TipoCambiarioService getTipoCambiarioEndpointPort() {
        return super.getPort(new QName("http://ws.api.cambiario.sivad.ms.nmp.com.mx/", "TipoCambiarioEndpointPort"), TipoCambiarioService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TipoCambiarioService
     */
    @WebEndpoint(name = "TipoCambiarioEndpointPort")
    public TipoCambiarioService getTipoCambiarioEndpointPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.api.cambiario.sivad.ms.nmp.com.mx/", "TipoCambiarioEndpointPort"), TipoCambiarioService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (TIPOCAMBIARIOENDPOINTSERVICE_EXCEPTION!= null) {
            throw TIPOCAMBIARIOENDPOINTSERVICE_EXCEPTION;
        }
        return TIPOCAMBIARIOENDPOINTSERVICE_WSDL_LOCATION;
    }

}
