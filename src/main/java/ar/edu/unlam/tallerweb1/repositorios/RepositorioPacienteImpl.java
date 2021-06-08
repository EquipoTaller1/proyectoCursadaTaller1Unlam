package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.DatosRegistroPaciente;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class RepositorioPacienteImpl implements RepositorioPaciente{

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPacienteImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void registrarPaciente(DatosRegistroPaciente datosRegistroPaciente, Persona persona) {
        final Session session = sessionFactory.getCurrentSession();
        Usuario usuario = new Usuario();
        usuario.setRol("Paciente");
        usuario.setEmail(datosRegistroPaciente.getEmail());
        usuario.setPassword(new BCryptPasswordEncoder().encode(datosRegistroPaciente.getPassword()));
        usuario.setPersona(persona);
        session.save(usuario);
    }

    @Override
    public Persona consultarAfiliado(String numeroAfiliado) {
        Persona personaBuscada = (Persona) sessionFactory.getCurrentSession().createCriteria(Persona.class)
                .add(Restrictions.eq("numeroAfiliado", numeroAfiliado))
                .uniqueResult();

        return personaBuscada;
    }

}
