package co.za.zwideheights.ejb.cit;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import co.za.zwideheights.cit.param.GraveConstant;
import co.za.zwidehsights.jpa.entity.cit.TUser;

@Stateless(mappedName = "tUserServiceBean")
public class TUserServiceBean implements TUserService {

	private static final Logger LOGGER = Logger.getLogger(TUserServiceBean.class);

	@PersistenceContext(unitName = "citPU")
	private EntityManager em;

	@Override
	public TUser findById(Long id) {
		return em.find(TUser.class, id);
	}

	@Override
	public TUser login(String email, String password) {
		
		TypedQuery<TUser> query = em.createNamedQuery("TUser.findByPrincipal", TUser.class);
		query.setParameter("email", email);
		query.setParameter("password", password);
		TUser result = query.getSingleResult();
		return result;
		
	}
	
	@Override
	public TUser findByEmail(String email) {
		TypedQuery<TUser> query = em.createNamedQuery("TUser.findByEmail", TUser.class);
		query.setParameter("email", email);
		TUser result = query.getSingleResult();
		return result;
	}

	@Override
	public void register(TUser user) {
		em.merge(user);
	}

	@Override
	public TUser findByIdDate(Long id, Date createDate) {
		TypedQuery<TUser> query = em.createNamedQuery("TUser.findByIdCreateDate", TUser.class);
		query.setParameter("id", id);
		query.setParameter("createDate", createDate);
		TUser result = query.getSingleResult();
		return result;
	}

	@Override
	public List<TUser> find(Map<String, Object> params, Integer pageStartIndex, Integer pageSize) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(t.id) FROM tender4suredb.TUser t WHERE 1=1 ");

		for (String key : params.keySet()) {

			if (key.equals(GraveConstant.CREATE_DATE)) {
				sb.append(" AND t.create_date >= :createDate ");
			} else if (key.equals(GraveConstant.EMAIL)) {
				sb.append(" AND t.email <= :email ");
			} else if (key.equals(GraveConstant.NAME)) {
				sb.append(" AND t.name LIKE " + "%" + ":name" + "%");
			} else if (key.equals(GraveConstant.SURNAME)) {
				sb.append(" AND t.surname LIKE " + "%" + ":surname" + "%");
			}
		}

		Query query = em.createNativeQuery(sb.toString(), TUser.class);

		for (String key : params.keySet()) {

			if (key.equals(GraveConstant.CREATE_DATE)) {
				query.setParameter(GraveConstant.CREATE_DATE, (Calendar) params.get(key), TemporalType.DATE);
			} else if (key.equals(GraveConstant.EMAIL)) {
				query.setParameter(GraveConstant.EMAIL, params.get(key));
			} else if (key.equals(GraveConstant.NAME)) {
				query.setParameter(GraveConstant.NAME, params.get(key));
			} else if (key.equals(GraveConstant.SURNAME)) {
				query.setParameter(GraveConstant.SURNAME, params.get(key));
			}
		}

		query.setFirstResult(((pageStartIndex - 1) < 0 ? 0 : (pageStartIndex - 1)) * pageSize);
		query.setMaxResults(pageSize);

		List<TUser> results = (List<TUser>) query.getResultList();
		return results;
	}

	@Override
	public Long count(Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(t.id) FROM tender4suredb.TUser t WHERE 1=1 ");

		for (String key : params.keySet()) {

			if (key.equals(GraveConstant.CREATE_DATE)) {
				sb.append(" AND t.create_date >= :createDate ");
			} else if (key.equals(GraveConstant.EMAIL)) {
				sb.append(" AND t.email <= :email ");
			} else if (key.equals(GraveConstant.NAME)) {
				sb.append(" AND t.name LIKE " + "%" + ":name" + "%");
			} else if (key.equals(GraveConstant.SURNAME)) {
				sb.append(" AND t.surname LIKE " + "%" + ":surname" + "%");
			}
		}

		Query query = em.createNativeQuery(sb.toString(), TUser.class);

		for (String key : params.keySet()) {

			if (key.equals(GraveConstant.CREATE_DATE)) {
				query.setParameter(GraveConstant.CREATE_DATE, (Calendar) params.get(key), TemporalType.DATE);
			} else if (key.equals(GraveConstant.EMAIL)) {
				query.setParameter(GraveConstant.EMAIL, params.get(key));
			} else if (key.equals(GraveConstant.NAME)) {
				query.setParameter(GraveConstant.NAME, params.get(key));
			} else if (key.equals(GraveConstant.SURNAME)) {
				query.setParameter(GraveConstant.SURNAME, params.get(key));
			}
		}

		Long value = ((BigInteger) query.getSingleResult()).longValue();
		return value;
	}

	@Override
	public void save(TUser user) {
		em.merge(user);
	}

	@Override
	public void remove(Long id) {
		em.remove(id);
	}

	@Override
	public void merge(TUser user) {
		em.merge(user);
	}



}