package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Excepciones.ErrorEnFormatoDeFechaException;
import ar.edu.unlam.tallerweb1.Excepciones.FaltanDatosParaElRegistroException;
import ar.edu.unlam.tallerweb1.Excepciones.PersonaYaExisteException;
import ar.edu.unlam.tallerweb1.configuraciones.SendEmail;
import ar.edu.unlam.tallerweb1.modelo.Persona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioPersona;
import ar.edu.unlam.tallerweb1.modelo.formularios.FormularioPersonaMedico;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAdministrador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;


@Service
public class ServicioAdministradorImpl implements ServicioAdministrador {

    private RepositorioAdministrador repositorioAdministrador;
    private final String urlRegistroPaciente = "http://localhost:8080/proyecto_limpio_spring_war_exploded/registro";


    @Autowired
    public ServicioAdministradorImpl(RepositorioAdministrador _repositorioPersona) {
        this.repositorioAdministrador = _repositorioPersona;
    }


    @Override
    public void registrar(Persona persona) throws FaltanDatosParaElRegistroException, PersonaYaExisteException, ParseException {

        chequearDatosFormulario(persona);

        repositorioAdministrador.registrar(persona);


    }

    @Override
    public void registrarMedico(Persona nuevaPersona) throws ParseException {
        chequearDatosFomularioMedico(nuevaPersona);
        repositorioAdministrador.registrarMedico(nuevaPersona);
    }

    private void chequearDatosFomularioMedico(Persona persona) {

        FormularioPersonaMedico formularioPersona = new FormularioPersonaMedico();

        if (!formularioPersona.chequearFormularioMedico(persona)) {
            throw new FaltanDatosParaElRegistroException();
        }

        chequearFecha(persona.getFechaNacimiento());
    }

    private void chequearDatosFormulario(Persona persona) {

        FormularioPersona formularioPersona = new FormularioPersona();

        if (!formularioPersona.chequearFormulario(persona)) {
            throw new FaltanDatosParaElRegistroException();
        }

        chequearFecha(persona.getFechaNacimiento());
    }

    @Override
    public boolean enviarEmailDeRegistro(FormularioPersona formulario) {

        boolean seEnvioCorrectamente = false;

        if (formulario.chequearFormulario(formulario)) {
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

    @Override
    public void chequearFecha(String fecha) {

        if (!isLegalDate(fecha)) {
            throw new ErrorEnFormatoDeFechaException();
        }

    }



    boolean isLegalDate(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        return sdf.parse(s, new ParsePosition(0)) != null;
    }
}
