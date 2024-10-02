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