package mx.com.nmp.consolidados.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.nmp.consolidados.oag.vo.PartidaResponseVO;

import static mx.com.nmp.consolidados.utils.Constantes.SKU;
import static mx.com.nmp.consolidados.utils.Constantes.PARTIDA;
import static mx.com.nmp.consolidados.utils.Constantes.CUMPLE_ARBITRARIEDAD;

public class ExcelUtils {

	private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);

	public File crearExcelNotificacionesCorrectas(List<PartidaResponseVO> partidas) {

        File archivo = new File("reporte.xlsx");

        Workbook workbook = new XSSFWorkbook();

        Sheet pagina = workbook.createSheet("Reporte de productos");

        ArrayList<String> titulos = new ArrayList<>();
        titulos.add(SKU);
        titulos.add(PARTIDA);
        titulos.add(CUMPLE_ARBITRARIEDAD);
 
        Row fila = pagina.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        for (int i = 0; i < titulos.size(); i++) {
            Cell celda = fila.createCell(i);
            
            celda.setCellValue(titulos.get(i));
            celda.setCellStyle(headerStyle);
        }
        
        for (int i = 0; i < partidas.size(); i++) {
        	
        	fila = pagina.createRow(i + 1);
        	
            Cell celda = fila.createCell(0);
            celda.setCellValue(partidas.get(i).getSku());
            
            Cell celda1 = fila.createCell(1);
            celda1.setCellValue(partidas.get(i).getIdPartida());
            
            Cell celda2 = fila.createCell(2);
            celda2.setCellValue(partidas.get(i).getCumpleArbitraje());
        }

        try(FileOutputStream salida = new FileOutputStream(archivo)) {
            

            workbook.write(salida);

            workbook.close();
      
        } catch (FileNotFoundException ex) {
        	log.info("Archivo no localizable en sistema de archivos");
        } catch (IOException ex) {
        	log.info("Error de entrada/salida");
        }finally {
        	try {
				workbook.close();
			} catch (IOException e) {
				log.error("{0}",e);
			}
        }
		
		return archivo;
	}
	
	public File crearExcelNotificacionesFallidas(List<PartidaResponseVO> partidas) {

        File archivo = new File("reporteFallidas.xlsx");

        Workbook workbook = new XSSFWorkbook();

        Sheet pagina = workbook.createSheet("Reporte de productos que no se pudo hacer su ajuste");

        ArrayList<String> titulos = new ArrayList<>();
        titulos.add(SKU);
        titulos.add(PARTIDA);
 
        Row fila = pagina.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        for (int i = 0; i < titulos.size(); i++) {
            Cell celda = fila.createCell(i);
            
            celda.setCellValue(titulos.get(i));
            celda.setCellStyle(headerStyle);
        }
        
        for (int i = 0; i < partidas.size(); i++) {
        	
        	fila = pagina.createRow(i + 1);
        	
            Cell celda = fila.createCell(0);
            celda.setCellValue(partidas.get(i).getSku());
            
            Cell celda1 = fila.createCell(1);
            celda1.setCellValue(partidas.get(i).getIdPartida());
        }

        try( FileOutputStream salida = new FileOutputStream(archivo)) {
           

            workbook.write(salida);

            workbook.close();
      
        } catch (FileNotFoundException ex) {
        	log.info("Archivo no localizable en sistema de archivos");
        } catch (IOException ex) {
        	log.info("Error de entrada/salida");
        }finally {
        	try {
				workbook.close();
			} catch (IOException e) {
				log.error("{0}",e);
			}
        }
		
		return archivo;
	}
		
}

