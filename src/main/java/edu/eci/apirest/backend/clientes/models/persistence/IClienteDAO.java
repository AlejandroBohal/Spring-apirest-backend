package edu.eci.apirest.backend.clientes.models.persistence;
import org.springframework.data.repository.CrudRepository;
import edu.eci.apirest.backend.clientes.models.entity.Cliente;
public interface IClienteDAO extends CrudRepository<Cliente,Long>{
	
}
