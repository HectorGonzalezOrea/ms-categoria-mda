package mx.com.nmp.escenariosdinamicos.utils;

public final class Constantes {

	private Constantes() {
		throw new UnsupportedOperationException();
	}
	
	public static final String HEADER_APIKEY_KEY = "X-IBM-Client-Id";
	public static final String HEADER_USUARIO = "usuario";
	public static final String HEADER_ID_CONSUMIDOR = "idConsumidor";
	public static final String HEADER_ID_DESTINO = "idDestino";
	public static final String HEADER_OAUTH_BEARER = "oauth.bearer";
	public static final String GRANT_TYPE = "grant_type";
	public static final String SCOPE = "scope";
	public static final int STATUS_CODE_OK = 200;
	public static final String ID_ARCHIVO = "idArchivo";
	public static final String BASIC = "Basic ";
	public static final String ASUNTO_MESSAGE="Creacion Escenario";
	public static final String ASUNTO_MESSAGE_UPDATE="Actualización de escenarios";
	public static final String ASUNTO_MESSAGE_ELIMINA="Eliminación de escenarios";
	public static final String CONTENIDO="Se solicita la creación del nuevo escenario en el componente de OBR.";
	public static final String ESCENARIO_SEQ_KEY = "escenario_sequence";
	public static final String ID = "_id";

	public static final String HEADER_ACCEPT_KEY = "Accept";
	public static final String HEADER_ACCEPT_VALUE = "application/json";
	
	public static final String ERROR_CODE = "NMP-MDA-400";
	public static final String ERROR_MENSAJE = "El cuerpo de la petición no está bien formado, verifique su información.";
	
	public static final String EXITO_GUARDAR = "Exitoso";
	
	public static final String ERROR_SERVER="NMP-MDA-500";
	public static final String ERROR_SERVER_MSG="Error interno del servidor. Falla de comunicación.";
	
	public static final String EXITO_ELIMINAR="NMP-MDA-200";
	public static final String EXITO_ELIMINAR_MSG="Escenarios eliminados exitosamente";
	public static final String EXITO_EXCEPCION=" a excepción de los ids ";
	
	public static final String EXITO_MODIFICAR="NMP-MDA-200";
	public static final String EXITO_MODIFICAR_MSG="Operación ejecutada satisfactoriamente";
	public static final String EXITO_MODIFICAR_EXCEPCION=", los siguientes escenarios no fueron encontrados ni actualizados {";
	
	public static final String EXITO_REGLA_CODE="NMP-MDA-200";
	public static final String EXITO_REGLA_MSG="Regla existente ";
	
	public static final String EXITO_EJECUTAR_ESCENARIODINAMICO="NMP-MDA-200";
	public static final String MENSAJE_OK="Operación ejecutada satisfactoriamente.";
	
	public static final String ERROR_CODE_AUTORIZACION="NMP-MDA-401";
	public static final String MESSAGE_ERROR_AUTORIZACION="Se ha producido un error de autorización";
	
	public static final String ERROR_CODE_BAD_REQUEST = "NMP-MDA-400";
	public static final String MESSAGE_ERROR_BAD_REQUEST ="El cuerpo de la petición no está bien formado, verifique su información";
	public static final String MESSAGE_ERROR_BAD_REQUEST_DIAS="Cada dia no puede tener el valor de mas de un caracter, verifique su información";
	
	public static final String ERROR_CODE_NOT_FOUND = "NMP-MDA-404";
	public static final String MESSAGE_ERROR_CODE_NOT_FOUND ="No existen los recursos con los identificadores {";
	public static final String MESSAGE_ERROR_NOT_FOUND_COMPLEMENT="}, verificar y volver a enviar la petición";
	public static final String MESSAGE_ERROR_RULE_NOT_FOUND="La regla no cumple con los parametros proporcionados";
	
	public static final String ERROR_REPETIDOS="NMP-MDA-409";
	public static final String ERROR_REPETIDOS_MENSAJE="La información enviada ya se encuentra registrada, ";
	public static final String ERROR_REPETIDOS_COMPLEMENTO=". Verificar y volver a enviar la petición.";

	public static final String USER="usuario";
	public static final String DESTINO="origen";
	public static final String ORIGEN="destino";

	public static final String URL_VALOR_MONTE="https://dev1775-ms-valormonte-mda.mybluemix.net/NMP/MotorDescuentosAPI/v1/valorMonte";

	
	public static final String PRECIO_MEDIO="PM";
	public static final String PRECIO_ALTO="PA";
	public static final String PRECIO_BAJO="PB";
	
	public static final int PRECIO_ETIQUETA=1;
	public static final int PRECIO_ACTUAL=2;
	public static final int PRESTAMO=3;
	public static final int VALOR_MONTE_ACTUALIZADO=4;
	public static final int AVALUO_TECNICO=5;
	public static final int AVALUO_COMERCIAL=6;
	public static final int PRECIO_MERCADO=7;
	
	public static final int NUMERO_MAXIMO_SCROLL =5;
	public static final int DIFERENCIA_DIAS =-33;
	public static final String CAMPO_FECHA_INDEX="fecha_op";
	public static final String PARTIDA="partida";
	public static final String COU_VENTA="cuo";
	
	public static final String REQUEST_LOG = "Request: ";
	public static final String S_LOG = "*********************";
	public static final String SUCCESS_SEND_LOG = "successful send ";
	public static final String SEND_LOG = "Send message ==>>";
	public static final String RECIEVED_LOG = "Recieved message ==>>";

	public static final String TEMPLATE_NOTIFICACION_EMAIL_HEAD="<body>\r\n" + 
			"	<table style=\"border-collapse: collapse;\">\r\n" + 
			"		<caption>Reporte de precios</caption>\r\n" + 
			"		<thead>\r\n" + 
			"		<tr>\r\n" + 
			"			<th style=\"border: 1px solid black\">Partida</th>\r\n" + 
			"			<th style=\"border: 1px solid black\">SKU</th>\r\n" + 
			"			<th style=\"border: 1px solid black\">Prestamo</th>\r\n" + 
			"			<th style=\"border: 1px solid black\">Precio</th>\r\n" + 
			"			<th style=\"border: 1px solid black\">Precio etiqueta</th>\r\n" + 
			"		</tr>\r\n" + 
			"	</thead>\r\n" + 
			"	<tbody>";
	public static final String TEMPLATE_NOTIFICACION_EMAIL_FOOTER="	</tbody>\r\n" + 
			"	</table>\r\n" + 
			"</body>";
	public static final String ASUNTO_AJUSTE_PRECIOS_FALSE = "Notificación de Partidas a las que no se pudo hacer el ajuste de precios.";

	public static final String ABRIR_TD_STYLE="<td style=\"border: 1px solid black\">";
	public static final String CERRAR_TD="</td>";
	public static final String TR="<tr>";
	public static final String CLOSE_TR="</tr>";
	public static final String TABLE="<table style=\"border-collapse: collapse;\">";
	public static final String CLOSE_TABLE="</table>";
	public static final String ID_ESCENARIO="idEscenario";
	public static final String SALTO="<br>";
	public static final String TEMPLATE_UPDATE="<td style=\"border: 1px solid black\">idEscenario</td><td style=\"border: 1px solid black\">diaUno</td><td style=\"border: 1px solid black\">diaDos</td><td style=\"border: 1px solid black\">diaTres</td><td style=\"border: 1px solid black\">idRegla</td>";
	
	public static final String CERO="0";
	public static final String X="X";
	public static final String S="S";
	public static final String B="B";
	public static final String M="M";
	public static final String ESCENARIO="PM- Escenario";
	public static final String MAX="maximo";
	public static final String SKU_OPT="8005050";
	
	//nivelAgrupacion desde el request
	public static final String RAMO="Ramo";
	public static final String SUBRAMO="Subramo";
	public static final String FACTOR="Factor";
	public static final String CATEGORIA="Categoria";
	
	//nivelAgrupacion desde el indice de elastic
	public static final String SUBRAMO_DES="subramo_des";
	public static final String FACTOR_DES="factor";
	public static final String RAMO_DES= "ramo_des";
}
