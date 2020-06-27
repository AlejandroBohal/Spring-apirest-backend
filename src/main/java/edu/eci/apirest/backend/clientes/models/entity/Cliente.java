package edu.eci.apirest.backend.clientes.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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

	@NotEmpty
	@Size(min=4,max=25)
	@Column(nullable=false,length=100)
	private String nombre;

	@NotEmpty
	@Column(nullable=false,length=100)
	private String apellido;

	@NotEmpty
	@Email
	@Column(nullable=false,unique=true,length=100)
	private String email;

	@Column(name="create_at",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	public long getId() {
		return id;
	}
	@PrePersist
	public void prePersist() {
		createAt = new Date();
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

}
