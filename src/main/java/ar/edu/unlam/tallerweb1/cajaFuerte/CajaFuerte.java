package ar.edu.unlam.tallerweb1.cajaFuerte;

public class CajaFuerte {

	private boolean estaAbierto;

	private int codigoSeteado;

	public CajaFuerte() {
		this.estaAbierto = true;
	}

	public boolean estaAbierta() {

		return this.estaAbierto;
	}

	public void cerrarCaja() {

		this.estaAbierto = false;

	}

	public void ponerCodigo(int codigo) {

		this.codigoSeteado = codigo;

	}

	public int getCodigo() {
		return codigoSeteado;
	}

	public void abrirConCodigo(int codigo) {

		if (codigo == this.codigoSeteado) {
			this.estaAbierto = true;
		}

	}

}
