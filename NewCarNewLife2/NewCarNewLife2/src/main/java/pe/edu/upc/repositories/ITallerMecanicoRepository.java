package pe.edu.upc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entities.TallerMecanico;

@Repository
public interface ITallerMecanicoRepository extends JpaRepository<TallerMecanico, Integer> {

	@Query("select count(t.nameTaller) from TallerMecanico t where t.nameTaller=:name")
	public int buscarTallerMecanico(@Param("name") String nombre);

	List<TallerMecanico> findByNameTaller(String nameTaller);

	@Query(value =" SELECT D.name_distrito,count(t.id_taller_mecanico) "
			+ " from Distrito D "
			+ " join taller_mecanico t on t.Distrito = D.id_distrito "
			+ " group by D.name_distrito ",
			nativeQuery=true)
	public List<String[]> Reportnum();
}
