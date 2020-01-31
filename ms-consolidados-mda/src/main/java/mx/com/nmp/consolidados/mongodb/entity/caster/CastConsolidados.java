package mx.com.nmp.consolidados.mongodb.entity.caster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.com.nmp.consolidados.model.ConsultarArchivoConsolidadoResInner;
import mx.com.nmp.consolidados.model.InfoProducto;
import mx.com.nmp.consolidados.mongodb.entity.ArchivoEntity;
//@Repository
public class CastConsolidados {
	public ConsultarArchivoConsolidadoResInner fillVoValues(ArchivoEntity entity) {
		ConsultarArchivoConsolidadoResInner consolidado = null;
		if (entity != null) {
			consolidado = new ConsultarArchivoConsolidadoResInner();
			consolidado.setIdArchivo(Integer.valueOf(entity.getIdArchivo().intValue()));
			consolidado.setFechaReporte(entity.getFechaAplicacion());
			consolidado.setNombreArchivo(entity.getNombreArchivo());
			consolidado.setIdPrioridad(entity.getPrioridad());
			consolidado.setNombreArchivo(entity.getNombreAjuste());
			String contentDoc = entity.getAdjunto();
			List<InfoProducto> lst = castJsonToList(contentDoc);
			consolidado.setProducto(lst);
		}
		return consolidado;
	}

	public ArrayList<ConsultarArchivoConsolidadoResInner> toVOs(List<ArchivoEntity> entities) {
		ArrayList<ConsultarArchivoConsolidadoResInner> lstConsolidados = null;

		if (entities != null && !entities.isEmpty()) {
			lstConsolidados = new ArrayList<>();
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
		List<InfoProducto> lst = new ArrayList<>();
		InfoProducto p;
		String aux = "idProducto";
		try {
			while ((line = b.readLine()) != null) {
				p = new InfoProducto();
				String[] csv = line.split(cvsSplitBy);
				if (!csv[0].equals(aux)) {
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

	public String lstToJson(List<InfoProducto> lst) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(lst);
		return json;
	}

	public static List<InfoProducto> castJsonToList(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		List<InfoProducto> participantJsonList = null;
		try {
			participantJsonList = mapper.readValue(jsonString, new TypeReference<List<InfoProducto>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return participantJsonList;
	}

	public static Date resetTimeToUp(Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public static Date resetTimeToDown(Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}
