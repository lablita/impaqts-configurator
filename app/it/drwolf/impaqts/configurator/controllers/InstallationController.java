package it.drwolf.impaqts.configurator.controllers;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import it.drwolf.impaqts.configurator.dao.InstallationDAO;
import it.drwolf.impaqts.configurator.exceptions.EntityAlreadyPersistedException;
import it.drwolf.impaqts.configurator.exceptions.NonUniquenessException;
import it.drwolf.impaqts.configurator.model.Installation;
import play.db.jpa.JPAApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

public class InstallationController extends Controller {

	private final InstallationDAO installationDAO;
	private final JPAApi jpaApi;

	@Inject
	public InstallationController(InstallationDAO installationDAO, JPAApi jpaApi) {
		this.installationDAO = installationDAO;
		this.jpaApi = jpaApi;
	}

	public Result addCorpusToInstallation(Long installationId, Long corpusId) {
		return this.jpaApi.withTransaction(em -> {
			Installation installation = this.installationDAO.addCorpusToInstallation(installationId, corpusId, em);
			if (installation == null) {
				return Results.notFound(String.format("One of installation id (%d) or corpus id (%d) not found.",
						installationId, corpusId));
			}
			return Results.ok(Json.toJson(installation));
		});
	}

	public Result create(Http.Request request) {
		JsonNode jsonInstallation = request.body().asJson();
		Installation installation = Json.fromJson(jsonInstallation, Installation.class);
		return this.jpaApi.withTransaction(em -> {
			Installation persistedInstallation = null;
			try {
				persistedInstallation = this.installationDAO.create(installation, em);
			} catch (EntityAlreadyPersistedException e) {
				return Results.badRequest("Installation already persisted.");
			}
			if (persistedInstallation != null) {
				return Results.ok(Json.toJson(persistedInstallation));
			}
			return Results.internalServerError(String.format("Error while creating installation."));
		});
	}

	public Result delete(Long id) {
		return this.jpaApi.withTransaction(em -> {
			Long deletedId = this.installationDAO.delete(id, em);
			if (deletedId != null) {
				return Results.noContent();
			}
			return Results.notFound(String.format("Installation with id %d not found.", id));
		});
	}

	public Result getByName(String installationName) {
		return this.jpaApi.withTransaction("default", true, em -> {
			Installation installation = null;
			try {
				installation = this.installationDAO.getByName(installationName, em);
			} catch (NonUniquenessException e) {
				return Results.internalServerError(
						String.format("More than one installation with name %s", installationName));
			}
			if (installation != null) {
				return Results.ok(Json.toJson(installation));
			}
			return Results.notFound(String.format("Installation with name %s not found.", installationName));
		});
	}

	public Result load(Long id) {
		return this.jpaApi.withTransaction("default", true, em -> {
			Installation persistedInstallation = this.installationDAO.load(id, em);
			if (persistedInstallation != null) {
				return Results.ok(Json.toJson(persistedInstallation));
			}
			return Results.notFound(String.format("Installation with id %d not found.", id));
		});
	}

	public Result removeCorpusFromInstallation(Long installationId, Long corpusId) {
		return this.jpaApi.withTransaction(em -> {
			Installation installation = this.installationDAO.removeCorpusFromInstallation(installationId, corpusId, em);
			if (installation == null) {
				return Results.notFound(String.format("One of installation id (%d) or corpus id (%d) not found.",
						installationId, corpusId));
			}
			return Results.ok(Json.toJson(installation));
		});
	}

	public Result update(Http.Request request, Long id) {
		JsonNode jsonInstallation = request.body().asJson();
		Installation installation = Json.fromJson(jsonInstallation, Installation.class);
		return this.jpaApi.withTransaction(em -> {
			Installation persistedInstallation = this.installationDAO.update(id, installation, em);
			if (persistedInstallation == null) {
				return Results.notFound(String.format("Installation with id %d not found.", id));
			}
			return Results.ok(Json.toJson(persistedInstallation));
		});
	}
}
