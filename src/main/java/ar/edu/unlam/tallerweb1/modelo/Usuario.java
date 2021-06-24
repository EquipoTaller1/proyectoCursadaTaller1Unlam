package ar.edu.unlam.tallerweb1.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;


// Clase que modela el concepto de Usuario, la anotacion @Entity le avisa a hibernate que esta clase es persistible
// el paquete ar.edu.unlam.tallerweb1.modelo esta indicado en el archivo hibernateCOntext.xml para que hibernate
// busque entities en él
@Entity
public class Usuario {

	// La anotacion id indica que este atributo es el utilizado como clave primaria de la entity, se indica que el valor es autogenerado.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// para el resto de los atributo no se usan anotaciones entonces se usa el default de hibernate: la columna se llama igual que
	// el atributo, la misma admite nulos, y el tipo de dato se deduce del tipo de dato de java.
	private String email;
	private String password;
	@OneToOne()
	@JoinColumn(name = "persona_id")
	private Persona persona;

	@Column
	private String rol;

	@OneToOne(mappedBy = "usuario")
	private Ubicacion ubicacion;
	//@ManyToMany(mappedBy = "medicos")

	@ManyToMany
	@JoinTable(name="especialidad_medico",
			joinColumns=@JoinColumn(name="medico_id"),
			inverseJoinColumns=@JoinColumn(name="especialidad_id"))
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Especialidad> especialidades;

	@OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Cita> citasPaciente;

	public Usuario() {
		this.especialidades = new ArrayList<>();
		this.citasPaciente = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public List<Especialidad> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidad> especialidades) {
		this.especialidades = especialidades;
	}

	public List<Cita> getCitasPaciente() {
		return citasPaciente;
	}

	public void setCitasPaciente(List<Cita> citasPaciente) {
		this.citasPaciente = citasPaciente;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Usuario)) return false;
		Usuario usuario = (Usuario) o;
		return Objects.equals(getId(), usuario.getId()) &&
				Objects.equals(getEmail(), usuario.getEmail()) &&
				Objects.equals(getPassword(), usuario.getPassword()) &&
				Objects.equals(getRol(), usuario.getRol());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getEmail(), getPassword(), getRol());
	}
}
