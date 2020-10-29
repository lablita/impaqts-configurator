package it.drwolf.impaqts.configurator.controllers;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import it.drwolf.impaqts.configurator.dao.MetadatumValueDAO;
import it.drwolf.impaqts.configurator.exceptions.EntityAlreadyPersistedException;
import it.drwolf.impaqts.configurator.model.MetadatumValue;
import play.db.jpa.JPAApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

public class MetadatumValueController extends Controller {

	private final MetadatumValueDAO metadatumValueDAO;
	private final JPAApi jpaApi;

	@Inject
	public MetadatumValueController(MetadatumValueDAO metadatumValueDAO, JPAApi jpaApi) {
		this.metadatumValueDAO = metadatumValueDAO;
		this.jpaApi = jpaApi;
	}

	public Result create(Http.Request request, Long metadatumId) {
		JsonNode jsonMetadatumValue = request.body().asJson();
		MetadatumValue metadatumValue = Json.fromJson(jsonMetadatumValue, MetadatumValue.class);
		return this.jpaApi.withTransaction(em -> {
			MetadatumValue persistedMetadatumValue = null;
			try {
				persistedMetadatumValue = this.metadatumValueDAO.create(metadatumValue, metadatumId, em);
			} catch (EntityAlreadyPersistedException e) {
				return Results.badRequest("MetadatumValue already persisted.");
			}
			if (persistedMetadatumValue != null) {
				return Results.ok(Json.toJson(persistedMetadatumValue));
			}
			return Results.internalServerError(String.format("Error while creating metadatum value."));
		});
	}

	public Result delete(Long id) {
		return this.jpaApi.withTransaction(em -> {
			Long deletedId = this.metadatumValueDAO.delete(id, em);
			if (deletedId != null) {
				return Results.noContent();
			}
			return Results.notFound(String.format("Metadatum value with id %d not found.", id));
		});
	}

	public Result load(Long id) {
		return this.jpaApi.withTransaction("default", true, em -> {
			MetadatumValue persistedMetadatumValue = this.metadatumValueDAO.load(id, em);
			if (persistedMetadatumValue != null) {
				return Results.ok(Json.toJson(persistedMetadatumValue));
			}
			return Results.notFound(String.format("Metadatum value with id %d not found.", id));
		});
	}

	public Result update(Http.Request request, Long id) {
		JsonNode jsonMetadatumValue = request.body().asJson();
		MetadatumValue metadatumValue = Json.fromJson(jsonMetadatumValue, MetadatumValue.class);
		return this.jpaApi.withTransaction(em -> {
			MetadatumValue persistedMetadatumValue = this.metadatumValueDAO.update(id, metadatumValue, em);
			if (persistedMetadatumValue == null) {
				return Results.notFound(String.format("Metadatum value with id %d not found.", id));
			}
			return Results.ok(Json.toJson(persistedMetadatumValue));
		});
	}
}
