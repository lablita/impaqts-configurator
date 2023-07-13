package it.drwolf.impaqts.configurator.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Corpus implements Serializable {

	private Long id;
	private String name;
	private String endpoint;

	private Boolean secureUrl = false;
	private Set<Installation> installations = new HashSet<>();
	private Set<Metadatum> metadata = new HashSet<>();

	public String getEndpoint() {
		return endpoint;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public Boolean isSecureUrl() {
		return secureUrl;
	}

	public void setSecureUrl(Boolean secureUrl) {
		this.secureUrl = secureUrl;
	}

	@ManyToMany(mappedBy = "corpora")
	@JsonIgnore
	public Set<Installation> getInstallations() {
		return installations;
	}

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "corpus")
	public Set<Metadatum> getMetadata() {
		return metadata;
	}

	public String getName() {
		return name;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInstallations(Set<Installation> installations) {
		this.installations = installations;
	}

	public void setMetadata(Set<Metadatum> metadata) {
		this.metadata = metadata;
	}

	public void setName(String name) {
		this.name = name;
	}
}
