package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Cita;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.nio.channels.ScatteringByteChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

    public List<Cita> obtenerCitasPorFecha(String email, Calendar fecha){
        final Session session = sessionFactory.getCurrentSession();

        Usuario medico = (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("rol", "Medico"))
                .uniqueResult();

        if (medico == null)
            return null;

        Criteria criteria = session.createCriteria(Cita.class)
                .add(Restrictions.eq("medico", medico))
                .add(Restrictions.eq("fecha", fecha))
                .add(Restrictions.sizeEq("historias", 1))
                .createCriteria("historias")
                .add(Restrictions.eq("observacion", "Creado"))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        List<Cita> citas = criteria.list();

        Calendar aux = Calendar.getInstance();
        for (Cita cita : citas ) {
            cita.getFecha().add(Calendar.DAY_OF_MONTH, 1);
            aux.setTime(cita.getHora());
            aux.add(Calendar.HOUR_OF_DAY, 3);
            cita.setHora(aux.getTime());
        }

        return citas;

    }

}

