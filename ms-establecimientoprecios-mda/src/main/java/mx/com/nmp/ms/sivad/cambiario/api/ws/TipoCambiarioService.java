
package mx.com.nmp.ms.sivad.cambiario.api.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import mx.com.nmp.ms.sivad.cambiario.ws.diamantes.datatypes.ObjectFactory;
import mx.com.nmp.ms.sivad.cambiario.ws.diamantes.datatypes.TiposType;
import mx.com.nmp.ms.sivad.cambiario.ws.diamantes.datatypes.Void;


/**
 * 
 * 
 */
@WebService(name = "TipoCambiarioService", targetNamespace = "http://nmp.com.mx/ms/sivad/cambiario/ws/diamantes/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface TipoCambiarioService {


    /**
     * 
     * @param actualizarRequest
     * @return
     *     returns mx.com.nmp.ms.sivad.cambiario.ws.diamantes.datatypes.Void
     */
    @WebMethod(action = "http://nmp.com.mx/ms/cambiario/ws/diamantes/actualizar")
    @WebResult(name = "actualizacionesResponse", targetNamespace = "http://nmp.com.mx/ms/sivad/cambiario/ws/diamantes/datatypes/", partName = "ActualizarResponse")
    public Void actualizar(
        @WebParam(name = "tipos", targetNamespace = "http://nmp.com.mx/ms/sivad/cambiario/ws/diamantes/datatypes/", partName = "ActualizarRequest")
        TiposType actualizarRequest);

}
