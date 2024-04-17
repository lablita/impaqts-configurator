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
import javax.persistence.Transient;

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

	private Boolean defaultAttribute = Boolean.FALSE;

	public Boolean getDefaultAttribute() {
		return defaultAttribute;
	}

	public void setDefaultAttribute(Boolean defaultAttribute) {
		this.defaultAttribute = defaultAttribute;
	}

	@ManyToOne
	@JsonIgnore
	public Corpus getCorpus() {
		return this.corpus;
	}

	public Boolean getDocumentMetadatum() {
		return this.documentMetadatum;
	}

	public Boolean getFreeText() {
		return this.freeText;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}

	public Boolean getMultipleChoice() {
		return this.multipleChoice;
	}

	public String getName() {
		return this.name;
	}

	@ManyToOne
	@JsonIgnore
	public Metadatum getParentMetadatum() {
		return this.parentMetadatum;
	}

	public Integer getPosition() {
		return this.position;
	}

	public Boolean getRetrieveValuesFromCorpus() {
		return this.retrieveValuesFromCorpus;
	}

	@OneToMany(mappedBy = "parentMetadatum")
	public Set<Metadatum> getSubMetadata() {
		return this.subMetadata;
	}

	@Transient
	public boolean isChild() {
		return this.parentMetadatum != null;
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
