package com.gbm.fullstack.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.domain.Page;

import com.gbm.fullstack.model.Product;
import com.gbm.fullstack.model.Product.ProductBuilder;

import io.swagger.annotations.ApiModelProperty;

public class ProductDTO {
	@NotNull
	@ApiModelProperty(
			notes = "Unique identifier of the product. No two products can have the same id.", 
			example = "40cd4842-910a-45f3-a4be-31624e5a3a0a", 
			required = true, 
			position = 0)
	private UUID id;
	
	@NotNull
	@Size(min = 1, message = "{Size.ProductDto.name}")
	@ApiModelProperty(
			notes = "Product name.", 
			example = "Italian Plates", 
			required = true, 
			position = 1)
	private String name;
	
	@NotNull
	@Size(min = 1, message = "{Size.ProductDto.description}")
	@ApiModelProperty(
			notes = "Product description.", 
			example = "Blue and white Italian Plates", 
			required = true, 
			position = 2)
	private String description;
	
	@NotNull
	@Min(0)
	@ApiModelProperty(
			notes = "Product price.", 
			example = "149.99", 
			required = true, 
			position = 3)
	private Double price;
	
	@NotNull
	@Min(0)
	@ApiModelProperty(
			notes = "Product warehouse quantity.", 
			example = "25", 
			required = true, 
			position = 4)
	private Integer quantity;
	
	
	/**
	 * default constructor
	 */
	public ProductDTO() {
	}

	/**
	 * constructor using product 
	 * 
	 * @param product
	 */
	public ProductDTO(Product product) {
		this.id = product.getProductId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.quantity = product.getQuantity();
	}

	/**
	 * constructor using fields
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param price
	 * @param amount
	 */
	public ProductDTO(UUID id, String name, String description, Double price, Integer quantity) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}

	/**
	 * 
	 * @return UUID
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 * @return ProductDTO
	 */
	public ProductDTO setId(UUID id) {
		this.id = id;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public ProductDTO setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 * @return
	 */
	public ProductDTO setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * 
	 * @param price
	 * @return
	 */
	public ProductDTO setPrice(Double price) {
		this.price = price;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 
	 * @param quantity
	 * @return
	 */
	public ProductDTO setQuantity(Integer quantity) {
		this.quantity = quantity;
		return this;
	}
	
	public Product toModel() {
		return new ProductBuilder(this.id, this.name, this.price, this.quantity)
				.withOptionalDescription(this.description)
				.build();
	}
	
	public static List<Object> getProductDTOListFromEntity(Page<Product> products) {
		List<Object> objectList = new ArrayList<>();
		for (Product product : products) {
			objectList.add(new ProductDTO(product));
		}
		
		return objectList;
	}
	
}
