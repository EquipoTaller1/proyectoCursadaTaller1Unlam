package ar.edu.unlam.tallerweb1;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import ar.edu.unlam.tallerweb1.cajaFuerte.CajaFuerte;

public class CajaFuerteTest {

	private CajaFuerte cajaFuerte;
	
	private int prueba;

	@Test
	public void alCrearUnaCajaFuerteDeberiaEstarAbierta() {

		whenCreoUnaCajaFuerte();

		thenLaCajaFuerteEstaAbierta();

	}

	@Test
	public void alCerrarLaCajaDeberiaEstarCerrada() {

		cajaFuerte = givenCajaFuerte();

		whenCierroLaCajaFuerte(cajaFuerte);

		thenLaCajaDeberiaEstarCerrada(cajaFuerte);

	}

	@Test
	public void alCerrarLaCajaSeAbreConElCodigoQueLePuse() {

		cajaFuerte = givenCajaFuerte();

		whenCierroLaCajaFuertePoniendoleUnCodigo(cajaFuerte, 2222);

		thenLaCajaSeAbreConElcodigoQueLePuse(cajaFuerte, 2222);

	}

	@Test
	public void alAbrirCajaConCodigoIncorrectoNoSeAbre() {

		cajaFuerte = givenCajaFuerte();

		int codigoCorrecto = 123;

		int codigoIncorrecto = 9828282;

		whenCierroLaCajaFuertePoniendoleUnCodigo(cajaFuerte, codigoCorrecto);

		whenIntentoAbrirConCodigoIncorrecto(cajaFuerte, codigoIncorrecto);

		thenLaCajaNoSeAbre();

	}

	private void thenLaCajaNoSeAbre() {

		assertThat(cajaFuerte.estaAbierta()).isFalse();

	}

	private void whenIntentoAbrirConCodigoIncorrecto(CajaFuerte cajaFuerte, int codigoIncorrecto) {

		cajaFuerte.abrirConCodigo(codigoIncorrecto);

	}

	private void thenLaCajaSeAbreConElcodigoQueLePuse(CajaFuerte cajaFuerte, int codigo) {

		cajaFuerte.abrirConCodigo(codigo);
		assertThat(cajaFuerte.estaAbierta()).isTrue();

	}

	private void whenCierroLaCajaFuertePoniendoleUnCodigo(CajaFuerte cajaFuerte, int codigo) {

		cajaFuerte.ponerCodigo(codigo);
		cajaFuerte.cerrarCaja();
	}

	private void thenLaCajaDeberiaEstarCerrada(CajaFuerte cajaFuerte) {
		assertThat(cajaFuerte.estaAbierta()).isFalse();

	}

	private void whenCierroLaCajaFuerte(CajaFuerte cajaFuerte) {

		cajaFuerte.cerrarCaja();

	}

	private CajaFuerte givenCajaFuerte() {

		return new CajaFuerte();

	}

	// este metodo no retorna nada pero sin embargo parecieria que el metodo del
	// test esta recibiendo una caja fuerte.
	private void whenCreoUnaCajaFuerte() {
		cajaFuerte = new CajaFuerte();
	}

	private void thenLaCajaFuerteEstaAbierta() {
		assertThat(cajaFuerte.estaAbierta()).isTrue();

	}

}
