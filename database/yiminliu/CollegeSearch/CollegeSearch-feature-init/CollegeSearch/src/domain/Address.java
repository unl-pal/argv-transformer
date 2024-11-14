package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private long addressId;

	@Column(name = "Street")
	private String streetAddress;

	@Column(name = "City")
	String city;

	@Column(name = "State")
	String state;

	@Column(name = "Zip_Code")
	String zipCode;

	@Column(name = "State")
	String country;

	@Column(name = "State")
	String location;
}
