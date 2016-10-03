package it.sidigroup.persistence;

import java.io.Serializable;
import java.util.Date;

public class MeasurementDTO implements Serializable{

	private static final long serialVersionUID = 2075732741265843803L;
	private String type;
	private int writeable;
	private String id;
	private String inventoryID;
	private String name;
	private String description;
	private Date lastGoodUpdate;	
	private Date lastUpdate;
	private double value;
	private String state;
	private String unit;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getWriteable() {
		return writeable;
	}
	public void setWriteable(int writeable) {
		this.writeable = writeable;
	}
	public String getInventoryID() {
		return inventoryID;
	}
	public void setInventoryID(String inventoryID) {
		this.inventoryID = inventoryID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getLastGoodUpdate() {
		return lastGoodUpdate;
	}
	public void setLastGoodUpdate(Date lastGoodUpdate) {
		this.lastGoodUpdate = lastGoodUpdate;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
