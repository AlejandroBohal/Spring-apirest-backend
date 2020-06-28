package edu.eci.apirest.backend.clientes.models.persistence;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.eci.apirest.backend.clientes.models.entity.Cliente;
public interface IClienteDAO extends JpaRepository<Cliente,Long> {
	
}
