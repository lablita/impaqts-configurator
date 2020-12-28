package it.drwolf.impaqts.configurator.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Installation implements Serializable {

	private Long id;
	private String projectSubTitle;
	private String projectName;
	private Set<Corpus> corpora = new HashSet<>();
	private Set<Color> colors = new HashSet<>();
	private Set<Logo> logos = new HashSet<>();
	private Set<String> fonts = new HashSet<>();
	@Lob
	private String copyright;
	@Lob
	private String credits;

	@OneToMany(mappedBy = "installation", cascade = { CascadeType.ALL })
	public Set<Color> getColors() {
		return this.colors;
	}

	public String getCopyright() {
		return this.copyright;
	}

	@ManyToMany
	@JoinTable(name = "Installation_Corpus", joinColumns = {
			@JoinColumn(name = "installation_id") }, inverseJoinColumns = { @JoinColumn(name = "corpus_id") })
	public Set<Corpus> getCorpora() {
		return this.corpora;
	}

	public String getCredits() {
		return this.credits;
	}

	@ElementCollection
	@OrderBy
	public Set<String> getFonts() {
		return this.fonts;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
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

	public void setColors(Set<Color> colors) {
		this.colors = colors;
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

	public void setFonts(Set<String> fonts) {
		this.fonts = fonts;
	}

	public void setId(Long id) {
		this.id = id;
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
