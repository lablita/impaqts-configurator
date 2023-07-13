package it.drwolf.impaqts.configurator.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import it.drwolf.impaqts.configurator.exceptions.EntityAlreadyPersistedException;
import it.drwolf.impaqts.configurator.model.Corpus;
import it.drwolf.impaqts.configurator.model.Metadatum;

public class CorpusDAO {

	public Corpus create(Corpus corpus, EntityManager em) throws EntityAlreadyPersistedException {
		if (corpus.getId() == null) {
			em.persist(corpus);
			return corpus;
		}
		throw new EntityAlreadyPersistedException();
	}

	public Long delete(Long id, EntityManager em) {
		Corpus toBeDeleted = this.load(id, em);
		if (toBeDeleted != null) {
			toBeDeleted.getInstallations().forEach(installation -> {
				installation.getCorpora().remove(toBeDeleted);
				em.merge(installation);
			});
			em.remove(toBeDeleted);
			return id;
		}
		return null;
	}

	public List<Metadatum> listMetadata(Long corpusId, EntityManager em) {
		Corpus corpus = this.load(corpusId, em);
		if (corpus != null && !corpus.getMetadata().isEmpty()) {
			List<Metadatum> metadata = new ArrayList<>();
			metadata.addAll(corpus.getMetadata().stream().filter(metadatum -> metadatum.getParentMetadatum() == null)
					.collect(Collectors.toList()));
			return metadata;
		}
		return Collections.emptyList();
	}

	public Corpus load(Long id, EntityManager em) {
		return em.find(Corpus.class, id);
	}

	public Corpus update(Long id, Corpus corpus, EntityManager em) {
		Corpus persistedCorpus = this.load(id, em);
		if (persistedCorpus == null) {
			return null;
		}
		corpus.setId(id);
		corpus.getMetadata().addAll(persistedCorpus.getMetadata());
		return em.merge(corpus);
	}
}
