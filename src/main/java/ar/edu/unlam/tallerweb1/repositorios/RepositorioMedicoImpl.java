package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Medico;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioMedico")
public class RepositorioMedicoImpl implements RepositorioMedico {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioMedicoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Medico> obtenerTodos()
    {
        final Session session = sessionFactory.getCurrentSession();

        return (List<Medico>) session.createCriteria(Medico.class)
                        .add(Restrictions.isNotNull("id")).list();
    }

}

