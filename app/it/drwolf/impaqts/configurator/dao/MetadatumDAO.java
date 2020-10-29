package it.drwolf.impaqts.configurator.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import it.drwolf.impaqts.configurator.exceptions.EntityAlreadyPersistedException;
import it.drwolf.impaqts.configurator.model.Corpus;
import it.drwolf.impaqts.configurator.model.Metadatum;

public class MetadatumDAO {

	private final CorpusDAO corpusDAO;

	@Inject
	public MetadatumDAO(CorpusDAO corpusDAO) {
		this.corpusDAO = corpusDAO;
	}

	public Metadatum addChildToParentMetadatum(Metadatum parentMetadatum, Metadatum childMetadatum, EntityManager em) {
		parentMetadatum.getSubMetadata().add(childMetadatum);
		childMetadatum.setParentMetadatum(parentMetadatum);
		em.merge(parentMetadatum);
		return em.merge(childMetadatum);
	}

	public Metadatum create(Metadatum metadatum, Long corpusId, EntityManager em)
			throws EntityAlreadyPersistedException {
		Corpus corpus = this.corpusDAO.load(corpusId, em);
		if (corpus == null) {
			return null;
		}
		if (metadatum.getId() == null) {
			em.persist(metadatum);
			corpus.getMetadata().add(metadatum);
			em.merge(corpus);
			return metadatum;
		}
		throw new EntityAlreadyPersistedException();
	}

	public Long delete(Long id, EntityManager em) {
		Metadatum toBeDeleted = this.load(id, em);
		if (toBeDeleted != null) {
			em.remove(toBeDeleted);
			return id;
		}
		return null;
	}

	public Metadatum load(Long id, EntityManager em) {
		return em.find(Metadatum.class, id);
	}

	public Metadatum removeChildFromParentMetadatum(Metadatum parentMetadatum, Metadatum childMetadatum,
			EntityManager em) {
		parentMetadatum.getSubMetadata().remove(childMetadatum);
		childMetadatum.setParentMetadatum(null);
		em.merge(parentMetadatum);
		return em.merge(childMetadatum);
	}

	public Metadatum update(Long id, Metadatum metadatum, EntityManager em) {
		Metadatum persistedMetadatum = this.load(id, em);
		if (persistedMetadatum == null) {
			return null;
		}
		metadatum.setId(id);
		metadatum.setParentMetadatum(persistedMetadatum.getParentMetadatum());
		metadatum.getMetadatumValues().addAll(persistedMetadatum.getMetadatumValues());
		return em.merge(metadatum);
	}
}
