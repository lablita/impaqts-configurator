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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Installation implements Serializable {

	private Long id;
	private String projectSubTitle;
	private String projectName;
	private Set<Corpus> corpora = new HashSet<>();
	private Set<Logo> logos = new HashSet<>();
	private String css;
	private String copyright;
	private String credits;
	private Boolean impaqts;

	@Lob
	private byte[] favicon;

	@Lob
	public String getCopyright() {
		return this.copyright;
	}

	@ManyToMany
	@JoinTable(name = "Installation_Corpus", joinColumns = {
			@JoinColumn(name = "installation_id") }, inverseJoinColumns = { @JoinColumn(name = "corpus_id") })
	public Set<Corpus> getCorpora() {
		return this.corpora;
	}

	@Lob
	public String getCredits() {
		return this.credits;
	}

	@Lob
	@JsonIgnore
	public String getCss() {
		return this.css;
	}

	public byte[] getFavicon() {
		return this.favicon;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}

	public Boolean getImpaqts() {
		return impaqts;
	}

	@OneToMany(mappedBy = "installation", cascade = { CascadeType.ALL })
	public Set<Logo> getLogos() {
		return this.logos;
	}

	@Column(unique = true)
	public String getProjectName() {
		return this.projectName;
	}

	public String getProjectSubTitle() {
		return this.projectSubTitle;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public void setCorpora(Set<Corpus> corpora) {
		this.corpora = corpora;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public void setFavicon(byte[] favicon) {
		this.favicon = favicon;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImpaqts(Boolean impaqts) {
		this.impaqts = impaqts;
	}

	public void setLogos(Set<Logo> logos) {
		this.logos = logos;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setProjectSubTitle(String projectSubTitle) {
		this.projectSubTitle = projectSubTitle;
	}
}
