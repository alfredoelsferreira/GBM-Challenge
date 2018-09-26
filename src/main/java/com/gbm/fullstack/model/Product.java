package com.gbm.fullstack.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="product")
public class Product extends AbstractAuditingEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="product_id", updatable = false, nullable=false, columnDefinition = "BINARY(16)")
    private UUID productId;

    @Column(name="name", nullable=false)
    private String name;
    
    @Column(name="description", nullable=true)
    private String description;
    
    @Column(name="price", nullable=false)
    private Double price;
    
    
    @Column(name="quantity", nullable=false)
    private Integer quantity;
    
    /**
     * Default constructor
     */
	public Product() {
	}
	
	public Product(ProductBuilder productBuilder) {
		this.productId = productBuilder.productId;
		this.name = productBuilder.name;
		this.description = productBuilder.description;
		this.price = productBuilder.price;
		this.quantity = productBuilder.quantity;
	}
	


	/**
	 * Constructor using fields 
	 * @param productId
	 * @param name
	 * @param description
	 * @param price
	 * @param amount
	 */
	public Product(UUID productId, String name, String description, Double price, Integer quantity) {
		super();
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}
	
	/**
	 * Constructor using fields 
	 * @param name
	 * @param description
	 * @param price
	 * @param amount
	 */
	public Product(String name, String description, Double price, Integer quantity) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}


	/**
	 * Product Id
	 * @return UUID productId
	 */
	public UUID getProductId() {
		return productId;
	}


	/**
	 * Set's product id
	 * @param productId
	 * @return Product
	 */
	public Product setProductId(UUID productId) {
		this.productId = productId;
		return this;
	}


	/**
	 * Product name
	 * @return String name
	 */
	public String getName() {
		return name;
	}


	/**
	 * Set's product name
	 * @param name
	 * @return Product
	 */
	public Product setName(String name) {
		this.name = name;
		return this;
	}


	/**
	 * Product description
	 * @return String description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * Set's product description
	 * @param description
	 * @return Product
	 */
	public Product setDescription(String description) {
		this.description = description;
		return this;
	}


	/**
	 * Product price
	 * @return Double price
	 */
	public Double getPrice() {
		return price;
	}


	/**
	 * Set's product price
	 * @param price
	 * @return Product
	 */
	public Product setPrice(Double price) {
		this.price = price;
		return this;
	}


	/**
	 * Product quantity
	 * @return Integer quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}


	/**
	 * Set's product quantity
	 * @param quantity
	 * @return Product
	 */
	public Product setQuantity(Integer quantity) {
		this.quantity = quantity;
		return this;
	}
	
	
	// Adding Builder Pattern
	public static class ProductBuilder {
		private UUID productId;
	    private String name;
	    private String description;
	    private Double price;
	    private Integer quantity;
	    
	    public ProductBuilder(UUID productId, String name, Double price, Integer quantity) {
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
	    
	    public ProductBuilder withOptionalDescription(String description) {
            this.description = description;
            return this;
        }
	    
	    public Product build() {
            isValidateProductData();
            return new Product(this);
        }
	    
	    private boolean isValidateProductData() {
            //Do some basic validations to check
            return true;
        }
 
	}
    
}
