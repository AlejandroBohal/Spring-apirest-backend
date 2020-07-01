package edu.eci.apirest.backend.clientes.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author AlejandroB
 *
 */
@Entity
@Table(name="clientes")

public class Cliente implements Serializable{
	/**
	 * Customer entity, mapped from database.
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@NotEmpty(message = "No debe estar vacio.")
	@Size(min=4,max=25, message = "El tamaño tiene que estar entre 4 y 25")
	@Column(nullable=false,length=100)
	private String nombre;

	@NotEmpty(message ="No debe estar vacio.")
	@Column(nullable=false,length=100)
	private String apellido;

	@NotEmpty(message = "No debe estar vacio.")
	@Email(message = "No es un correo bien formado.")
	@Column(nullable=false,unique=true,length=100)
	private String email;

	@NotNull(message= "No debe estar vacia")
	@Column(name="create_at",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date createAt;

	private String foto;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
}
