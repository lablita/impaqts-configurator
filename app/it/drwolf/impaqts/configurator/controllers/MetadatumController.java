package it.drwolf.impaqts.configurator.controllers;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import it.drwolf.impaqts.configurator.dao.MetadatumDAO;
import it.drwolf.impaqts.configurator.exceptions.EntityAlreadyPersistedException;
import it.drwolf.impaqts.configurator.model.Metadatum;
import play.db.jpa.JPAApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

public class MetadatumController extends Controller {

	private final MetadatumDAO metadatumDAO;
	private final JPAApi jpaApi;

	@Inject
	public MetadatumController(MetadatumDAO metadatumDAO, JPAApi jpaApi) {
		this.metadatumDAO = metadatumDAO;
		this.jpaApi = jpaApi;
	}

	public Result addChildToParentMetadatum(Long parentId, Long childId) {
		return this.jpaApi.withTransaction(em -> {
			Metadatum parentMetadatum = this.metadatumDAO.load(parentId, em);
			if (parentMetadatum == null) {
				return Results.notFound(String.format("Parent metadatum with id %d not found.", parentId));
			}
			Metadatum childMetadatum = this.metadatumDAO.load(childId, em);
			if (childMetadatum == null) {
				return Results.notFound(String.format("Child metadatum with id %d not found.", parentId));
			}
			childMetadatum = this.metadatumDAO.addChildToParentMetadatum(parentMetadatum, childMetadatum, em);
			return Results.ok(Json.toJson(childMetadatum));
		});
	}

	public Result create(Http.Request request, Long corpusId) {
		JsonNode jsonMetadatum = request.body().asJson();
		Metadatum metadatum = Json.fromJson(jsonMetadatum, Metadatum.class);
		return this.jpaApi.withTransaction(em -> {
			Metadatum persistedMetadatum = null;
			try {
				persistedMetadatum = this.metadatumDAO.create(metadatum, corpusId, em);
			} catch (EntityAlreadyPersistedException e) {
				return Results.badRequest("Metadatum already persisted.");
			}
			if (persistedMetadatum != null) {
				return Results.ok(Json.toJson(persistedMetadatum));
			}
			return Results.internalServerError(String.format("Error while creating metadatum."));
		});
	}

	public Result delete(Long id) {
		return this.jpaApi.withTransaction(em -> {
			Long deletedId = this.metadatumDAO.delete(id, em);
			if (deletedId != null) {
				return Results.noContent();
			}
			return Results.notFound(String.format("Metadatum with id %d not found.", id));
		});
	}

	public Result load(Long id) {
		return this.jpaApi.withTransaction("default", true, em -> {
			Metadatum persistedMetadatum = this.metadatumDAO.load(id, em);
			if (persistedMetadatum != null) {
				return Results.ok(Json.toJson(persistedMetadatum));
			}
			return Results.notFound(String.format("Metadatum with id %d not found.", id));
		});
	}

	public Result removeChildFromParentMetadatum(Long parentId, Long childId) {
		return this.jpaApi.withTransaction(em -> {
			Metadatum parentMetadatum = this.metadatumDAO.load(parentId, em);
			if (parentMetadatum == null) {
				return Results.notFound(String.format("Parent metadatum with id %d not found.", parentId));
			}
			Metadatum childMetadatum = this.metadatumDAO.load(childId, em);
			if (parentMetadatum == null) {
				return Results.notFound(String.format("Child metadatum with id %d not found.", parentId));
			}
			childMetadatum = this.metadatumDAO.removeChildFromParentMetadatum(parentMetadatum, childMetadatum, em);
			return Results.ok(Json.toJson(childMetadatum));
		});
	}

	public Result update(Http.Request request, Long id) {
		JsonNode jsonMetadatum = request.body().asJson();
		Metadatum metadatum = Json.fromJson(jsonMetadatum, Metadatum.class);
		return this.jpaApi.withTransaction(em -> {
			Metadatum persistedMetadatum = this.metadatumDAO.update(id, metadatum, em);
			if (persistedMetadatum == null) {
				return Results.notFound(String.format("Metadatum with id %d not found.", id));
			}
			return Results.ok(Json.toJson(persistedMetadatum));
		});
	}

}
