package app;

import equiposService.EquiposService;
import equiposService.EquiposServiceException;
import equiposService.NotFoundException;
import model.Jugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuDeEquipo extends JFrame {
    private JList<Jugador> listaJugadores;
    private JPanel jugadoresPanel;
    private JScrollPane scrollJugadores;
    private JButton backButton;
    private static final EquiposService equiposService = new EquiposService();

    public MenuDeEquipo(String codigoEquipo) {
        setTitle("Gestión de equipos - Equipo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(jugadoresPanel);
        setSize(500,700);
        setLocationRelativeTo(null);
        setVisible(true);
        listaJugadores.setBackground(new Color(43,45,48));
        listaJugadores.setForeground(new Color(196, 196, 196));
        scrollJugadores.setBackground(new Color(43,45,48));
        backButton.setBackground(new Color(43,45,48));
        backButton.setForeground(new Color(196, 196, 196));
        DefaultListModel<Jugador> modelo = new DefaultListModel<>();
        try {
            equiposService.consultarEquipoCompleto(codigoEquipo).getListaJugadores().forEach(modelo::addElement);
        } catch (EquiposServiceException | NotFoundException e) {
            System.err.println(e.getMessage());
        }
        listaJugadores.setModel(modelo);
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        listaJugadores.setCellRenderer(renderer);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Inicio();
            }
        });
    }
}
