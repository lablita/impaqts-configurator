/*
 * Copyright (C) 2024
 * EMMACorpus
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
			metadatum.setCorpus(corpus);
			metadatum = em.merge(metadatum);
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
		metadatum.setCorpus(persistedMetadatum.getCorpus());
		metadatum.getSubMetadata().clear();
		persistedMetadatum.getSubMetadata().forEach(sm -> sm.setParentMetadatum(metadatum));
		metadatum.getSubMetadata().addAll(persistedMetadatum.getSubMetadata());
		return em.merge(metadatum);
	}
}
