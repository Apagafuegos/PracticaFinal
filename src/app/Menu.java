package app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import equiposService.EquiposService;
import equiposService.EquiposServiceException;
import equiposService.NotFoundException;
import model.Equipo;
import model.Jugador;

public class Menu {

	private static final Scanner SC = new Scanner(System.in);
	private static final EquiposService ES = new EquiposService();
	private static final Gui GUI = new Gui();

	public void menu() {

		Integer opc = null;
		do {
			GUI.opciones();
			opc = SC.nextInt();
			SC.nextLine();
			switch (opc) {
			case 1:
				try {
					ES.consultarEquipos().stream().forEach(e -> System.out.println(e));
				} catch (EquiposServiceException e) {
					System.err.println(e);
				}
				break;
			case 2:
				Equipo eq = new Equipo();
				GUI.introducirCodigoEquipo();
				eq.setCodigo(SC.nextLine());
				GUI.introducirNombreEquipo();
				eq.setNombre(SC.nextLine());
				List<Jugador> listaJugadores = new ArrayList<>();
				String opc2 = null;
				do {
					GUI.introducirNombreJugador();
					String nombre = SC.nextLine();
					GUI.fechaNacimiento();
					LocalDate fecha = LocalDate.of(SC.nextInt(), SC.nextInt(), SC.nextInt());
					SC.nextLine();
					listaJugadores.add(new Jugador(eq.getCodigo(), nombre, fecha));
					GUI.otroJugador();
					opc2 = SC.nextLine();
				} while (!opc2.equalsIgnoreCase("n"));
				eq.setListaJugadores(listaJugadores);
				try {
					ES.crearEquipo(eq);
					GUI.equipoGuardado();
				} catch (EquiposServiceException e) {
					System.err.println(e);
				}
				break;

			case 3:
				menuEquipo();
				break;
			case 0:
				break;
			default:
				GUI.opcionIncorrecta();
				break;
			}

		} while (opc != 0);
	}

	private void menuEquipo() {
		GUI.introducirCodigoEquipo();
		String codigo = SC.nextLine();
		try {
			ES.consultarEquipoCompleto(codigo);

		} catch (EquiposServiceException e) {
			e.printStackTrace();
		} catch (NotFoundException e2) {
			GUI.noExisteEquipo();
			return;
		}
		Integer opc = null;
		do {
			GUI.opcionesEquipo();
			opc = SC.nextInt();
			SC.nextLine();

			switch (opc) {
			case 1:
				try {
					Equipo eq = ES.consultarEquipoCompleto(codigo);
					System.out.println(eq);
					for (Jugador jugador : eq.getListaJugadores()) {
						System.out.println(jugador);
					}
				} catch (EquiposServiceException | NotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					ES.borrarEquipoCompleto(codigo);
					GUI.equipoBorrado();
					return;
				} catch (NotFoundException | EquiposServiceException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				GUI.introducirNombreJugador();
				String nombre = SC.nextLine();
				GUI.fechaNacimiento();
				LocalDate fecha = LocalDate.of(SC.nextInt(), SC.nextInt(), SC.nextInt());
				Jugador j = new Jugador(codigo, nombre, fecha);
				try {
					ES.añadirJugadorAEquipo(ES.consultarEquipoCompleto(codigo), j);
					GUI.jugadorGuardado();
				} catch (EquiposServiceException | NotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					System.out.println("La edad media de la plantilla es de "
							+ ES.consultarEquipoCompleto(codigo).getEdadMedia() + " años.");
				} catch (EquiposServiceException | NotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 5:
				GUI.rutaFichero();
				String ruta = SC.nextLine();
				try {
					ES.exportarJugadores(codigo, ruta);
				} catch (EquiposServiceException | NotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 0:
				break;
			default:
				GUI.opcionIncorrecta();
			}

		} while (opc != 0);

	}
}