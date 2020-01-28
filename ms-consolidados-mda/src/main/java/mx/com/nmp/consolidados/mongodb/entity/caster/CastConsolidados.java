package mx.com.nmp.consolidados.mongodb.entity.caster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import mx.com.nmp.consolidados.model.Consolidados;
import mx.com.nmp.consolidados.model.InfoProducto;
import mx.com.nmp.consolidados.mongodb.entity.ArchivoEntity;

public class CastConsolidados {
	public Consolidados fillVoValues(ArchivoEntity entity) {
		Consolidados consolidado=null;
		if(entity!=null) {
			consolidado=new Consolidados();
			consolidado.setIdArchivo(entity.getIdArchivo());
			consolidado.setAdjunto(entity.getAdjunto());
			consolidado.setVigencia(entity.getVigencia());
			consolidado.setNombreAjuste(entity.getNombreAjuste());
			consolidado.setEmergente(entity.getEmergente());
			consolidado.setFechaAplicacion(entity.getFechaAplicacion());
		}
		return consolidado;
	}
	public List<Consolidados> toVOs(List<ArchivoEntity> entities){
		List<Consolidados> lstConsolidados=null;
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
	
	public List<InfoProducto> cvsLectura(BufferedReader b) {
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