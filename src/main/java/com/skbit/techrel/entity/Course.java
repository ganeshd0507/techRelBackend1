package com.skbit.techrel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String logo;
	
	 @NotBlank(message = "Title is mandatory")
	 @Column(nullable = false)
	 private String title;
	
	@NotBlank(message = "Duration is mandatory")
	@Column(nullable = false)
	private String duration;
	
	@NotBlank(message = "Live Project Duration is mandatory")
    @Column(nullable = false)
	private String liveProjectDuration;

	@NotBlank(message = "Syllabus is mandatory")
    @Size(max = 1000, message = "Syllabus must be at most 1000 characters")
    @Column(nullable = false, length = 1000)
	private String syllabus;
	
	@NotBlank(message = "Brochures is mandatory")
    @Size(max = 5000, message = "Brochures must be at most 5000 characters")
    @Column(nullable = false, length = 5000)
	private String brochures;

    @NotBlank(message = "Cost is mandatory")
    @Pattern(regexp = "\\d+", message = "Cost must be a valid number")
    @Column(nullable = false)
	private String cost;

	@Transient
	private String logoImageUrl;

	public String getLogoImageUrl() {
		return "/course-images/" + this.logo;
	}

/*	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Course(int id, String logo, String title, String duration, String liveProjectDuration, String syllabus,
			String brochures, String cost, String logoImageUrl) {
		super();
		this.id = id;
		this.logo = logo;
		this.title = title;
		this.duration = duration;
		this.liveProjectDuration = liveProjectDuration;
		this.syllabus = syllabus;
		this.brochures = brochures;
		this.cost = cost;
		this.logoImageUrl = logoImageUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getLiveProjectDuration() {
		return liveProjectDuration;
	}

	public void setLiveProjectDuration(String liveProjectDuration) {
		this.liveProjectDuration = liveProjectDuration;
	}

	public String getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}

	public String getBrochures() {
		return brochures;
	}

	public void setBrochures(String brochures) {
		this.brochures = brochures;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	} */
}
