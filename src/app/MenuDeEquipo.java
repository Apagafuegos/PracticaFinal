package app;

import equiposService.EquiposService;
import equiposService.EquiposServiceException;
import equiposService.NotFoundException;
import model.Equipo;
import model.Jugador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MenuDeEquipo extends JFrame {
    private JList<Jugador> listaJugadores;
    private JPanel jugadoresPanel;
    private JScrollPane scrollJugadores;
    private JLabel equipoLabel;
    private JButton volverButton;
    private JButton borrarButton;
    private JButton addButton;
    private JButton edadMediaButton;
    private JButton exportarButton;
    private static final EquiposService equiposService = new EquiposService();
    private static Equipo equipo = null;

    public MenuDeEquipo(String codigoEquipo) {
        setTitle("Gestión de equipos - Equipo");
        try {
            equipo = equiposService.consultarEquipoCompleto(codigoEquipo);
        } catch (EquiposServiceException | NotFoundException e) {
            throw new RuntimeException(e);
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(jugadoresPanel);
        jugadoresPanel.setBackground(new Color(43, 45, 48));
        jugadoresPanel.setForeground(new Color(196, 196, 196));
        setSize(650, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        listaJugadores.setBackground(new Color(43, 45, 48));
        listaJugadores.setForeground(new Color(196, 196, 196));
        listaJugadores.setFont(listaJugadores.getFont().deriveFont(18.0f));
        scrollJugadores.setBackground(new Color(43, 45, 48));
        try {
            equipoLabel.setText(equiposService.consultarEquipoCompleto(codigoEquipo).getNombre());
        } catch (EquiposServiceException | NotFoundException e) {
            throw new RuntimeException(e);
        }
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


        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Inicio();
            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    equiposService.borrarEquipoCompleto(codigoEquipo);
                    dispose();
                    new Inicio();
                } catch (NotFoundException | EquiposServiceException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JTextField nombreJugador = new JTextField();
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    JFormattedTextField fechaNacimiento = new JFormattedTextField(df);
                    JPanel panel = new JPanel(new GridLayout(0, 1));
                    panel.add(new JLabel("Nombre del jugador"));
                    panel.add(nombreJugador);
                    panel.add(new JLabel("Fecha de nacimiento"));
                    panel.add(fechaNacimiento);
                    int result = JOptionPane.showConfirmDialog(null, panel, "Introduzca los detalles del jugador",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        Jugador j = new Jugador(codigoEquipo, nombreJugador.getText(), LocalDate.parse(fechaNacimiento.getText(), formatter));
                        equiposService.añadirJugadorAEquipo(equipo, j);
                        modelo.addElement(j);
                    }

                } catch (EquiposServiceException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        edadMediaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "La edad media del equipo es de " + equipo.getEdadMedia() + " años.",
                        "Edad media del equipo", JOptionPane.INFORMATION_MESSAGE);

            }
        });
        exportarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showSaveDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    try {
                        File dir = fileChooser.getSelectedFile();
                        String filename = "\\"+codigoEquipo+".txt";
                        equiposService.exportarJugadores(codigoEquipo, dir.getAbsolutePath() + filename);
                        JOptionPane.showMessageDialog(null, "Archivo generado con éxito", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (EquiposServiceException | NotFoundException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}
