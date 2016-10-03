package it.sidigroup.persistence;

import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="sensor")
@NamedQueries({
	@NamedQuery(name = "getListOfSensors", query = "select s from sensor s"), 
	@NamedQuery(name = "searchByName", query = "select s from sensor s where s.name= :nameSensor"),
	@NamedQuery(name = "searchByID", query = "select s from sensor s where s.inventoryID= :inventoryID"),
	@NamedQuery(name = "deleteAllSensors", query = "delete from sensor"),
	@NamedQuery(name = "getLastGoodReading", query = "select s from sensor s where s.lastGoodUpdate = "
			+ "(select sm.storedAt from singleMeasurement sm where s.inventoryID = sm.inventoryID and sm.storedAt = sm.lastGoodUpdate)"),
	@NamedQuery(name = "lastReadingFromSensors", query = "select s from sensor s, singleMeasurement sm where s.inventoryID = sm.inventoryID order by sm.storedAt DESC"),
})
@Table(name = "SENSOR")
public class Sensor implements Serializable {
	/*	[{
		 	"type":"double",								//ignore
			"writeable":1,									//ignore
			
			"unit":"°C",									//measurement
			"value":"19.65",								//measurement
			"state":0,										//measurement 0 ok, 1-->grave, 2->gravissimo, 4->non riesco ad interrogare il Modbus (errore interno)
			"lastGoodUpdate":"Set 12, 2016 9:20:38 AM",		//measure
			"lastUpdate":"Set 12, 2016 9:20:38 AM"			//measure

			"id":"Modbus_Temperature_1",					//sensor
			"inventoryID":"temp_mandata_1",					//sensor
			"name":"TemperaturaMandata",					//sensor
			"description":"Temperatura di Mandata",			//sensor
		}]
	*/
	
	private static final long serialVersionUID = -334263965386990848L;

	@Id
	@GeneratedValue(strategy = AUTO)
	private long id;

	@Column(name = "INVENTORY_ID", length = 50)
	private String inventoryID;
	
	@Column(name = "NAME", length = 50)
	private String name;

	@Column(name = "DESCRIPTION", length = 150)
	private String description;

	@Column(name = "LAST_GOOD_UPDATE", length = 50)
	@Temporal(TemporalType.DATE)
	private Date lastGoodUpdate;

	@OneToMany(cascade=CascadeType.PERSIST, orphanRemoval=true)
    @JoinColumn(name="SENSOR_ID")
	private Collection<SingleMeasurement> measurements;

	public Sensor(){};
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String param) {
		this.description = param;
	}

	public Collection<SingleMeasurement> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(Collection<SingleMeasurement> param) {
		this.measurements = param;
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

	public Date getLastGoodUpdate() {
		return lastGoodUpdate;
	}

	public void setLastGoodUpdate(Date lastGoodUpdate) {
		this.lastGoodUpdate = lastGoodUpdate;
	}
}
