package pe.edu.upc.serviceImplement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.entities.Provincia;
import pe.edu.upc.repository.IProvinciaRepository;
import pe.edu.upc.serviceInterface.IProvinciaService;

@Service
public class ProvinciaServiceImpl implements IProvinciaService {

	@Autowired
	public IProvinciaRepository pR;

	@Override
	public int insert(Provincia provincia) {
		// TODO Auto-generated method stub
		int rpta = pR.buscarProvincia(provincia.getNameProvincia());

		if (rpta == 0) {
			pR.save(provincia);
		}
		return rpta;
	}

	@Override
	public List<Provincia> list() {
		// TODO Auto-generated method stub
		return pR.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Provincia listProvincia(int idProvincia) {
		// TODO Auto-generated method stub
		Optional<Provincia> op = pR.findById(idProvincia);
		return op.isPresent() ? op.get() : new Provincia();
	}

	@Override
	public List<Provincia> findByNameProvincia(String nombre) {
		// TODO Auto-generated method stub
		return pR.findByNameProvincia(nombre);
	}

	@Override
	public void delete(int idProvincia) {
		// TODO Auto-generated method stub
		pR.deleteById(idProvincia);
	}

}
