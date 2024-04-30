package app;

import equiposService.EquiposService;
import equiposService.EquiposServiceException;

import javax.swing.*;
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
        setTitle("Gestión de equipos - INICIO");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(pantallaInicio);
        setSize(500,200);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
        eqRegistradosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new EquiposRegistrados();
            }
        });
    }
}
