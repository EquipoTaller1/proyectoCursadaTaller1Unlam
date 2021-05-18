package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroPaciente;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioPacienteImpl implements RepositorioPaciente{

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPacienteImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void registrarPaciente(FormularioRegistroPaciente formularioRegistroPaciente, Persona persona) {
        final Session session = sessionFactory.getCurrentSession();
        Usuario usuario = new Usuario();
        usuario.setRol("Paciente");
        usuario.setEmail(formularioRegistroPaciente.getEmail());
        usuario.setPassword(new BCryptPasswordEncoder().encode(formularioRegistroPaciente.getPassword()));
        usuario.setPersona(persona);
        session.save(usuario);
    }
}
