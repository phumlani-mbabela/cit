package co.za.zwideheights.ejb.cit;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import co.za.zwidehsights.jpa.entity.cit.TUser;

@Local
public interface TUserService {
	
	public TUser findById(Long id) ;
	public TUser findByEmail(String email) ;
	public TUser login(String email, String password) ;
	public void register(TUser user) ;
	public TUser findByIdDate(Long id, Date createDate) ;
	public List<TUser> find(Map<String, Object> params, Integer pageStartIndex, Integer pageSize) ;
	public Long count(Map<String, Object> params) ;
	public void save(TUser user) ;
	public void merge(TUser user) ;
	public void remove(Long id) ;

}