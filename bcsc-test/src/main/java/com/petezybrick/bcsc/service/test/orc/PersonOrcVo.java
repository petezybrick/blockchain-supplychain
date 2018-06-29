package com.petezybrick.bcsc.service.test.orc;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.petezybrick.bcsc.service.orc.BaseOrcVo;

public class PersonOrcVo extends BaseOrcVo{
	private Integer personId;
	private String lastName;
	private String firstName;
	private String gender;
	private LocalDate birthDate;
	private Timestamp createdAt;
	private ByteBuffer personByte;
	private Boolean personBoolean;
	private Double personDouble;
	private Float personFloat;
	private BigDecimal personDecimal;
	
	
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
			.setPersonByte( (ByteBuffer)objs.get(6) ) 
			.setPersonBoolean( (Boolean)objs.get(7) ) 
			.setPersonDouble( (Double)objs.get(8) ) 
			.setPersonFloat( (Float)objs.get(9) ) 
			.setPersonDecimal( (BigDecimal)objs.get(10) ) 
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
		objs.add(personByte);
		objs.add(personBoolean);
		objs.add(personDouble);
		objs.add(personFloat);
		objs.add(personDecimal);
		return objs;
	}
	
	
	public void fromObjectList( List<Object> objs ) throws Exception {
		this.personId = (Integer)objs.get(0);
		this.lastName = (String)objs.get(1);
		this.firstName = (String)objs.get(2);
		this.gender = (String)objs.get(3);
		this.birthDate = (LocalDate)objs.get(4);
		this.createdAt = (Timestamp)objs.get(5);
		this.personByte = (ByteBuffer)objs.get(5);
		this.personBoolean = (Boolean)objs.get(5);
		this.personDouble = (Double)objs.get(5);
		this.personFloat = (Float)objs.get(5);
		this.personDecimal = (BigDecimal)objs.get(5);
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
		try {
			return "PersonOrcVo [personId=" + personId + ", lastName=" + lastName + ", firstName=" + firstName + ", gender=" + gender + ", birthDate=" + birthDate
					+ ", createdAt=" + fmtr.format(createdAt) + ", personBinary=" + roByteBufferToString(personByte) + ", personBoolean=" + personBoolean + ", personDouble=" + personDouble
					+ ", personFloat=" + personFloat + ", personDecimal=" + personDecimal + "]";
		} catch (Exception e) {
			return "?";
		}
	}


	public Timestamp getCreatedAt() {
		return createdAt;
	}


	public PersonOrcVo setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
		return this;
	}


	public ByteBuffer getPersonByte() {
		return personByte;
	}


	public Boolean getPersonBoolean() {
		return personBoolean;
	}


	public Double getPersonDouble() {
		return personDouble;
	}


	public Float getPersonFloat() {
		return personFloat;
	}


	public BigDecimal getPersonDecimal() {
		return personDecimal;
	}


	public PersonOrcVo setPersonByte(ByteBuffer personByte) {
		this.personByte = personByte;
		return this;
	}


	public PersonOrcVo setPersonBoolean(Boolean personBoolean) {
		this.personBoolean = personBoolean;
		return this;
	}


	public PersonOrcVo setPersonDouble(Double personDouble) {
		this.personDouble = personDouble;
		return this;
	}


	public PersonOrcVo setPersonFloat(Float personFloat) {
		this.personFloat = personFloat;
		return this;
	}


	public PersonOrcVo setPersonDecimal(BigDecimal personDecimal) {
		this.personDecimal = personDecimal;
		return this;
	}

}
