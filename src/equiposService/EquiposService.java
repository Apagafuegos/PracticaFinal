package equiposService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Equipo;
import model.Jugador;

public class EquiposService extends Dao {

	public List<Equipo> consultarEquipos() throws EquiposServiceException {
		List<Equipo> listaEquipos = new ArrayList<>();
		String sql = "select * from equipo";
		try (Connection conn = getNewConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Equipo eq = new Equipo();
				eq.setCodigo(rs.getString("codigo"));
				eq.setNombre(rs.getString("nombre"));
				listaEquipos.add(eq);
			}
			st.close();
		} catch (SQLException e) {
			throw new EquiposServiceException("Hubo un problema");
		}
		return listaEquipos;
	}

	private List<Jugador> consultarJugadoresEquipo(String codigo) throws SQLException {
		List<Jugador> listaJugadores = new ArrayList<>();
		String sql = "select * from jugador where codigo_equipo = ?";
		try (Connection conn = getNewConnection(); PreparedStatement st2 = conn.prepareStatement(sql)) {
			st2.setString(1, codigo);
			ResultSet rs2 = st2.executeQuery();
			while (rs2.next()) {
				listaJugadores.add(new Jugador(rs2.getInt("numero"), rs2.getString("codigo_equipo"),
						rs2.getString("nombre"), rs2.getDate("nacimiento").toLocalDate()));
			}
			st2.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw e;
		}
		return listaJugadores;
	}

	public Equipo consultarEquipoCompleto(String codigo) throws EquiposServiceException, NotFoundException {
		Equipo eq = new Equipo();
		eq.setCodigo(codigo);
		String sql = "select * from equipo where codigo = ?";
		try (Connection conn = getNewConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
			st.setString(1, codigo);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				eq.setCodigo(rs.getString("codigo"));
				eq.setNombre(rs.getString("nombre"));
				eq.setListaJugadores(consultarJugadoresEquipo(eq.getCodigo()));
			} else {
				throw new NotFoundException("No existe un equipo con ese codigo");
			}
			st.close();
		} catch (SQLException e) {
			throw new EquiposServiceException("Hubo un problema");
		}
		return eq;
	}

	private void insertarJugador(Connection conn, Jugador jugador) throws SQLException {
		String sql = "insert into jugador values (?,?,?,?)";
		try ( PreparedStatement st = conn.prepareStatement(sql)) {
			st.setInt(1, jugador.getNumero());
			st.setString(2, jugador.getCodigoEquipo());
			st.setString(3, jugador.getNombre());
			st.setDate(4, Date.valueOf(jugador.getFechaNacimiento()));
			st.executeUpdate();
			//st.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw e;
		}
	}

	public void crearEquipo(Equipo eq) throws EquiposServiceException {

		int i = 1;
		for (Jugador j : eq.getListaJugadores()) {
			j.setCodigoEquipo(eq.getCodigo());
			j.setNumero(i);
			i++;
		}

		String sql = "insert into equipo values (?,?)";
		try (Connection conn = getNewConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
			conn.setAutoCommit(false);
			try {
				st.setString(1, eq.getCodigo());
				st.setString(2, eq.getNombre());
				st.executeUpdate();
				//st.close();
				for (Jugador jugador : eq.getListaJugadores()) {
					insertarJugador(conn, jugador);
				}
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			throw new EquiposServiceException("Hubo un problema");
		}
	}

	public void borrarEquipoCompleto(String codigo) throws NotFoundException, EquiposServiceException {
		String sql = "delete from jugador where codigo_equipo = ?";
		try (Connection conn = getNewConnection(); PreparedStatement st = conn.prepareStatement(sql)) {
			conn.setAutoCommit(false);
			try {
				st.setString(1, codigo);
				st.executeUpdate();

				String sql2 = "delete from equipo where codigo = ?";
				try (conn; PreparedStatement st2 = conn.prepareStatement(sql2)) {
					try {
						st2.setString(1, codigo);
						if (st2.executeUpdate() == 0) {
							conn.rollback();
							throw new NotFoundException("No existe un equipo con dicho codigo");
						}
					} catch (SQLException e) {
						conn.rollback();
						throw e;
					}
					conn.commit();
				} catch (SQLException e) {
					conn.rollback();
					throw new EquiposServiceException("Hubo un problema");
				}
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}

		} catch (SQLException e) {
			throw new EquiposServiceException("Hubo un problema");
		}

	}

	public void añadirJugadorAEquipo(Equipo eq, Jugador j) throws EquiposServiceException {
		try {
			int numJug = consultarJugadoresEquipo(eq.getCodigo()).size();
			j.setCodigoEquipo(eq.getCodigo());
			j.setNumero(numJug + 1);
			insertarJugador(getNewConnection(), j);
		} catch (SQLException e) {
			throw new EquiposServiceException("Hubo un problema");
		}
	}

	public void exportarJugadores(String codigo, String ruta) throws EquiposServiceException, NotFoundException {
		File file = new File(ruta);
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(consultarJugadoresEquipo(codigo).toString());
			writer.close();
		} catch (IOException e) {
			throw new EquiposServiceException("Hubo un problema con el fichero");
		} catch (SQLException e2) {
			throw new NotFoundException("No existe un equipo con dicho codigo");
		}
	}
}
