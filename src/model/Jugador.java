package model;

import java.time.LocalDate;

public class Jugador {

	private int numero;
	private String codigoEquipo;// FK de tabla equipos
	private String nombre;
	private LocalDate fechaNacimiento;

	public Jugador(int numero, String codigoEquipo, String nombre, LocalDate fechaNacimiento) {
		this.numero = numero;
		this.codigoEquipo = codigoEquipo;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
	}

	public Jugador(String codigoEquipo, String nombre, LocalDate fechaNacimiento) {
		this.codigoEquipo = codigoEquipo;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getCodigoEquipo() {
		return codigoEquipo;
	}

	public void setCodigoEquipo(String codigoEquipo) {
		this.codigoEquipo = codigoEquipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Override
	public String toString() {
		return numero + "-" + nombre + "-" + fechaNacimiento;
	}

}
