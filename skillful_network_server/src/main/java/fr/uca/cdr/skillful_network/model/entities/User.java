package fr.uca.cdr.skillful_network.model.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="user")
public class User implements UserDetails  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Size(min = 2, max = 20, message = "firstName must be between 2 and 20 characters")
	private String firstName;
	@Size(min = 2, max = 20, message = "lastName must be between 2 and 20 characters")

	private String lastName;
	@Size(min = 8, message = "password must be at least 8 characters")
	private String password;
	@PastOrPresent
	private Date birthDate;
	@NotNull(message = "Email cannot be null")
	@Email(message = "Email should be valid")
	private String email;
	private String mobileNumber;
	private String status;
	private boolean validated = false;
	private String careerGoal;
	private boolean photo = false;

	private LocalDateTime temporaryCodeExpirationDate;
	
	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private Set<Skill> skillSet = new HashSet<Skill>();

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private Set<Qualification> qualificationSet = new HashSet<Qualification>();

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private Set<Subscription> subscriptionSet = new HashSet<Subscription>();

	@OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JsonIgnoreProperties("user") 
	@JsonBackReference 
	private Set<JobApplication> jobApplicationSet = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JsonIgnoreProperties("user")
	private Set<TrainingApplication> trainingApplicationSet = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	private Set<Training> trainingSet = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JsonIgnoreProperties("user") 
	private Set<Simulation> simulationSet = new HashSet<>();
	
	@Transient
	private Collection<? extends GrantedAuthority> authorities;
	
	public User() {
		super();
	}

	public User(long id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public User(String password, String email) {
		super();
		this.password = password;
		this.email = email;
	}

	public User(long id, String firstName, String lastName, String password, Date birthDate, String email,
			String mobileNumber, int status, boolean photo, String careerGoal) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.birthDate = birthDate;
		this.email = email.toLowerCase();
		this.mobileNumber = mobileNumber;
		this.status = Status.fromId(status);
		this.photo = photo;
		this.validated = true;
		this.careerGoal = careerGoal;
	}

	public User(long id,
			@Size(min = 2, max = 20, message = "firstName must be between 2 and 20 characters") String firstName,
			@Size(min = 2, max = 20, message = "lastName must be between 2 and 20 characters") String lastName,
			@Size(min = 8, message = "password must be at least 8 characters") String password,
			@PastOrPresent Date birthDate,
			@NotNull(message = "Email cannot be null") @Email(message = "Email should be valid") String email,
			String mobileNumber, String status, boolean validated, String careerGoal, boolean photo, Set<Skill> skillSet,
			Set<Qualification> qualificationSet, Set<Subscription> subscriptionSet,
			Set<JobApplication> jobApplicationSet, Set<TrainingApplication> trainingApplicationSet, Set<Role> roles, Set<Simulation> simulationSet, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.birthDate = birthDate;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.status = status;
		this.validated = validated;
		this.careerGoal = careerGoal;
		this.photo = photo;
		this.skillSet = skillSet;
		this.qualificationSet = qualificationSet;
		this.subscriptionSet = subscriptionSet;
		this.jobApplicationSet = jobApplicationSet;
		this.trainingApplicationSet = trainingApplicationSet;
		this.simulationSet = simulationSet;
		this.roles = roles;
		this.authorities = authorities;
	}
	
	public static User build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(roles -> new SimpleGrantedAuthority(roles.getName().name())).collect(Collectors.toList());
		return new User(user.getId(), user.getFirstName(), user.getLastName(), user.getPassword(),
				user.getBirthDate(), user.getEmail(), user.getMobileNumber(), user.getStatus(), 
				user.isValidated(),user.getCareerGoal(), user.isPhoto(), user.getSkillSet(), user.getQualificationSet(),
				user.getSubscriptionSet(), user.getJobApplicationSet(), user.getTrainingApplicationSet(),
				user.getRoles(), user.getSimulationSet() , authorities);

	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;

	}
	

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User user = (User) o;
		return Objects.equals(id, user.id);
	}
	
	

	public Set<Training> getTrainingSet() {
		return trainingSet;
	}

	public void setTrainingSet(Set<Training> trainingSet) {
		this.trainingSet = trainingSet;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public String getCareerGoal() {
		return careerGoal;
	}

	public void setCareerGoal(String careerGoal) {
		this.careerGoal = careerGoal;
	}

	public boolean isPhoto() {
		return photo;
	}

	public void setPhoto(boolean photo) {
		this.photo = photo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Skill> getSkillSet() {
		return skillSet;
	}

	public void setSkillSet(Set<Skill> skillSet) {
		this.skillSet = skillSet;
	}

	public Set<Qualification> getQualificationSet() {
		return qualificationSet;
	}

	public void setQualificationSet(Set<Qualification> qualificationSet) {
		this.qualificationSet = qualificationSet;
	}

	public Set<Subscription> getSubscriptionSet() {
		return subscriptionSet;
	}

	public void setSubscriptionSet(Set<Subscription> subscriptionSet) {
		this.subscriptionSet = subscriptionSet;
	}

	public Set<JobApplication> getJobApplicationSet() {
		return jobApplicationSet;
	}

	public void setJobApplicationSet(Set<JobApplication> jobApplicationSet) {
		this.jobApplicationSet = jobApplicationSet;
	}

	public Set<TrainingApplication> getTrainingApplicationSet() {
		return trainingApplicationSet;
	}

	public void setTrainingApplicationSet(Set<TrainingApplication> trainingApplicationSet) {
		this.trainingApplicationSet = trainingApplicationSet;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


//	public LocalDateTime getDateExpiration() {
//		return dateExpiration;
//	}
//
//	public void setDateExpiration(LocalDateTime dateExpiration) {
//		this.dateExpiration = dateExpiration;
//	}
//
//	

	public LocalDateTime getTemporaryCodeExpirationDate() {
		return temporaryCodeExpirationDate;
	}

	public void setTemporaryCodeExpirationDate(LocalDateTime temporaryCodeExpirationDate) {
		this.temporaryCodeExpirationDate = temporaryCodeExpirationDate;
	}
	
	

	public Set<Simulation> getSimulationSet() {
		return simulationSet;
	}

	public void setSimulationSet(Set<Simulation> simulationSet) {
		this.simulationSet = simulationSet;
	}

	@Override
	public String toString() {
		return "User [" + id + "] firstName=" + firstName + ", lastName=" + lastName + ", password=" + password
				+ ", birthDate=" + birthDate + ", email=" + email + ", mobileNumber=" + mobileNumber + ", status="
				+ status + ", validated=" + validated + ", careerGoal=" + careerGoal + ", photo=" + photo + ", "
				+ ", skillSet=" + skillSet + ", qualificationSet=" + qualificationSet + ", subscriptionSet="
				+ subscriptionSet + ", jobApplicationSets=" + jobApplicationSet + ", trainingApplicationSet="
				+ trainingApplicationSet + ", roles=" + roles + "]";
	}
}
