package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

// Interface que define los metodos del Repositorio de Usuarios.
public interface RepositorioRegistroUsuario {
	
	Usuario consultarUsuario (Usuario usuario);

	Usuario userByEmail (String email);

	void createUser(Usuario usuario);

	Usuario consultarPorId(Long id);
}
