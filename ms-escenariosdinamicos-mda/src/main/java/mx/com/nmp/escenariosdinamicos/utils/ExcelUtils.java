package mx.com.nmp.escenariosdinamicos.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.nmp.escenariosdinamicos.oag.vo.PreciosVO;

public class ExcelUtils {
	private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);
	private  String[] header() {
		log.info("Generando el header");
		String [] header= new String[5];
		header[0]="Partida";
		header[1]="SKU";
		header[2]="Prestamo";
		header[3]="Precio";
		header[4]="Precio etiqueta";
		return header;
	}
	private  void printHeader(Sheet sheet , Workbook workbook ) {
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		Row headerRow = sheet.createRow(0);
		String[]header=header();
		for(int head=0; head<header.length;head++) {
			 Cell cell = headerRow.createCell(head);
			 cell.setCellValue(header[head]);
	         cell.setCellStyle(style);
		}
	}
	
	public byte[] crearExcelNotificaciones(List<PreciosVO> lstPrecios) {
		Row row= null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] xls = null;
		// Creamos el libro de trabajo de Excel formato OOXML
		Workbook workbook = new XSSFWorkbook();
		// La hoja donde pondremos los datos
		Sheet sheet=workbook.createSheet("Resultado");
		//crae los header del excel
		printHeader(sheet,workbook);
		//inicia el contador en 1 para pintar apartir de la sgunda fila
		int rowNum = 1;
		try {
			for ( PreciosVO precios : lstPrecios) {
				row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(precios.getFolioPartida());
				row.createCell(1).setCellValue(precios.getSku());
				row.createCell(2).setCellValue(precios.getPrecioActual());
				row.createCell(3).setCellValue(precios.getPrecioModificado());
				row.createCell(4).setCellValue(precios.getPrecioModificado());
			}
		
		    workbook.write(baos);
			xls=baos.toByteArray();
			baos.close();
			workbook.close();
		} catch (IOException e) {
			log.info("Ocurrio un error al generar el excel: {0} ",e);
			
		}finally {
			try {
				baos.close();
				workbook.close();
			} catch (IOException e) {
				log.info("Ocurrio un error al cerrar el recurso del excel: {0}",e);
			}
			
		}
		return xls;
		
	}

}
