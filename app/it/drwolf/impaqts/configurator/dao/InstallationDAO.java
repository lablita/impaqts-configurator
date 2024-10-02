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

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import it.drwolf.impaqts.configurator.exceptions.EntityAlreadyPersistedException;
import it.drwolf.impaqts.configurator.exceptions.NonUniquenessException;
import it.drwolf.impaqts.configurator.model.Corpus;
import it.drwolf.impaqts.configurator.model.Installation;

public class InstallationDAO {

	private final CorpusDAO corpusDAO;

	@Inject
	public InstallationDAO(CorpusDAO corpusDAO) {
		this.corpusDAO = corpusDAO;
	}

	public Installation addCorpusToInstallation(Long installationId, Long corpusId, EntityManager em) {
		Installation installation = this.load(installationId, em);
		if (installation == null) {
			return null;
		}
		Corpus corpus = this.corpusDAO.load(corpusId, em);
		if (corpus == null) {
			return null;
		}
		installation.getCorpora().add(corpus);
		installation = em.merge(installation);
		corpus.getInstallations().add(installation);
		em.merge(corpus);
		return installation;
	}

	public Installation create(Installation installation, EntityManager em) throws EntityAlreadyPersistedException {
		if (installation.getId() == null) {
			em.persist(installation);
			return installation;
		}
		throw new EntityAlreadyPersistedException();
	}

	public Long delete(Long id, EntityManager em) {
		Installation toBeDeleted = this.load(id, em);
		if (toBeDeleted != null) {
			toBeDeleted.getCorpora().clear();
			em.remove(toBeDeleted);
			return id;
		}
		return null;
	}

	public Installation getByName(String installationName, EntityManager em) throws NonUniquenessException {
		List<Installation> installations = em
				.createQuery("from Installation i where i.projectName=:installationName", Installation.class)
				.setParameter("installationName", installationName)
				.getResultList();
		if (installations != null) {
			if (installations.size() == 1) {
				return installations.get(0);
			} else if (installations.size() > 1) {
				throw new NonUniquenessException();
			}
		}
		return null;
	}

	public String getDefaultCss(EntityManager em) throws NonUniquenessException {
		return this.getByName("DEFAULT", em).getCss();
	}

	public byte[] getDefaultFavicon(EntityManager em) throws NonUniquenessException {
		return this.getByName("DEFAULT", em).getFavicon();
	}

	public Installation load(Long id, EntityManager em) {
		return em.find(Installation.class, id);
	}

	public Installation removeCorpusFromInstallation(Long installationId, Long corpusId, EntityManager em) {
		Installation installation = this.load(installationId, em);
		if (installation == null) {
			return null;
		}
		Corpus corpus = this.corpusDAO.load(corpusId, em);
		if (corpus == null) {
			return null;
		}
		installation.getCorpora().remove(corpus);
		corpus.getInstallations().remove(installation);
		em.merge(installation);
		em.merge(corpus);
		return installation;
	}

	public Installation update(Long id, Installation installation, EntityManager em) {
		Installation persistedInstallation = this.load(id, em);
		if (persistedInstallation == null) {
			return null;
		}
		installation.setId(id);
		installation.getCorpora().addAll(persistedInstallation.getCorpora());
		return em.merge(installation);
	}
}
