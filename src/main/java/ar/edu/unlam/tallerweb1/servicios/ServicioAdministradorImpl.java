package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.FaltanDatosParaElRegistroException;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioAdministradorImpl implements ServicioAdministrador {

    private RepositorioAdministrador _repositorioPersona;

    @Autowired
    public ServicioAdministradorImpl(RepositorioAdministrador _repositorioPersona) {
        this._repositorioPersona = _repositorioPersona;
    }


    @Override
    public void registrar(Persona persona) {

        if (persona.getNombre() == null ||
                persona.getApellido() == null ||
                persona.getFechaNacimiento() == null ||
                persona.getNumeroAfiliado() == null ||
                persona.getNumeroDocumento() == null ||
                persona.getTipoDocumento() == null) {
            throw new FaltanDatosParaElRegistroException();
        }


        _repositorioPersona.registrar(persona);

    }
}
