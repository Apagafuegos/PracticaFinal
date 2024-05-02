package app;

import equiposService.EquiposService;
import equiposService.EquiposServiceException;
import equiposService.NotFoundException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                                throw new RuntimeException(ex);
                            }
                        } catch (EquiposServiceException ex) {
                            JOptionPane.showMessageDialog(pantallaInicio, ex.getMessage());
                        }
                    }else {
                        JOptionPane.showMessageDialog(panel, "Operación cancelada");
                    }
                    //String codigoEquipo = JOptionPane.showInputDialog(pantallaInicio, "Introduce el nombre del equipo a consultar", "Consulta de equipo", JOptionPane.QUESTION_MESSAGE);


            }
        });
    }
}
