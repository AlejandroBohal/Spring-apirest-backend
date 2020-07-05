package edu.eci.apirest.backend.clientes.models.persistence;
import edu.eci.apirest.backend.clientes.models.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.eci.apirest.backend.clientes.models.entity.Cliente;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IClienteDAO extends JpaRepository<Cliente,Long> {
    @Query("from Region")
    public List<Region> findAllRegiones();
}
