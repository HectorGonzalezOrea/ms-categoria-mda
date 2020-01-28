package mx.com.nmp.consolidados.mongodb.entity.caster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import mx.com.nmp.consolidados.model.ConsultarArchivoConsolidadoResInner;
import mx.com.nmp.consolidados.model.InfoProducto;
import mx.com.nmp.consolidados.mongodb.entity.ArchivoEntity;

public class CastConsolidados {
	public ConsultarArchivoConsolidadoResInner fillVoValues(ArchivoEntity entity) {
		ConsultarArchivoConsolidadoResInner consolidado=null;
		if(entity!=null) {
			try {
				consolidado=new ConsultarArchivoConsolidadoResInner();
				consolidado.setIdArchivo(Integer.valueOf(entity.getIdArchivo().intValue()));
				consolidado.setFechaReporte(entity.getFechaAplicacion());
				consolidado.nombreArchivo(entity.getAdjunto().getName());
				File archivo =entity.getAdjunto();
				FileReader fileReader;
				fileReader = new FileReader(archivo);
				BufferedReader b=new BufferedReader(fileReader);
	    		List<InfoProducto> productos=cvsLectura(b);
				consolidado.setProducto(productos);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
    		
		}
		return consolidado;
	}
	public List<ConsultarArchivoConsolidadoResInner> toVOs(List<ArchivoEntity> entities){
		List<ConsultarArchivoConsolidadoResInner> lstConsolidados=null;
		
		if(entities != null && !entities.isEmpty()){
			lstConsolidados=new ArrayList<>();
			for (ArchivoEntity entity : entities) {
				lstConsolidados.add(fillVoValues(entity));
			}
		}
		return lstConsolidados;
	}
	
	public File convert(MultipartFile file) throws IOException {
	     File convFile = new File(file.getOriginalFilename());
	     convFile.createNewFile();
	     FileOutputStream fos = new FileOutputStream(convFile);
	     fos.write(file.getBytes());
	     fos.close();
	     return convFile;
	}
	
	private List<InfoProducto> cvsLectura(BufferedReader b) {
    	String line = "";
        String cvsSplitBy = ",";
    	List<InfoProducto> lst =new ArrayList<>();
    	InfoProducto p;
    	String aux="idProducto";
    	try {
			while ((line = b.readLine()) != null) {
				p=new InfoProducto();
			    String[] csv = line.split(cvsSplitBy);
			    System.out.println(csv[0]+"*"+csv[1]+"*"+csv[2]+"*"+csv[3]+"*"+csv[4]);
			    if(!csv[0].equals(aux)) {
			    p.setIdProducto(Integer.parseInt(csv[0]));
			    p.setUbicacionActual(csv[1]);
			    p.setFolioSku(Integer.parseInt(csv[2]));
			    p.setPrestamoCosto(Float.valueOf(csv[3]));
			    p.setPrecioFinal(Float.valueOf(csv[4]));
			    lst.add(p);	
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return lst;
    }	
	
}
