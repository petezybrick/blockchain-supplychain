package com.petezybrick.bcsc.service.orc;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonOrcVo extends BaseOrcVo{
	private Integer personId;
	private String lastName;
	private String firstName;
	private String gender;
	private LocalDate birthDate;
	private Timestamp createdAt;
	
	
	@Override
	public PersonOrcVo createInstance(List<Object> objs, String schemaVersion ) throws Exception {
		if( "1.0".equals(schemaVersion ) ) {
		return new PersonOrcVo()
			.setPersonId((Integer)objs.get(0))
			.setLastName( (String)objs.get(1) )
			.setFirstName( (String)objs.get(2) )
			.setGender( (String)objs.get(3) )
			.setBirthDate( (LocalDate)objs.get(4) ) 
			.setCreatedAt( (Timestamp)objs.get(5) ) 
			;
		} else throw new Exception("Invalid schema version " + schemaVersion );
	}

	
	// TODO: some automated way to ensure the same order as the schema
	public List<Object> toObjectList() throws Exception {
		List<Object> objs = new ArrayList<Object>();
		objs.add(personId);
		objs.add(lastName);
		objs.add(firstName);
		objs.add(gender);
		objs.add(birthDate);
		objs.add(createdAt);
		return objs;
	}
	
	
	public void fromObjectList( List<Object> objs ) throws Exception {
		this.personId = (Integer)objs.get(0);
		this.lastName = (String)objs.get(1);
		this.firstName = (String)objs.get(2);
		this.gender = (String)objs.get(3);
		this.birthDate = (LocalDate)objs.get(4);
		this.createdAt = (Timestamp)objs.get(5);
	}
	
	
	public Integer getPersonId() {
		return personId;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getGender() {
		return gender;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public PersonOrcVo setPersonId(Integer personId) {
		this.personId = personId;
		return this;
	}
	public PersonOrcVo setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	public PersonOrcVo setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	public PersonOrcVo setGender(String gender) {
		this.gender = gender;
		return this;
	}
	public PersonOrcVo setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
		return this;
	}


	@Override
	public String toString() {
		final SimpleDateFormat fmtr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZ");
		return "PersonOrcVo [personId=" + personId + ", lastName=" + lastName + ", firstName=" + firstName + ", gender=" + gender + ", birthDate=" + birthDate
				+ ", createdAt=" + fmtr.format(createdAt) + "]";
	}


	public Timestamp getCreatedAt() {
		return createdAt;
	}


	public PersonOrcVo setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
		return this;
	}

}
