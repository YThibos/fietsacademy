package be.vdab.services;

import java.util.List;

import be.vdab.entities.Campus;
import be.vdab.repositories.CampusRepository;

public class CampusService extends AbstractService {

	private final CampusRepository campusRepository = new CampusRepository();
	
	public List<Campus> findByGemeente(String gemeente) {
		return campusRepository.findByGemeente(gemeente);
	}
	
	public List<Campus> findAll() {
		return campusRepository.findAll();
	}
	
	public Campus read(long id) {
		return campusRepository.read(id);
	}
	
}
