package mx.com.nmp.consolidados.utils;

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
	
	public static final String PRODUCTOS = "Productos";
	
	public static final String SKU = "SKU";
	public static final String PARTIDA = "Partida";
	public static final String CUMPLE_ARBITRARIEDAD = "Cumple Arbitrariedad";
	
	public static final String NOMBRE_EXCEL = "Reporte.xlsx";
	public static final String ASUNTO_ARBITRARIEDAD_TRUE = "Notificación de Partidas que cumplieron la arbitrariedad.";
	public static final String CONTENIDO_HTML_ARBITRARIEDAD_TRUE = "'Se le informa que estas son las partidas que <i><b>si</b></i> cumplieron la arbitrariedad.' <br/><br/><br/>";
	
	public static final String ARMAR_TABLA_INICIO = "<html>\r\n" + 
			"	<body >\r\n" + 
			"		<table border=1>\r\n" + 
			"			<caption>Partidas</caption>\r\n" + 
			"			<tr>\r\n" + 
			"				<th>\r\n" + 
			"					SKU\r\n" + 
			"				</th>\r\n" + 
			"				<th>\r\n" + 
			"					Partida\r\n" + 
			"				</th>\r\n" + 
			"				<th>\r\n" + 
			"					Arbitrariedad\r\n" + 
			"				</th>\r\n" + 
			"			</tr>";
	public static final String ARMAR_TABLA_FINAL = "		</table>\r\n" + 
			"	</body>\r\n" + 
			"</html>";
	
	public static final String ARMAR_TABLA_INICIO_FALLIDOS = "<html>\r\n" + 
			"	<body >\r\n" + 
			"		<table border=1>\r\n" + 
			"			<caption>Partidas</caption>\r\n" + 
			"			<tr>\r\n" + 
			"				<th>\r\n" + 
			"					SKU\r\n" + 
			"				</th>\r\n" + 
			"				<th>\r\n" + 
			"					Id Partida\r\n" + 
			"				</th>\r\n" + 
			"			</tr>";
	public static final String ARMAR_TABLA_FINAL_FALLIDOS = "		</table>\r\n" + 
			"	</body>\r\n" + 
			"</html>";
	
	public static final String ASUNTO_AJUSTE_PRECIOS_FALSE = "Notificación de Partidas a las que no se pudo hacer el ajuste de precios.";
	public static final String CONTENIDO_HTML_PRECIOS_FALSE_INICIO = "'Se le informa que el reporte solicitado '";
	public static final String CONTENIDO_HTML_PRECIOS_FALSE_FINAL = "' contiene las partidas que no han sido procesadas <br/><i>Favor de ingresar al portal de inteligencia comercial para verificar los cambios realizados.</i>' <br/><br/><br/>";
	
	public static final String CONTENIDO_HTML_BI = "<i><b>";
	public static final String CONTENIDO_HTML_BI_FINAL = "</b></i>";
	
}
