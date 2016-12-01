//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.01 at 04:23:09 PM CST 
//


package base.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numberAvailable" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="link" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="action" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="mediaType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="rel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "description",
    "numberAvailable",
    "link"
})
@XmlRootElement(name = "Product")
public class Product {

    protected int pId;
    protected int price;
    protected int partnerId;
    @XmlElement(required = true)
    protected String description;
    protected int numberAvailable;
    @XmlElement(required = true)
    protected List<Product.Link> link;

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
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
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

    /**
     * Gets the value of the link property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the link property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLink().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Product.Link }
     * 
     * 
     */
    public List<Product.Link> getLink() {
        if (link == null) {
            link = new ArrayList<Product.Link>();
        }
        return this.link;
    }


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
     *         &lt;element name="action" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="mediaType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="rel" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "action",
        "mediaType",
        "rel",
        "url"
    })
    public static class Link {

        @XmlElement(required = true)
        protected String action;
        @XmlElement(required = true)
        protected String mediaType;
        @XmlElement(required = true)
        protected String rel;
        @XmlElement(required = true)
        protected String url;

        /**
         * Gets the value of the action property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAction() {
            return action;
        }

        /**
         * Sets the value of the action property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAction(String value) {
            this.action = value;
        }

        /**
         * Gets the value of the mediaType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMediaType() {
            return mediaType;
        }

        /**
         * Sets the value of the mediaType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMediaType(String value) {
            this.mediaType = value;
        }

        /**
         * Gets the value of the rel property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRel() {
            return rel;
        }

        /**
         * Sets the value of the rel property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRel(String value) {
            this.rel = value;
        }

        /**
         * Gets the value of the url property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUrl() {
            return url;
        }

        /**
         * Sets the value of the url property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUrl(String value) {
            this.url = value;
        }

    }

}
