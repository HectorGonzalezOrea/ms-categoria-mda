package mx.com.nmp.consolidados.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static mx.com.nmp.consolidados.utils.Constantes.PRODUCTOS;

public class ExcelUtils {

	private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);

	public Workbook crearExcelNotificacionesCorrectas(List<String> productos) {

        File archivo = new File("reporte.xlsx");

        Workbook workbook = new XSSFWorkbook();

        Sheet pagina = workbook.createSheet("Reporte de productos");

        ArrayList<String> titulos = new ArrayList<>();
        titulos.add(PRODUCTOS);
 
        Row fila = pagina.createRow(0);

        for (int i = 0; i < titulos.size(); i++) {
            Cell celda = fila.createCell(i);
            
            celda.setCellValue(titulos.get(i));
        }

        fila = pagina.createRow(1);

        for (int i = 0; i < productos.size(); i++) {
            Cell celda = fila.createCell(i);

            celda.setCellValue(productos.get(i));
        }

        try {
            FileOutputStream salida = new FileOutputStream(archivo);

            workbook.write(salida);

            workbook.close();

            log.info("Archivo creado existosamente en {0}", archivo.getAbsolutePath());

        } catch (FileNotFoundException ex) {
        	log.info("Archivo no localizable en sistema de archivos");
        } catch (IOException ex) {
        	log.info("Error de entrada/salida");
        }
		
		return workbook;
	}
	
	public static void main(String[] args) {

		ArrayList<String> productos = new ArrayList<>();
		
		productos.add("sku1");
		productos.add("sku2");
		productos.add("sku3");
		productos.add("sku4");
		productos.add("sku5");
		
		ExcelUtils e = new ExcelUtils();
		
		Workbook archivo = e.crearExcelNotificacionesCorrectas(productos);
		
		String encodedString = ConvertStringToBase64.encode(archivo.toString());
		log.info("String : {}", encodedString);
	}

}
