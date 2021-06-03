package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioUserDetaillsService implements UserDetailsService {

    private ServicioRegistroUsuario servicioRegistroUsuario;

    @Autowired
    public ServicioUserDetaillsService(ServicioRegistroUsuario servicioRegistroUsuario){
        this.servicioRegistroUsuario = servicioRegistroUsuario;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = servicioRegistroUsuario.consultarUsuarioEmail(username);

        if (usuario != null){

            List<SimpleGrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));

            return new User(usuario.getEmail(), usuario.getPassword(), roles);
        }
        else {
            throw new UsernameNotFoundException("No se encontro usuario");
        }
    }
}
