package ar.edu.unlam.tallerweb1.repositorios;


import ar.edu.unlam.tallerweb1.Excepciones.AfiliadoNoExisteException;
import ar.edu.unlam.tallerweb1.Excepciones.FaltanDatosParaElRegistroException;
import ar.edu.unlam.tallerweb1.Excepciones.PersonaYaExisteException;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioPersona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioPersonaMedico;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RepositorioAdministradorImpl implements RepositorioAdministrador {

    private SessionFactory sessionFactory;


    @Autowired
    public RepositorioAdministradorImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void registrar(Persona persona) throws FaltanDatosParaElRegistroException, PersonaYaExisteException, ParseException {

        chequearFormulario(persona);

        chequearSiLaPersonaYaExiste(persona);

        registrarPersona(persona);
    }

    @Override
    public void registrarMedico(Persona persona) {

        chequearFormularioMedico(persona);

        chequearSiLaPersonaMedicoYaExiste(persona);

        registrarPersona(persona);

    }

    private void chequearSiLaPersonaMedicoYaExiste(Persona persona) {
        if ((Persona) sessionFactory.getCurrentSession().createCriteria(Persona.class)
                .add(Restrictions.eq("matricula", persona.getMatricula()))
                .uniqueResult() != null) {

            throw new PersonaYaExisteException("La persona ya existe");
        }
    }

    private void registrarPersona(Persona persona) {
        sessionFactory.getCurrentSession().save(persona);
    }

    private void chequearSiLaPersonaYaExiste(Persona persona) {
        if ((Persona) sessionFactory.getCurrentSession().createCriteria(Persona.class)
                .add(Restrictions.eq("numeroAfiliado", persona.getNumeroAfiliado()))
                .uniqueResult() != null) {

            throw new PersonaYaExisteException("La persona ya existe");
        }
    }

    private void chequearFormulario(Persona persona) {
        FormularioPersona formularioPersona = new FormularioPersona();
        if (!formularioPersona.chequearFormulario(persona)) {
            throw new FaltanDatosParaElRegistroException("Faltan datos para el registro");
        }
    }

    private void chequearFormularioMedico(Persona persona) {
        FormularioPersonaMedico formularioPersonaMedico = new FormularioPersonaMedico();
        if (!formularioPersonaMedico.chequearFormularioMedico(persona)) {
            throw new FaltanDatosParaElRegistroException("Faltan datos para el registro");
        }
    }
}
