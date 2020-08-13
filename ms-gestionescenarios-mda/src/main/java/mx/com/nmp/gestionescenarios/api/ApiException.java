package mx.com.nmp.gestionescenarios.api;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-20T16:07:47.599Z")

public class ApiException extends Exception{
    public ApiException (int code, String msg) {
        super(msg);
    }
}
