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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Inicio extends JFrame {

    private JLabel tituloLabel;
    private JButton createButton;
    private JButton consultarUnEquipoPorButton;
    private JButton eqRegistradosButton;
    private JPanel pantallaInicio;
    private final EquiposService equiposService = new EquiposService();

    public Inicio() {
        setTitle("Gestión de equipos - Inicio");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(pantallaInicio);
        setSize(1000,700);
        setLocationRelativeTo(null);
        setVisible(true);
        eqRegistradosButton.setBackground(new java.awt.Color(43, 45, 48));
        consultarUnEquipoPorButton.setBackground(new java.awt.Color(43, 45, 48));
        createButton.setBackground(new java.awt.Color(43, 45, 48));
        eqRegistradosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new EquiposRegistrados();
            }
        });

        consultarUnEquipoPorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    JTextField codigoEquipoField = new JTextField();
                    JPanel panel = new JPanel(new GridLayout(0, 1));
                    panel.add(new JLabel("Introduce el código del equipo a consultar"));
                    panel.add(codigoEquipoField);
                    int result = JOptionPane.showConfirmDialog(null, panel, "Consulta de equipo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if(result == JOptionPane.OK_OPTION){
                        try {
                            try {
                                equiposService.consultarEquipoCompleto(codigoEquipoField.getText());
                                dispose();
                                new MenuDeEquipo(codigoEquipoField.getText());
                            } catch (NotFoundException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (EquiposServiceException ex) {
                            JOptionPane.showMessageDialog(pantallaInicio, ex.getMessage());
                        }
                    }else {
                        JOptionPane.showMessageDialog(panel, "Operación cancelada");
                    }


            }
        });
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField codigoEquipo = new JTextField();
                JTextField nombreEquipo = new JTextField();
                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Código del Equipo"));
                panel.add(codigoEquipo);
                panel.add(new JLabel("Nombre del Equipo"));
                panel.add(nombreEquipo);
                int result = JOptionPane.showConfirmDialog(null, panel, "Introduzca los detalles del jugador",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if(result == JOptionPane.OK_OPTION){
                    JTextField nombreJugador = new JTextField();
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    JFormattedTextField fechaNacimiento = new JFormattedTextField(df);
                    JPanel panel2 = new JPanel(new GridLayout(0, 1));
                    panel2.add(new JLabel("Nombre del jugador"));
                    panel2.add(nombreJugador);
                    panel2.add(new JLabel("Fecha de nacimiento"));
                    panel2.add(fechaNacimiento);
                    int result2 = JOptionPane.showConfirmDialog(null, panel2, "Introduzca un jugador",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result2 == JOptionPane.OK_OPTION) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        Equipo equipo = new Equipo();
                        equipo.setCodigo(codigoEquipo.getText());
                        equipo.setNombre(nombreEquipo.getText());
                        equipo.getListaJugadores().add(new Jugador(
                                equipo.getCodigo(),
                                nombreJugador.getText(),
                                LocalDate.parse(fechaNacimiento.getText(), formatter))
                        );
                        try {
                            equiposService.crearEquipo(equipo);
                            JOptionPane.showMessageDialog(null, "¡Equipo añadido!", "", JOptionPane.INFORMATION_MESSAGE);
                        } catch (EquiposServiceException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }




                }
            }
        });
    }
}
