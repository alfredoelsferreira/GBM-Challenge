/*
 * Copyright 2018 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author Alfredo Ferreira
 * @email alfredferreira@gmail.com
 *
 */
 

package com.gbm.fullstack.controller;


import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gbm.fullstack.dto.GBMApiResponse;
import com.gbm.fullstack.dto.GBMApiResponse.ApiError;
import com.gbm.fullstack.dto.GBMApiResponse.Status;
import com.gbm.fullstack.dto.GBMListApiResponse;
import com.gbm.fullstack.dto.ProductDTO;
import com.gbm.fullstack.dto.error.ErrorCode;
import com.gbm.fullstack.model.Product;
import com.gbm.fullstack.service.ProductQuatityService;
import com.gbm.fullstack.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/v1/products")
@Api(tags={ "Products" }, description = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Products.")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductQuatityService productQuantityService;
	
	private final String DATA_TYPE = Product.class.getSimpleName().toLowerCase();
	
	/**
	 * Returns list of all Products in the system.
	 * 
	 * @param pageRequest
	 * @return
	 */
	@ApiOperation(
			value = "Returns list of all Products in the system.",
			consumes = "application/json", produces = "application/json")
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_USER')")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllProducts(Pageable pageRequest) {
		logger.info("getAllProducts");
		Page<Product> products = productService.getAllProducts(pageRequest);
		if (products.getSize() > 0) {
			return new ResponseEntity<>(
	                new GBMListApiResponse(
	                        Status.OK, ProductDTO.getProductDTOListFromEntity(products), null, products, DATA_TYPE
	                ),
	                HttpStatus.OK
	        );
		}
		
		// return an empty list 
		return new ResponseEntity<>(
				new GBMApiResponse<>(Status.OK, products, null, DATA_TYPE),
				HttpStatus.OK);
	}
	
	/**
	 * Returns a specific product by their identifier. 404 if does not exist.
	 * 
	 * @param productId
	 * @return
	 */
	@ApiOperation("Returns a specific product by their identifier. 404 if does not exist.")
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_USER')")
	@RequestMapping(value="/{productId}", method = RequestMethod.GET)
	public ResponseEntity<?> getProductById(
			@ApiParam("Id of the product to be obtained. Cannot be empty.")
			@PathVariable UUID productId) {
		logger.info("getProductById " + productId);
		Product product = productService.getProductById(productId);
		if (product == null) {
			return new ResponseEntity<>(
					new GBMApiResponse<>(Status.ERROR, null, new ApiError(ErrorCode.ERROR_NOT_FOUND), DATA_TYPE),
					HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(
                new GBMApiResponse<ProductDTO>(
                        Status.OK, new ProductDTO(product), null, DATA_TYPE
                ),
                HttpStatus.OK
        );
	}
	
	/**
	 * Creates a new product.
	 * 
	 * @param productDTO
	 * @return
	 */
	@ApiOperation("Creates a new product.")
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
	@RequestMapping (method = RequestMethod.POST)
	public ResponseEntity<?> addProduct(
			@ApiParam("Product information for a new product to be created.")
			@RequestBody ProductDTO productDTO) {
		logger.info("addProduct " + productDTO.toString());
		productService.addObserver(productQuantityService);
		
		Product product = productService.addProduct(productDTO);
		
		
		productService.removeObserver(productQuantityService);
		return new ResponseEntity<>(
                new GBMApiResponse<ProductDTO>(
                        Status.OK, new ProductDTO(product), null, DATA_TYPE
                ),
                HttpStatus.ACCEPTED
        );
	}
	
	/**
	 * Updates a product from the system. 404 if the product's identifier is not found.
	 * 
	 * @param productDTO
	 * @return
	 */
	@ApiOperation("Updates a product from the system. 404 if the product's identifier is not found.")
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
	@RequestMapping (value = "/{productId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProduct(
			@ApiParam("Id of the product to be patch. Cannot be empty.")
			@PathVariable UUID productId, 
			@ApiParam("Product information to update or create new product.")
			@RequestBody ProductDTO productDTO) {
		logger.info("updateProduct " + productDTO.toString());
		productService.addObserver(productQuantityService);
		
		Product product = productService.updateProduct(productId, productDTO);
		productService.removeObserver(productQuantityService);
		if (product == null) {
			return new ResponseEntity<>(
					new GBMApiResponse<>(Status.ERROR, null, new ApiError(ErrorCode.ERROR_NOT_FOUND), DATA_TYPE),
					HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(
                new GBMApiResponse<ProductDTO>(
                        Status.OK, new ProductDTO(product), null, DATA_TYPE
                ),
                HttpStatus.ACCEPTED
        );
	}
	
	
	/**
	 * Updates a product partially from the system. 404 if the product's identifier is not found.
	 * 
	 * @param productId
	 * @param productDTO
	 * @return
	 */
	@ApiOperation("Updates a product partially from the system. 404 if the product's identifier is not found.")
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
	@RequestMapping (value = "/{productId}", method = RequestMethod.PATCH)
	public ResponseEntity<?> patchProduct(
			@ApiParam("Id of the product to be patch. Cannot be empty.")
			@PathVariable UUID productId, 
			@ApiParam("Product information to update.")
			@RequestBody ProductDTO productDTO) {
		logger.info("patchProduct " + productDTO.toString());
		productService.addObserver(productQuantityService);
		
		Product product = productService.patchProduct(productId, productDTO);
		productService.removeObserver(productQuantityService);
		if (product == null) {
			return new ResponseEntity<>(
					new GBMApiResponse<>(Status.ERROR, null, new ApiError(ErrorCode.ERROR_NOT_FOUND), DATA_TYPE),
					HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(
                new GBMApiResponse<ProductDTO>(
                        Status.OK, new ProductDTO(product), null, DATA_TYPE
                ),
                HttpStatus.ACCEPTED
        );
	}
	
	
	/**
	 * Deletes a product from the system. 404 if the product's identifier is not found.
	 * 
	 * @param productId
	 * @return
	 */
	@ApiOperation("Deletes a product from the system. 404 if the product's identifier is not found.")
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
	@RequestMapping (value = "/{productId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProduct(
			@ApiParam("Id of the product to be deleted. Cannot be empty.")
			@PathVariable UUID productId) {
		logger.info("deleteProduct " + productId);
		
		Product product = productService.deleteProduct(productId);
		if (product == null) {
			return new ResponseEntity<>(
					new GBMApiResponse<>(Status.ERROR, null, new ApiError(ErrorCode.ERROR_NOT_FOUND), DATA_TYPE),
					HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(
                new GBMApiResponse<ProductDTO>(
                        Status.OK, new ProductDTO(product), null, DATA_TYPE
                ),
                HttpStatus.ACCEPTED
        );
	}

}
