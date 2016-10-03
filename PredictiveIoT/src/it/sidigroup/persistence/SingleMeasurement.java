package it.sidigroup.persistence;

import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="singleMeasurement")
@NamedQueries({
	@NamedQuery(name = "getAllMeasurements", query = "select m from singleMeasurement m"), 
	@NamedQuery(name = "deleteAllData", query = "delete from singleMeasurement m"),
	@NamedQuery(name = "lastReading", query = "select m from singleMeasurement m where m.storedAt = (SELECT MAX(r.storedAt) from singleMeasurement r)") })
@Table(name = "SINGLEMEASUREMENT")
public class SingleMeasurement implements Serializable {

	/*
	 * "unit":"°C", //measurement "value":"19.65", //measurement "state":0,
	 * //measurement "lastGoodUpdate":"Set 12, 2016 9:20:38 AM", //measure
	 * "lastUpdate":"Set 12, 2016 9:20:38 AM" //measure
	 */

	private static final long serialVersionUID = 1L;

	public SingleMeasurement() {
	}

	@Id
	@GeneratedValue(strategy = AUTO)
	private Long id;

	@Column(name = "SENSOR_ID")
	private Long sensorID;
	
	@Column(name = "INVENTORY_ID", length = 50)
	private String inventoryID;
	
	@Column(name = "STORED_AT")
	@Temporal(TemporalType.DATE)
	private Date storedAt;
	
	@Column(name = "LAST_GOOD_UPDATE")
	@Temporal(TemporalType.DATE)
	private Date lastGoodUpdate;
	
	@Column(name = "VALUE", precision=10, scale=2)
	private double value;
	
	@Column(name = "STATE")
	private int state;
	
	@Column(name = "UNIT", length = 4)
	private String unit;

	public Date getStoredAt() {
		return storedAt;
	}

	public void setStoredAt(Date storedAt) {
		this.storedAt = storedAt;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getLastGoodUpdate() {
		return lastGoodUpdate;
	}

	public void setLastGoodUpdate(Date lastGoodUpdate) {
		this.lastGoodUpdate = lastGoodUpdate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getInventoryID() {
		return inventoryID;
	}

	public void setInventoryID(String inventoryID) {
		this.inventoryID = inventoryID;
	}

	public Long getSensorID() {
		return sensorID;
	}

	public void setSensorID(Long sensorID) {
		this.sensorID = sensorID;
	}

}
