package fr.uca.cdr.skillful_network.model.entities.simulation.exercise;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import fr.uca.cdr.skillful_network.model.entities.JobOffer;
import fr.uca.cdr.skillful_network.model.entities.Training;

@Entity
@Table(name = "keyword")
public class Keyword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private Long id;
	@NotFound(action=NotFoundAction.IGNORE)
	@NotNull
	private String name;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST},mappedBy ="keywords")
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@NotNull
	private Set<Exercise> exercises = new HashSet<Exercise>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST},mappedBy ="keywords")
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@NotNull
	private Set<JobOffer> jobOffers = new HashSet<JobOffer>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST},mappedBy ="keywords")
	@JsonIgnore
	@NotFound(action=NotFoundAction.IGNORE)
	@NotNull
	private Set<Training> trainings = new HashSet<Training>();

	public Keyword() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Keyword(Long id, String name, Set<Exercise> exercises, Set<JobOffer> jobOffers, Set<Training> trainings) {
		super();
		this.id = id;
		this.name = name;
		this.exercises = exercises;
		this.jobOffers = jobOffers;
		this.trainings = trainings;
	}

	public Keyword(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(Set<Exercise> exercises) {
		this.exercises = exercises;
	}

	public Set<JobOffer> getJobOffers() {
		return jobOffers;
	}

	public void setJobOffers(Set<JobOffer> jobOffers) {
		this.jobOffers = jobOffers;
	}

	public Set<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(Set<Training> trainings) {
		this.trainings = trainings;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
	
	@Override
	public String toString() {
		return "Keyword [id=" + id + ", name=" + name  + "]";
	}

	
}
