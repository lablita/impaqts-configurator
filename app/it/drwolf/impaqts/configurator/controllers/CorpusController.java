package it.drwolf.impaqts.configurator.controllers;

import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import it.drwolf.impaqts.configurator.dao.CorpusDAO;
import it.drwolf.impaqts.configurator.exceptions.EntityAlreadyPersistedException;
import it.drwolf.impaqts.configurator.model.Corpus;
import it.drwolf.impaqts.configurator.model.Metadatum;
import play.db.jpa.JPAApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

public class CorpusController extends Controller {

	public static final String CORPUS_WITH_ID_NOT_FOUND = "Corpus with id %d not found.";
	private final JPAApi jpaApi;
	private final CorpusDAO corpusDAO;

	@Inject
	public CorpusController(CorpusDAO corpusDAO, JPAApi jpaApi) {
		this.corpusDAO = corpusDAO;
		this.jpaApi = jpaApi;
	}

	public Result create(Http.Request request) {
		JsonNode jsonCorpus = request.body().asJson();
		Corpus corpus = Json.fromJson(jsonCorpus, Corpus.class);
		return this.jpaApi.withTransaction(em -> {
			Corpus persistedCorpus = null;
			try {
				persistedCorpus = this.corpusDAO.create(corpus, em);
			} catch (EntityAlreadyPersistedException e) {
				return Results.badRequest("Corpus already persisted.");
			}
			if (persistedCorpus != null) {
				return Results.ok(Json.toJson(persistedCorpus));
			}
			return Results.internalServerError(String.format("Error while creating corpus."));
		});
	}

	public Result delete(Long id) {
		return this.jpaApi.withTransaction(em -> {
			Long deletedId = this.corpusDAO.delete(id, em);
			if (deletedId != null) {
				return Results.noContent();
			}
			return Results.notFound(String.format(CORPUS_WITH_ID_NOT_FOUND, id));
		});
	}

	public Result listMetadata(Long corpusId) {
		return this.jpaApi.withTransaction("default", true, em -> {
			List<Metadatum> metadata = this.corpusDAO.listMetadata(corpusId, em);
			return Results.ok(Json.toJson(metadata));
		});
	}

	public Result load(Long id) {
		return this.jpaApi.withTransaction("default", true, em -> {
			Corpus persistedCorpus = this.corpusDAO.load(id, em);
			if (persistedCorpus != null) {
				return Results.ok(Json.toJson(persistedCorpus));
			}
			return Results.notFound(String.format(CORPUS_WITH_ID_NOT_FOUND, id));
		});
	}

	public Result update(Http.Request request, Long id) {
		JsonNode jsonCorpus = request.body().asJson();
		Corpus corpus = Json.fromJson(jsonCorpus, Corpus.class);
		return this.jpaApi.withTransaction(em -> {
			Corpus persistedCorpus = this.corpusDAO.update(id, corpus, em);
			if (persistedCorpus == null) {
				return Results.notFound(String.format(CORPUS_WITH_ID_NOT_FOUND, id));
			}
			return Results.ok(Json.toJson(persistedCorpus));
		});
	}
}
