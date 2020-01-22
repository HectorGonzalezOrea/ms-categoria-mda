package mx.com.nmp.usuarios.oag.vo;

import java.util.ArrayList;

public class IdentidadUsuarioResponseVO {

	private ArrayList<UsuarioVO> usuario;

	public ArrayList<UsuarioVO> getUsuario() {
		return usuario;
	}

	public void setUsuario(ArrayList<UsuarioVO> usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "IdentidadUsuarioResponseVO [usuario=" + usuario + "]";
	}

}
