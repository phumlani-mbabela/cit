package co.za.zwideheights.ejb.cit;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import co.za.zwidehsights.jpa.entity.cit.Grave;

@Local
public interface GraveService {
	
	public Grave findById(Long id) ;
	public List<Grave> find(Map<String, Object> params, Integer pageStartIndex, Integer pageSize) ;
	public Long count(Map<String, Object> params) ;
	public void save(Grave grave) ;
	public void merge(Grave grave) ;
	public void remove(Long id) ;
	public Grave findByGraveNumberNameSurname(String graveNumber, String name, String surname);

}