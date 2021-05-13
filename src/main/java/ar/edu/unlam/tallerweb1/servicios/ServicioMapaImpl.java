package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Medico;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("servicioMapa")
@Transactional
public class ServicioMapaImpl implements ServicioMapa{

    private RepositorioMedico medicoDAO;

    @Autowired
    public ServicioMapaImpl(RepositorioMedico medicoDAO){ this.medicoDAO = medicoDAO;}

    @Override
    public List<Medico> obtenerMedicosTodos()
    {
      return medicoDAO.obtenerTodos();
    }

}
