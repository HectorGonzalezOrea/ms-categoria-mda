package mx.com.nmp.usuarios.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import mx.com.nmp.usuarios.model.BadRequest;
import mx.com.nmp.usuarios.model.InternalServerError;
import mx.com.nmp.usuarios.utils.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		BadRequest bd = new BadRequest();
		bd.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
		bd.setMensaje(ex.getMessage());

		logger.error("{}" , bd);
		
		return new ResponseEntity(bd, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		BadRequest bd = new BadRequest();
		bd.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
		bd.setMensaje(ex.getMessage());

		logger.error("{}" , bd);
		
		return new ResponseEntity(bd, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		BadRequest bd = new BadRequest();
		bd.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
		bd.setMensaje(ex.getMessage());

		logger.error("{}" , bd);
		
		return new ResponseEntity(bd, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		BadRequest bd = new BadRequest();
		bd.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
		bd.setMensaje(ex.getMessage());

		logger.error("{}" , bd);
		
		return new ResponseEntity(bd, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ApiException.class})
	protected ResponseEntity<InternalServerError> handleEntityNotFound(Exception bae) {		
		InternalServerError ise = new InternalServerError();
		ise.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
		ise.setMensaje(bae.getMessage());

		logger.error("{}" , ise);
		
		return new ResponseEntity(ise, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}