package it.drwolf.impaqts.configurator.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import it.drwolf.impaqts.configurator.exceptions.EntityAlreadyPersistedException;
import it.drwolf.impaqts.configurator.model.Metadatum;
import it.drwolf.impaqts.configurator.model.MetadatumValue;

public class MetadatumValueDAO {

	private final MetadatumDAO metadatumDAO;

	@Inject
	public MetadatumValueDAO(MetadatumDAO metadatumDAO) {
		this.metadatumDAO = metadatumDAO;
	}

	public MetadatumValue create(MetadatumValue metadatumValue, Long metadatumId, EntityManager em)
			throws EntityAlreadyPersistedException {
		Metadatum metadatum = this.metadatumDAO.load(metadatumId, em);
		if (metadatum == null) {
			return null;
		}
		if (metadatumValue.getId() == null) {
			metadatumValue.setMetadatum(metadatum);
			em.persist(metadatumValue);
			metadatum.getMetadatumValues().add(metadatumValue);
			em.merge(metadatum);
			return metadatumValue;
		}
		throw new EntityAlreadyPersistedException();
	}

	public Long delete(Long id, EntityManager em) {
		MetadatumValue toBeDeleted = this.load(id, em);
		if (toBeDeleted != null) {
			em.remove(toBeDeleted);
			return id;
		}
		return null;
	}

	public MetadatumValue load(Long id, EntityManager em) {
		return em.find(MetadatumValue.class, id);
	}

	public MetadatumValue update(Long id, MetadatumValue metadatumValue, EntityManager em) {
		MetadatumValue persistedMetadatumValue = this.load(id, em);
		if (persistedMetadatumValue == null) {
			return null;
		}
		Metadatum metadatum = persistedMetadatumValue.getMetadatum();
		metadatumValue.setId(id);
		metadatumValue.setMetadatum(persistedMetadatumValue.getMetadatum());
		metadatumValue = em.merge(metadatumValue);
		metadatum.getMetadatumValues().add(metadatumValue);
		em.merge(metadatum);
		return metadatumValue;
	}
}
