package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class Equipo {

	private String codigo;
	private String nombre;
	private List<Jugador> listaJugadores;	

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Jugador> getListaJugadores() {
		return listaJugadores;
	}

	public void setListaJugadores(List<Jugador> listaJugadores) {
		this.listaJugadores = listaJugadores;
	}

	@Override
	public String toString() {
		return codigo + "/" + nombre;
	}

	public BigDecimal getEdadMedia() {
		BigDecimal edadMedia = BigDecimal.ZERO;	
		for (Jugador jugador : listaJugadores) {
			Period edadPeriodo = Period.between(jugador.getFechaNacimiento(), LocalDate.now());
			BigDecimal edad = BigDecimal.valueOf((double) edadPeriodo.getYears()
                    + edadPeriodo.getMonths() / 12D + edadPeriodo.getDays() / 365D);
			edadMedia = edadMedia.add(edad);
		}
		return edadMedia.divide(new BigDecimal(listaJugadores.size()), RoundingMode.HALF_EVEN);
	}

}
