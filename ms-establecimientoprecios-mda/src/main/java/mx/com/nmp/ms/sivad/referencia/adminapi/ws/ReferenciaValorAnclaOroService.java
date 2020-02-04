
package mx.com.nmp.ms.sivad.referencia.adminapi.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import mx.com.nmp.ms.sivad.referencia.ws.oro.datatypes.ObjectFactory;
import mx.com.nmp.ms.sivad.referencia.ws.oro.datatypes.ValorAnclaOroSucursalRequest;
import mx.com.nmp.ms.sivad.referencia.ws.oro.datatypes.Void;


/**
 * 
 */
@WebService(name = "ReferenciaValorAnclaOroService", targetNamespace = "http://nmp.com.mx/ms/sivad/referencia/ws/oro/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ReferenciaValorAnclaOroService {


    /**
     * 
     * @param parameters
     * @return
     *     returns mx.com.nmp.ms.sivad.referencia.ws.oro.datatypes.Void
     */
    @WebMethod(action = "http://nmp.com.mx/ms/referencia/ws/oro/actualizarValorAnclaOro")
    @WebResult(name = "valorAnclaOroSucursalResponse", targetNamespace = "http://nmp.com.mx/ms/sivad/referencia/ws/oro/datatypes/", partName = "parameters")
    public Void actualizarValorAnclaOro(
        @WebParam(name = "valorAnclaOroSucursalRequest", targetNamespace = "http://nmp.com.mx/ms/sivad/referencia/ws/oro/datatypes/", partName = "parameters")
        ValorAnclaOroSucursalRequest parameters);

}
