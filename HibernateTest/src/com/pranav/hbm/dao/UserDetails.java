package com.pranav.hbm.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name="USER_DETAILS")
public class UserDetails {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="USER_ID")
	private int userId;
	@Column(name="USER_NAME")
	private String userName;
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="street", column=@Column(name="Residence_STREET")),
		@AttributeOverride(name="city", column=@Column(name="Residence_CITY")),
		@AttributeOverride(name="state", column=@Column(name="Residence_STATE")),
		@AttributeOverride(name="pincode", column=@Column(name="Residence_PIN")),
	})
	private Address residenceAddr;
	@Embedded
	private Address OfficeAddr;
	@ElementCollection(fetch=FetchType.EAGER)
	@JoinTable(name="USER_ASSETS",
				joinColumns=@JoinColumn(name="USER_ID")
	)
	@Column(name="USER_ASSET")
	@GenericGenerator(name = "sequence.gen", strategy = "seqhilo")
	@CollectionId(columns = { @Column(name="ASSET_ID") }, generator = "sequence.gen", type =@Type(type="long"))
	private Collection<String> assets = new ArrayList<String>();
	@OneToMany(fetch=FetchType.EAGER)
	@JoinTable(name="USER_VEHICLES",
	joinColumns=@JoinColumn(name="USER_ID")
	)
	@Column(name="VEHICLE_ID")
	private List<Vehicle> vehicles = new ArrayList<Vehicle>();
	
	public List<Vehicle> getVehicles() {
		return vehicles;
	}
	
	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	public Collection<String> getAssets() {
		return assets;
	}
	public void setAssets(Collection<String> assets) {
		this.assets = assets;
	}
	public Address getOfficeAddr() {
		return OfficeAddr;
	}
	public void setOfficeAddr(Address officeAddr) {
		OfficeAddr = officeAddr;
	}
	public Address getOResidenceAddr() {
		return residenceAddr;
	}
	public void setResidenceAddr(Address residenceAddr) {
		this.residenceAddr = residenceAddr;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
