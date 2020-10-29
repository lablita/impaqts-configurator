package it.drwolf.impaqts.configurator.handlers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import play.Logger;
import play.http.HttpErrorHandler;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

public class ErrorHandler implements HttpErrorHandler {

	private final Logger.ALogger logger = Logger.of(this.getClass());

	private Result handleError(Throwable err) {
		return Results.internalServerError(Json.toJson(err));
	}

	@Override
	public CompletionStage<Result> onClientError(Http.RequestHeader request, int statusCode, String message) {
		return CompletableFuture.completedFuture(Results.status(statusCode, message));
	}

	@Override
	public CompletionStage<Result> onServerError(Http.RequestHeader request, Throwable exception) {
		this.logger.error("Internal server error", exception);
		return CompletableFuture.completedFuture(this.handleError(exception));
	}

}