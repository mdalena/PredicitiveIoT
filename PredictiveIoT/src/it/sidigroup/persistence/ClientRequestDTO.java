package it.sidigroup.persistence;

public class ClientRequestDTO {

	/*
		[{"type":"double",
		"unit":"°C",
		"value":"19.65",
		"writeable":1,
		"state":0,
		"id":"Modbus_Temperature_1",
		"inventoryID":"temp_mandata_1",
		"name":"TemperaturaMandata",
		"description":"Temperatura di Mandata",
		"lastGoodUpdate":"Set 12, 2016 9:20:38 AM",
		"lastUpdate":"Set 12, 2016 9:20:38 AM"}]

	 */
	
	private String dataType;
	private String unit;
	private String value;
	private String inventoryID;
	private String name;
	private String description;
	private String lastGoodUpdate;
	private String lastUpdate;
	
	private int writeable;
	private int state;
	private int id;
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
	public String getLastGoodUpdate() {
		return lastGoodUpdate;
	}
	public void setLastGoodUpdate(String lastGoodUpdate) {
		this.lastGoodUpdate = lastGoodUpdate;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public int getWriteable() {
		return writeable;
	}
	public void setWriteable(int writeable) {
		this.writeable = writeable;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
}
