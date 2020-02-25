package mx.com.nmp.consolidados.oag.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "arb:validarArbitrajePreciosPartidasRequest")
@XmlAccessorType (XmlAccessType.FIELD)
public class ValidarArbitrajePreciosPartidasRequest {
	@XmlElement(name="tip:partida")
	private Partida partida;

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}
}
