package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.FaltanDatosParaElRegistroException;
import ar.edu.unlam.tallerweb1.configuraciones.SendEmail;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioPersona;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioAdministradorImpl implements ServicioAdministrador {

    private RepositorioAdministrador _repositorioPersona;
    private final String urlRegistroPaciente = "http://localhost:8080/proyecto_limpio_spring_war_exploded/registro";

    @Autowired
    public ServicioAdministradorImpl(RepositorioAdministrador _repositorioPersona) {
        this._repositorioPersona = _repositorioPersona;
    }


    @Override
    public void registrar(Persona persona) {

        if (!persona.chequearPersona(persona)){
            throw new FaltanDatosParaElRegistroException();
        }

        _repositorioPersona.registrar(persona);
    }

    @Override
    public boolean enviarEmailDeRegistro(FormularioPersona formulario) {

        boolean seEnvioCorrectamente = false;

        if (formulario.chequearFormulario(formulario)){
            String subject = "Registro exitoso La clinica";

            String cuerpoDelEmail = "Le informamos que su numero de afiliado es: " + formulario.getNumeroAfiliado() +
                                    " Por favor utilicelo para darse de alta como usuario en el siguiente link: " + urlRegistroPaciente;

            String email = formulario.getEmail();
            SendEmail sendEmail = new SendEmail();
            sendEmail.SendSimpleEmail(subject, cuerpoDelEmail, email);
            seEnvioCorrectamente = true;
        }

        return seEnvioCorrectamente;

    }
}
