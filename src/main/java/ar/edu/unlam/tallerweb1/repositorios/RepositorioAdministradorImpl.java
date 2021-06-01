package ar.edu.unlam.tallerweb1.repositorios;


import ar.edu.unlam.tallerweb1.Excepciones.AfiliadoNoExisteException;
import ar.edu.unlam.tallerweb1.Excepciones.FaltanDatosParaElRegistroException;
import ar.edu.unlam.tallerweb1.Excepciones.PersonaYaExisteException;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioPersona;
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

        FormularioPersona formularioPersona = new FormularioPersona();

        if (!formularioPersona.chequearFormulario(persona)) {
            throw new FaltanDatosParaElRegistroException();
        }

        if ((Persona) sessionFactory.getCurrentSession().createCriteria(Persona.class)
                .add(Restrictions.eq("email", persona.getEmail()))
                .uniqueResult() != null) {

            throw new PersonaYaExisteException();

        }
        sessionFactory.getCurrentSession().save(persona);
    }
}
