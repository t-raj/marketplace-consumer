//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.29 at 04:19:08 PM CST 
//


package jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="partnerId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numberAvailable" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pId",
    "price",
    "partnerId",
    "numberAvailable"
})
@XmlRootElement(name = "Product")
public class Product {

    protected int pId;
    protected int price;
    protected int partnerId;
    protected int numberAvailable;

    /**
     * Gets the value of the pId property.
     * 
     */
    public int getPId() {
        return pId;
    }

    /**
     * Sets the value of the pId property.
     * 
     */
    public void setPId(int value) {
        this.pId = value;
    }

    /**
     * Gets the value of the price property.
     * 
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     */
    public void setPrice(int value) {
        this.price = value;
    }

    /**
     * Gets the value of the partnerId property.
     * 
     */
    public int getPartnerId() {
        return partnerId;
    }

    /**
     * Sets the value of the partnerId property.
     * 
     */
    public void setPartnerId(int value) {
        this.partnerId = value;
    }

    /**
     * Gets the value of the numberAvailable property.
     * 
     */
    public int getNumberAvailable() {
        return numberAvailable;
    }

    /**
     * Sets the value of the numberAvailable property.
     * 
     */
    public void setNumberAvailable(int value) {
        this.numberAvailable = value;
    }

}