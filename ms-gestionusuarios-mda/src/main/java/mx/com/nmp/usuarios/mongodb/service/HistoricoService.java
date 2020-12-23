package mx.com.nmp.usuarios.mongodb.service;

import java.util.Date;

import mx.com.nmp.usuarios.model.ConsultaHistoricoRes;
import mx.com.nmp.usuarios.model.CrearHistoricoRes;
import mx.com.nmp.usuarios.model.ReqHistorico.AccionEnum;

public interface HistoricoService {
	CrearHistoricoRes crearHistorico(AccionEnum accion, Date fecha, Integer idPerfil, String usuario) throws Exception;
	ConsultaHistoricoRes getHistorico(Integer idUsuario) throws Exception;
}