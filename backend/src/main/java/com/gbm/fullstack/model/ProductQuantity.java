package com.gbm.fullstack.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="product_quantity")
public class ProductQuantity extends AbstractAuditingEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	
	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="product_quantity_id", updatable = false, nullable=false, columnDefinition = "BINARY(16)")
    private UUID productQuantityId;
	
	@Column(name="new_quantity", nullable=false)
    private Integer newQuantity;
	
	
	@ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;
	
	
	public ProductQuantity() {
		super();
	}

	public ProductQuantity(UUID productQuantityId, Integer newQuantity, Product product,
			Timestamp modifiedDate) {
		super();
		this.productQuantityId = productQuantityId;
		this.newQuantity = newQuantity;
		this.product = product;
	}

	public UUID getProductQuantityId() {
		return productQuantityId;
	}

	public void setProductQuantityId(UUID productQuantityId) {
		this.productQuantityId = productQuantityId;
	}

	public Integer getNewQuantity() {
		return newQuantity;
	}

	public void setNewQuantity(Integer newQuantity) {
		this.newQuantity = newQuantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}


	@Override
	public String toString() {
		return "ProductQuantity [productQuantityId=" + productQuantityId + ", newQuantity=" + newQuantity
				+ ", product=" + product  + "]";
	}

}
