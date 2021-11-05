package com.naveen.redis.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.redis.entity.Product;
import com.naveen.redis.repository.ProductDao;

@RestController
@RequestMapping("/product")
@EnableCaching
public class ProductController {
	
	@Autowired
	private ProductDao productDao;
	
	@PostMapping
	public Product saveProduct(@RequestBody Product product)
	{
		return productDao.save(product);
	}
	@GetMapping
	public List<Product> getProducts()
	{
		return productDao.findAll();
	}
	
	@GetMapping("/{id}")
	@Cacheable(key="#id",value="Product", unless="#result.price>1000")
	public Product findById(@PathVariable("id") int id)
	{
		return productDao.findProductById(id);
	}

	@DeleteMapping("/{id}")
	@CacheEvict(key="#id",value="Product")
	public String delete(@PathVariable("id") int id)
	{
		return productDao.deleteProduct(id);
	}
}
