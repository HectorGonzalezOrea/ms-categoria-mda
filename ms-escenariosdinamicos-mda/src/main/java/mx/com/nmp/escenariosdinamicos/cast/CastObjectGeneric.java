package mx.com.nmp.escenariosdinamicos.cast;

import java.io.IOException;
import org.springframework.stereotype.Repository;
import mx.com.nmp.escenariosdinamicos.oag.vo.IndexGarantiaVO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class CastObjectGeneric {
	public IndexGarantiaVO JsonFieldToObject(String jsonField) {
		ObjectMapper mapper = new ObjectMapper();
		IndexGarantiaVO participantJsonList = null;
		try {
			participantJsonList = mapper.readValue(jsonField,IndexGarantiaVO.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return participantJsonList;
	}
}
