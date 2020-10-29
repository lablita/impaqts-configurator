package it.drwolf.impaqts.configurator.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Installation implements Serializable {

	private Long id;
	private String displayName;
	private String name;
	private Set<Corpus> corpora = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "Installation_Corpus", joinColumns = {
			@JoinColumn(name = "installation_id") }, inverseJoinColumns = { @JoinColumn(name = "corpus_id") })
	public Set<Corpus> getCorpora() {
		return corpora;
	}

	public String getDisplayName() {
		return displayName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@Column(unique = true)
	public String getName() {
		return name;
	}

	public void setCorpora(Set<Corpus> corpora) {
		this.corpora = corpora;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
