package it.drwolf.impaqts.configurator.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Metadatum implements Serializable {

	private Long id;
	private String name;
	private Boolean documentMetadatum = Boolean.TRUE;

	private Integer position;

	private Set<Metadatum> subMetadata = new HashSet<>();
	private Metadatum parentMetadatum;
	private Corpus corpus;
	private Boolean multipleChoice = Boolean.FALSE;
	private Boolean retrieveValuesFromCorpus = Boolean.FALSE;
	private Boolean freeText = Boolean.FALSE;

	@ManyToOne
	@JsonIgnore
	public Corpus getCorpus() {
		return corpus;
	}

	public Boolean getDocumentMetadatum() {
		return documentMetadatum;
	}

	public Boolean getFreeText() {
		return freeText;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public Boolean getMultipleChoice() {
		return multipleChoice;
	}

	public String getName() {
		return name;
	}

	@ManyToOne
	@JsonIgnore
	public Metadatum getParentMetadatum() {
		return parentMetadatum;
	}

	public Integer getPosition() {
		return position;
	}

	public Boolean getRetrieveValuesFromCorpus() {
		return retrieveValuesFromCorpus;
	}

	@OneToMany(mappedBy = "parentMetadatum")
	public Set<Metadatum> getSubMetadata() {
		return subMetadata;
	}

	public void setCorpus(Corpus corpus) {
		this.corpus = corpus;
	}

	public void setDocumentMetadatum(Boolean documentMetadatum) {
		this.documentMetadatum = documentMetadatum;
	}

	public void setFreeText(Boolean freeText) {
		this.freeText = freeText;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMultipleChoice(Boolean multipleChoice) {
		this.multipleChoice = multipleChoice;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentMetadatum(Metadatum parentMetadatum) {
		this.parentMetadatum = parentMetadatum;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public void setRetrieveValuesFromCorpus(Boolean retrieveValuesFromCorpus) {
		this.retrieveValuesFromCorpus = retrieveValuesFromCorpus;
	}

	public void setSubMetadata(Set<Metadatum> subMetadata) {
		this.subMetadata = subMetadata;
	}
}
