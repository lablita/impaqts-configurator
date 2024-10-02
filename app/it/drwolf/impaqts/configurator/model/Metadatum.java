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

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
	private String label;
	private MetadatumGroup metadatumGroup;

	@ManyToOne
	@JsonIgnore
	public Corpus getCorpus() {
		return this.corpus;
	}

	public Boolean getDefaultAttribute() {
		return defaultAttribute;
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

	public String getLabel() {
		return label;
	}

	@ManyToOne
	public MetadatumGroup getMetadatumGroup() {
		return metadatumGroup;
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

	public void setDefaultAttribute(Boolean defaultAttribute) {
		this.defaultAttribute = defaultAttribute;
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

	public void setLabel(String label) {
		this.label = label;
	}

	public void setMetadatumGroup(MetadatumGroup metadatumGroup) {
		this.metadatumGroup = metadatumGroup;
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
