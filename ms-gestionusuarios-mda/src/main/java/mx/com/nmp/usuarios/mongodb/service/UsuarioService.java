package mx.com.nmp.usuarios.mongodb.service;

import java.util.List;

import mx.com.nmp.usuarios.model.InfoUsuario;
import mx.com.nmp.usuarios.model.PerfilUsuario;
import mx.com.nmp.usuarios.mongodb.entity.UsuarioEntity;
import mx.com.nmp.usuarios.oag.vo.UsuarioVO;

public interface UsuarioService {

	void addUser(UsuarioVO usuario) throws Exception ;
	void upsertUsers(List<UsuarioVO> usuarios, String grupo) throws Exception ;
	List<InfoUsuario> getAllUsers(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean estatus, Integer perfil, String usuario, List<String> grupos) throws Exception;
	Boolean actualizarPerfilUsuario(Integer idUsuario, Integer idPerfil, List<String> grupos) throws Exception;
	Boolean actualizarEstatusUsuario(Integer idUsuario, Boolean estatus, List<String> grupos) throws Exception;
	Boolean deleteUsuario(Integer idUsuario, List<String> grupos) throws Exception;
	
	PerfilUsuario consultaUsuarioPerfil(String usuario) throws Exception;
	PerfilUsuario consultaUsuarioPerfil(Integer usuario, List<String> grupos) throws Exception;
	PerfilUsuario consultaUsuarioPerfil(Integer usuario) throws Exception;
	
	Boolean consultarUsuario(String usuario) throws Exception;
	UsuarioEntity consultarUsuario(Integer usuario) throws Exception;
	UsuarioEntity consultarUsuarios(String usuario) throws Exception;
}
