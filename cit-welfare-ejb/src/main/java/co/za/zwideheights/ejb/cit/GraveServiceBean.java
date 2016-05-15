package co.za.zwideheights.ejb.cit;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import co.za.zwideheights.cit.param.GraveConstant;
import co.za.zwidehsights.jpa.entity.cit.Grave;

@Stateless(mappedName = "graveServiceBean")
public class GraveServiceBean implements GraveService {

	private static final Logger LOGGER = Logger.getLogger(GraveServiceBean.class);

	@PersistenceContext(unitName = "citPU")
	private EntityManager em;

	@Override
	public Grave findById(Long id) {
		return em.find(Grave.class, id);
	}

	@Override
	public List<Grave> find(Map<String, Object> params, Integer pageStartIndex, Integer pageSize) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(g.id) FROM citdb.Grave g WHERE 1=1 ");
		
		for (String key : params.keySet()) {

			if (key.equals(GraveConstant.GRAVENUMBER)) {
				sb.append(" AND g.graveNumber LIKE " + "%" + ":graveNumber" + "%");
			} else if (key.equals(GraveConstant.SEX)) {
				sb.append(" AND g.sex = :sex ");
			} else if (key.equals(GraveConstant.AGE)) {
				sb.append(" AND g.age LIKE " + "%" + ":age" + "%");
			} else if (key.equals(GraveConstant.SURNAME)) {
				sb.append(" AND g.surname LIKE " + "%" + ":surname" + "%");
			} else if (key.equals(GraveConstant.NAME)) {
				sb.append(" AND g.name LIKE " + "%" + ":name" + "%");
			} else if (key.equals(GraveConstant.ADDRESS)) {
				sb.append(" AND g.address LIKE " + "%" + ":address" + "%");
			} else if (key.equals(GraveConstant.BURIALORDERNUMBER)) {
				sb.append(" AND g.burialOrderNumber LIKE " + "%" + ":burialOrderNumber" + "%");
			} else if (key.equals(GraveConstant.ISSUEDAT)) {
				sb.append(" AND g.issuedAt LIKE " + "%" + ":issuedAt" + "%");
			} else if (key.equals(GraveConstant.CITYCOUNCILREGNO)) {
				sb.append(" AND g.cityCouncilRegNo LIKE " + "%" + ":cityCouncilRegNo" + "%");
			} else if (key.equals(GraveConstant.CITDEBITNOTENUMBER)) {
				sb.append(" AND g.citDebitNoteNumber LIKE " + "%" + ":citDebitNoteNumber" + "%");
			} else if (key.equals(GraveConstant.RECIEPTNO)) {
				sb.append(" AND g.recieptNo LIKE " + "%" + ":recieptNo" + "%");
			} else if (key.equals(GraveConstant.DATEOFDEATH)) {
				sb.append(" AND g.dateOfDeath >= :dateOfDeath ");
			}
			
		}

		Query query = em.createNativeQuery(sb.toString(), Grave.class);

		for (String key : params.keySet()) {

			if (key.equals(GraveConstant.DATEOFDEATH)) {
				query.setParameter(GraveConstant.DATEOFDEATH, (Calendar) params.get(key), TemporalType.DATE);
			} else if (key.equals(GraveConstant.RECIEPTNO)) {
				query.setParameter(GraveConstant.RECIEPTNO, params.get(key));
			} else if (key.equals(GraveConstant.CITDEBITNOTENUMBER)) {
				query.setParameter(GraveConstant.CITDEBITNOTENUMBER, params.get(key));
			} else if (key.equals(GraveConstant.CITYCOUNCILREGNO)) {
				query.setParameter(GraveConstant.CITYCOUNCILREGNO, params.get(key));
			} else if (key.equals(GraveConstant.ISSUEDAT)) {
				query.setParameter(GraveConstant.ISSUEDAT, params.get(key));
			} else if (key.equals(GraveConstant.BURIALORDERNUMBER)) {
				query.setParameter(GraveConstant.BURIALORDERNUMBER, params.get(key));
			} else if (key.equals(GraveConstant.ADDRESS)) {
				query.setParameter(GraveConstant.ADDRESS, params.get(key));
			} else if (key.equals(GraveConstant.NAME)) {
				query.setParameter(GraveConstant.NAME, params.get(key));
			} else if (key.equals(GraveConstant.SURNAME)) {
				query.setParameter(GraveConstant.SURNAME, params.get(key));
			} else if (key.equals(GraveConstant.GRAVENUMBER)) {
				query.setParameter(GraveConstant.GRAVENUMBER, params.get(key));
			} else if (key.equals(GraveConstant.SEX)) {
				query.setParameter(GraveConstant.SEX, params.get(key));
			} else if (key.equals(GraveConstant.AGE)) {
				query.setParameter(GraveConstant.AGE, params.get(key));
			}
		}

		query.setFirstResult(((pageStartIndex - 1) < 0 ? 0 : (pageStartIndex - 1)) * pageSize);
		query.setMaxResults(pageSize);

		List<Grave> results = (List<Grave>) query.getResultList();
		return results;
	}

	@Override
	public Long count(Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(*) FROM citdb.Grave g WHERE 1=1 ");

		for (String key : params.keySet()) {

			if (key.equals(GraveConstant.GRAVENUMBER)) {
				sb.append(" AND g.graveNumber LIKE " + "%" + ":graveNumber" + "%");
			} else if (key.equals(GraveConstant.SEX)) {
				sb.append(" AND g.sex = :sex ");
			} else if (key.equals(GraveConstant.AGE)) {
				sb.append(" AND g.age LIKE " + "%" + ":age" + "%");
			} else if (key.equals(GraveConstant.SURNAME)) {
				sb.append(" AND g.surname LIKE " + "%" + ":surname" + "%");
			} else if (key.equals(GraveConstant.NAME)) {
				sb.append(" AND g.name LIKE " + "%" + ":name" + "%");
			} else if (key.equals(GraveConstant.ADDRESS)) {
				sb.append(" AND g.address LIKE " + "%" + ":address" + "%");
			} else if (key.equals(GraveConstant.BURIALORDERNUMBER)) {
				sb.append(" AND g.burialOrderNumber LIKE " + "%" + ":burialOrderNumber" + "%");
			} else if (key.equals(GraveConstant.ISSUEDAT)) {
				sb.append(" AND g.issuedAt LIKE " + "%" + ":issuedAt" + "%");
			} else if (key.equals(GraveConstant.CITYCOUNCILREGNO)) {
				sb.append(" AND g.cityCouncilRegNo LIKE " + "%" + ":cityCouncilRegNo" + "%");
			} else if (key.equals(GraveConstant.CITDEBITNOTENUMBER)) {
				sb.append(" AND g.citDebitNoteNumber LIKE " + "%" + ":citDebitNoteNumber" + "%");
			} else if (key.equals(GraveConstant.RECIEPTNO)) {
				sb.append(" AND g.recieptNo LIKE " + "%" + ":recieptNo" + "%");
			} else if (key.equals(GraveConstant.DATEOFDEATH)) {
				sb.append(" AND g.dateOfDeath >= :dateOfDeath ");
			}
		}

		Query query = em.createNativeQuery(sb.toString(), Grave.class);

		for (String key : params.keySet()) {

			if (key.equals(GraveConstant.DATEOFDEATH)) {
				query.setParameter(GraveConstant.DATEOFDEATH, (Calendar) params.get(key), TemporalType.DATE);
			} else if (key.equals(GraveConstant.RECIEPTNO)) {
				query.setParameter(GraveConstant.RECIEPTNO, params.get(key));
			} else if (key.equals(GraveConstant.CITDEBITNOTENUMBER)) {
				query.setParameter(GraveConstant.CITDEBITNOTENUMBER, params.get(key));
			} else if (key.equals(GraveConstant.CITYCOUNCILREGNO)) {
				query.setParameter(GraveConstant.CITYCOUNCILREGNO, params.get(key));
			} else if (key.equals(GraveConstant.ISSUEDAT)) {
				query.setParameter(GraveConstant.ISSUEDAT, params.get(key));
			} else if (key.equals(GraveConstant.BURIALORDERNUMBER)) {
				query.setParameter(GraveConstant.BURIALORDERNUMBER, params.get(key));
			} else if (key.equals(GraveConstant.ADDRESS)) {
				query.setParameter(GraveConstant.ADDRESS, params.get(key));
			} else if (key.equals(GraveConstant.NAME)) {
				query.setParameter(GraveConstant.NAME, params.get(key));
			} else if (key.equals(GraveConstant.SURNAME)) {
				query.setParameter(GraveConstant.SURNAME, params.get(key));
			} else if (key.equals(GraveConstant.GRAVENUMBER)) {
				query.setParameter(GraveConstant.GRAVENUMBER, params.get(key));
			} else if (key.equals(GraveConstant.SEX)) {
				query.setParameter(GraveConstant.SEX, params.get(key));
			} else if (key.equals(GraveConstant.AGE)) {
				query.setParameter(GraveConstant.AGE, params.get(key));
			}
		}

		Long value = ((BigInteger) query.getSingleResult()).longValue();
		return value;
	}

	@Override
	public void save(Grave grave) {
		em.merge(grave);
	}

	@Override
	public void merge(Grave grave) {
		em.merge(grave);
	}

	@Override
	public void remove(Long id) {
		em.remove(id);
	}

	@Override
	public Grave findByGraveNumberNameSurname(String graveNumber, String name, String surname) {
		// TODO Auto-generated method stub
		return null;
	}

}