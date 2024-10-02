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

package it.drwolf.impaqts.configurator.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Logo implements Serializable {

	public enum PositionType {
		TOP_LEFT, TOP_RIGHT, BOTTON_LEFT, BOTTON_RIGHT;
	}

	private Long id;
	private String url;
	private PositionType position;
	private Installation installation;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}

	@ManyToOne(optional = false)
	@JsonIgnore
	public Installation getInstallation() {
		return this.installation;
	}

	@Enumerated(EnumType.STRING)
	public PositionType getPosition() {
		return this.position;
	}

	public String getUrl() {
		return this.url;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInstallation(Installation installation) {
		this.installation = installation;
	}

	public void setPosition(PositionType position) {
		this.position = position;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
