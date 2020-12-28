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
