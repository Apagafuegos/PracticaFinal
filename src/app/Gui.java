package app;

public class Gui {

	public void opciones() {
		System.out.println("Elige una de estas opciones:");
		System.out.println("\t>(1) Ver todos los equipos registrados");
		System.out.println("\t>(2) Crear nuevo equipo");
		System.out.println("\t>(3) Consultar un equipo por su codigo");
		System.out.println("\t>(0) Salir");
	}

	public void introducirCodigoEquipo() {
		System.out.println("Introduce el código del equipo");
	}

	public void introducirNombreEquipo() {
		System.out.println("Introduce el nombre del equipo");
	}
	
	public void introducirNombreJugador() {
		System.out.println("Indica el nombre del jugador");
	}
	
	public void fechaNacimiento() {
		System.out.println("Indica la fecha de nacimiento (aaaa/mm/dd)");
	}
	
	public void otroJugador() {
		System.out.println("¿Desea introducir otro jugador?");
	}
	
	public void equipoGuardado() {
		System.out.println("¡Equipo guardado!");
	}
	
	public void opcionIncorrecta() {
		System.err.println("Opción incorrecta");
	}
	
	public void noExisteEquipo() {
		System.err.println("No existe dicho equipo");
	}
	
	public void opcionesEquipo() {
		System.out.println("Elige una de estas opciones para el equipo:");
		System.out.println("\t>(1) Ver plantilla");
		System.out.println("\t>(2) Borrar equipo");
		System.out.println("\t>(3) Añadir un jugador");
		System.out.println("\t>(4) Calcular edad media");
		System.out.println("\t>(5) Exportar plantilla a un fichero");
		System.out.println("\t>(0) Volver al menú principal");
	}
	
	public void equipoBorrado() {
		System.out.println("¡Equipo borrado!");
	}
	
	public void jugadorGuardado() {
		System.out.println("¡Jugador añadido!");
	}
	
	public void rutaFichero() {
		System.out.println("Indica la ruta del fichero");
	}

}
