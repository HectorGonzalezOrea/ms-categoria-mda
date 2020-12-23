package mx.com.nmp.usuarios.mongodb.service;

import java.util.List;

import mx.com.nmp.usuarios.model.InfoUsuario;
import mx.com.nmp.usuarios.model.PerfilUsuario;
import mx.com.nmp.usuarios.mongodb.entity.UsuarioEntity;
import mx.com.nmp.usuarios.oag.vo.UsuarioVO;

public interface UsuarioMongoService {

	void addUser(UsuarioVO usuario) throws Exception ;
	void upsertUsers(List<UsuarioVO> usuarios, String grupo) throws Exception ;
	List<InfoUsuario> getAllUsers(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean estatus, Integer perfil, String usuario) throws Exception;
	Boolean actualizarPerfilUsuario(Integer idUsuario, Integer idPerfil) throws Exception;
	Boolean actualizarEstatusUsuario(Integer idUsuario, Boolean estatus) throws Exception;
	Boolean deleteUsuario(Integer idUsuario) throws Exception;
	
	PerfilUsuario consultaUsuarioPerfil(String usuario) throws Exception;
	
	Boolean consultarUsuario(String usuario) throws Exception;
	UsuarioEntity consultarUsuario(Integer usuario) throws Exception;
}
