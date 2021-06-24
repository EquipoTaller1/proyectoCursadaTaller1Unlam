package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class RepositorioCitaImpl implements RepositorioCita {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioCitaImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Especialidad> allEspecialidad() {

        return sessionFactory.getCurrentSession().createCriteria(Especialidad.class)
                .list();
    }

    @Override
    public List<Cita> obtenerCitas(String email) {
        final Session session = sessionFactory.getCurrentSession();

        Usuario paciente = this.userByEmail(email);

        Criteria criteria = session.createCriteria(Cita.class)
                .add(Restrictions.eq("paciente", paciente))
                .addOrder(Order.desc("created_at"));

        return criteria.list();
    }

    @Override
    public List<Cita> obtenerCitasProximas(String email) {
        final Session session = sessionFactory.getCurrentSession();

        Usuario paciente = this.userByEmail(email);

        Criteria criteria = session.createCriteria(Cita.class)
                .add(Restrictions.eq("paciente", paciente))
                .add(Restrictions.sizeEq("historias", 1))
                .addOrder(Order.desc("created_at"))
                .createCriteria("historias")
                .add(Restrictions.eq("observacion", "Creado"))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        return criteria.list();
    }

    @Override
    public Cita guardarCita(LocalDate fecha, LocalTime hora, Especialidad especialidad, TipoCita tipoCita, Usuario medico, Usuario paciente) {
        Cita cita = new Cita();
        cita.setHora(hora);
        cita.setFecha(fecha);
        cita.setEspecialidad(especialidad);
        cita.setTipoCita(tipoCita);
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setCreated_at(LocalDateTime.now());

        CitaHistoria citaHistoria = new CitaHistoria();
        Estado creado = sessionFactory.getCurrentSession().get(Estado.class, Long.valueOf(1));
        citaHistoria.setEstado(creado);
        citaHistoria.setCita(cita);
        citaHistoria.setObservacion("Creado");
        citaHistoria.setCreated_at(Calendar.getInstance());

        cita.agregarHistoria(citaHistoria);
        sessionFactory.getCurrentSession().save(cita);
        sessionFactory.getCurrentSession().save(citaHistoria);

        return cita;
    }

    @Override
    public Cita obtenerCita(Long idCita) {
        return (Cita) sessionFactory.getCurrentSession().createCriteria(Cita.class)
                .add(Restrictions.eq("id", idCita))
                .uniqueResult();
    }

    @Override
    public List<Usuario> medicoByEspecialidad(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Usuario.class)
                .createCriteria("especialidades")
                .add(Restrictions.eq("id", id))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        return criteria.list();
    }

    @Override
    public Especialidad especialidadById(Long id) {
        return sessionFactory.getCurrentSession().get(Especialidad.class, id);
    }

    @Override
    public Usuario userById(Long id) {
        return sessionFactory.getCurrentSession().get(Usuario.class, id);
    }

    @Override
    public TipoCita tipoCitaById(Long id) {
        return sessionFactory.getCurrentSession().get(TipoCita.class, id);
    }

    @Override
    public Usuario userByEmail(String email) {
        final Session session = sessionFactory.getCurrentSession();
        return (Usuario) session.createCriteria(Usuario.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }
}
