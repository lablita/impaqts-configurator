package it.drwolf.impaqts.configurator.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MetadatumValue implements Serializable {

	private Long id;
	private String value;
	private Integer position;
	private Metadatum metadatum;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@ManyToOne
	@JsonIgnore
	public Metadatum getMetadatum() {
		return metadatum;
	}

	public Integer getPosition() {
		return position;
	}

	public String getValue() {
		return value;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMetadatum(Metadatum metadatum) {
		this.metadatum = metadatum;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
