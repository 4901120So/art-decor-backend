package com.example.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="productos")

public class Producto {

	@Id
	@Column(name= "Id_prod")
	private int Id_Producto;
	@Column(name= "nom_prod")
	private String name;
	@Column(name= "desc_prod")
	private String descripcion;
	@Column(name= "col_prod")
	private String color;
	@Column(name= "dim_prod")
	private String dimension;
	@Column(name= "stoc_prod")
	private int stock;
	@Column(name= "pre_prod")
	private String precio;
	
	public Producto() {
		super();
	}
	public Producto(int id_Producto, String name, String descripcion, String color, String dimension, int stock,
			String precio) {
		super();
		Id_Producto = id_Producto;
		this.name = name;
		this.descripcion = descripcion;
		this.color = color;
		this.dimension = dimension;
		this.stock = stock;
		this.precio = precio;
	}
	@JsonProperty("Id_Producto")
	public int getId_Producto() {
		return Id_Producto;
	}

	@JsonProperty("Id_Producto")
	public void setId_Producto(int id_Producto) {
		Id_Producto = id_Producto;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	
	
	
	
	

}
