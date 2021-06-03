package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("repositorioMedico")
@Transactional
public class RepositorioMedicoImpl implements RepositorioMedico {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioMedicoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Usuario> obtenerTodos()
    {
        final Session session = sessionFactory.getCurrentSession();

        return (List<Usuario>) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("rol", "Medico"))
                .list();
    }

    @Override
    public Persona consultarMedico(String matricula) {
        Persona personaBuscada = (Persona) sessionFactory.getCurrentSession().createCriteria(Persona.class)
                .add(Restrictions.eq("matricula", matricula))
                .uniqueResult();

        return personaBuscada;
    }

    @Override
    public void registrarMedico(FormularioRegistroMedico formularioRegistroPaciente, Persona persona) {
        final Session session = sessionFactory.getCurrentSession();
        Usuario usuario = new Usuario();
        usuario.setRol("Medico");
        usuario.setEmail(formularioRegistroPaciente.getEmail());
        usuario.setPassword(new BCryptPasswordEncoder().encode(formularioRegistroPaciente.getPassword()));
        usuario.setPersona(persona);
        session.save(usuario);
    }

}

