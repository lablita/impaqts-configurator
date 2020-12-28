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
public class Color implements Serializable {

	public enum ColorSet {
		PRIMARY, SECONDARY, WARNING, ERROR;
	}

	private Long id;
	private String name;
	private ColorSet color;
	private String hex;
	private Installation installation;

	@Enumerated(EnumType.STRING)
	public ColorSet getColor() {
		return this.color;
	}

	public String getHex() {
		return this.hex;
	}

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

	public String getName() {
		return this.name;
	}

	public void setColor(ColorSet color) {
		this.color = color;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInstallation(Installation installation) {
		this.installation = installation;
	}

	public void setName(String name) {
		this.name = name;
	}

}
