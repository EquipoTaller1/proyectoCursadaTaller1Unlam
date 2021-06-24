package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioRegistroMedico;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.nio.channels.ScatteringByteChannel;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
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
    public Usuario obtenerMedicoPorEmail(String email){
        final Session session = sessionFactory.getCurrentSession();

        Usuario medico = (Usuario) session.createCriteria(Usuario.class)
                      //  .setFetchMode("especialidades", FetchMode.JOIN)
                        .add(Restrictions.eq("rol", "Medico"))
                        .add(Restrictions.eq("email", email))
                        .uniqueResult();

        return medico;
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

        Agenda lunes = new Agenda();
        lunes.setActivo(true);
        lunes.setHoraDesde(LocalTime.parse("08:00"));
        lunes.setHoraHasta(LocalTime.parse("16:00"));
        lunes.setDia("lunes");
        lunes.setMedico(usuario);
        session.save(lunes);

        Agenda martes = new Agenda();
        martes.setActivo(true);
        martes.setHoraDesde(LocalTime.parse("08:00"));
        martes.setHoraHasta(LocalTime.parse("16:00"));
        martes.setDia("martes");
        martes.setMedico(usuario);
        session.save(martes);

        Agenda miercoles = new Agenda();
        miercoles.setActivo(true);
        miercoles.setHoraDesde(LocalTime.parse("08:00"));
        miercoles.setHoraHasta(LocalTime.parse("16:00"));
        miercoles.setDia("miércoles");
        miercoles.setMedico(usuario);
        session.save(miercoles);

        Agenda jueves = new Agenda();
        jueves.setActivo(true);
        jueves.setHoraDesde(LocalTime.parse("08:00"));
        jueves.setHoraHasta(LocalTime.parse("16:00"));
        jueves.setDia("jueves");
        jueves.setMedico(usuario);
        session.save(jueves);

        Agenda viernes = new Agenda();
        viernes.setActivo(true);
        viernes.setHoraDesde(LocalTime.parse("08:00"));
        viernes.setHoraHasta(LocalTime.parse("16:00"));
        viernes.setDia("viernes");
        viernes.setMedico(usuario);
        session.save(viernes);

        Agenda sabado = new Agenda();
        sabado.setActivo(false);
        sabado.setHoraDesde(LocalTime.parse("08:00"));
        sabado.setHoraHasta(LocalTime.parse("16:00"));
        sabado.setDia("sábado");
        sabado.setMedico(usuario);
        session.save(sabado);

        Agenda domingo = new Agenda();
        domingo.setActivo(false);
        domingo.setHoraDesde(LocalTime.parse("08:00"));
        domingo.setHoraHasta(LocalTime.parse("16:00"));
        domingo.setDia("domingo");
        domingo.setMedico(usuario);
        session.save(domingo);
    }

    @Override
    public boolean addEspecialidad(Usuario medico, int especialidad){

        final Session session = sessionFactory.getCurrentSession();

        Long id_esp = new Long(especialidad);

        Especialidad especialidadNueva = (Especialidad) session.createCriteria(Especialidad.class)
                    .add(Restrictions.eq("id", id_esp))
                    .uniqueResult();

        for (Especialidad esp : medico.getEspecialidades()) {
            if (esp.getId().equals(especialidadNueva.getId()))
                return false; //Ya tenia esa especialidad
        }

        medico.getEspecialidades().add(especialidadNueva);
        sessionFactory.getCurrentSession().update(medico);

        return true;

    }

    @Override
    public boolean deleteEspecialidad(String emailMedico, long especialidad){

        Usuario medico = this.obtenerMedicoPorEmail(emailMedico);
        if (medico == null)
            return  false;

        final Session session = sessionFactory.getCurrentSession();

        Especialidad especialidadBorrar = (Especialidad) session.createCriteria(Especialidad.class)
                .add(Restrictions.eq("id", especialidad))
                .uniqueResult();

        for (Especialidad esp : medico.getEspecialidades()) {
            if (esp.getId().equals(especialidadBorrar.getId()))
            {
                medico.getEspecialidades().remove(especialidadBorrar);
                sessionFactory.getCurrentSession().update(medico);
                return true; //Se borró la especialidad
            }
        }
        return false;
    }

    @Override
    public List<Agenda> obtenerAgenda(String email) {
        final Session session = sessionFactory.getCurrentSession();
        Usuario medico = this.obtenerMedicoPorEmail(email);

        Criteria criteria = session.createCriteria(Agenda.class)
                .add(Restrictions.eq("medico", medico));

        return criteria.list();
    }

    @Override
    public void guardarAgenda(Agenda agenda) {
        final Session session = sessionFactory.getCurrentSession();
        session.save(agenda);
    }

    @Override
    public void actualizarAgenda(Agenda agenda) {
        final Session session = sessionFactory.getCurrentSession();
        session.update(agenda);
    }

    @Override
    public Agenda getDiaAgenda(Long medico, String dia) {
        final Session session = sessionFactory.getCurrentSession();

        Usuario userMedico = session.get(Usuario.class, medico);

        Criteria criteria = session.createCriteria(Agenda.class)
                .add(Restrictions.eq("dia", dia))
                .add(Restrictions.eq("medico", userMedico));

        return (Agenda) criteria.uniqueResult();
    }

    public List<Cita> obtenerCitasPorFecha(String email, LocalDate fecha){
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
                .addOrder(Order.asc("hora"))
                .add(Restrictions.sizeEq("historias", 1))
                .createCriteria("historias")
                .add(Restrictions.eq("observacion", "Creado"))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        return (List<Cita>) criteria.list();
    }

    public List<Cita> obtenerCitasPorFechaMedicoId(Long idMedico, LocalDate fecha){
        final Session session = sessionFactory.getCurrentSession();

        Usuario medico = session.get(Usuario.class, idMedico);

        if (medico == null)
            return null;

        Criteria criteria = session.createCriteria(Cita.class)
                .add(Restrictions.eq("medico", medico))
                .add(Restrictions.eq("fecha", fecha))
                .addOrder(Order.asc("hora"))
                .add(Restrictions.sizeEq("historias", 1))
                .createCriteria("historias")
                .add(Restrictions.eq("observacion", "Creado"))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        return (List<Cita>) criteria.list();
    }

}

