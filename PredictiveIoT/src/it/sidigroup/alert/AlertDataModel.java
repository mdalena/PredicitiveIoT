package it.sidigroup.alert;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "paramONE" })
@XmlRootElement(name = "AlertDataModel")
public class AlertDataModel {

    @XmlElement(required = true)
    private BigDecimal paramONE;

    public BigDecimal getParamONE() {
        return this.paramONE;
    }

    public void setParamONE(BigDecimal paramONE) {
        this.paramONE= paramONE;
    }

}