package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.Excepciones.AfiliadoNoExisteException;
import ar.edu.unlam.tallerweb1.Excepciones.PersonaYaExisteException;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Persona consultarAfiliado(String numeroAfiliado) {

        Persona personaBuscada = (Persona) sessionFactory.getCurrentSession().createCriteria(Persona.class)
                .add(Restrictions.eq("numeroAfiliado", numeroAfiliado))
                .uniqueResult();

        if (personaBuscada == null) {
            throw new AfiliadoNoExisteException("El afiliado no existe");
        }

        return personaBuscada;

    }

    @Override
    public void registrar(Persona persona) {


        if ((Persona) sessionFactory.getCurrentSession().createCriteria(Persona.class)
                .add(Restrictions.eq("id", persona.getId()))
                .uniqueResult() != null) {

            throw new PersonaYaExisteException();

        }
        sessionFactory.getCurrentSession().save(persona);

    }
}
