package pe.edu.upc.serviceInterface;

import java.util.List;

import pe.edu.upc.entities.Provincia;

public interface IProvinciaService {

	public int insert(Provincia provincia);

	List<Provincia> list();

	Provincia listProvincia(int idProvincia);

	List<Provincia> findByNameProvincia(String nombre);

	public void delete(int idProvincia);

}
