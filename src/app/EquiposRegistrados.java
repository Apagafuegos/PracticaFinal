package app;

import equiposService.EquiposService;
import equiposService.EquiposServiceException;
import model.Equipo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EquiposRegistrados extends JFrame {
    private JLabel equiposRegistradosLabel;
    private JList<Equipo> listaEquipos;
    private JPanel equiposRegistrados;
    private JScrollPane scrollEquipos;
    private JButton backButton;
    private static final EquiposService equiposService = new EquiposService();

    public EquiposRegistrados() {
        setTitle("Gestión de equipos - Equipos Registrados");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(equiposRegistrados);
        setSize(500,700);
        setLocationRelativeTo(null);
        setVisible(true);
        listaEquipos.setBackground(new Color(43,45,48));
        listaEquipos.setForeground(new Color(196, 196, 196));
        scrollEquipos.setBackground(new Color(43,45,48));
        backButton.setBackground(new Color(43,45,48));
        backButton.setForeground(new Color(196, 196, 196));
        DefaultListModel<Equipo> modelo = new DefaultListModel<>();
        try {
            equiposService.consultarEquipos().forEach(modelo::addElement);
        } catch (EquiposServiceException e) {
            System.err.println(e.getMessage());
        }
        listaEquipos.setModel(modelo);
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        listaEquipos.setCellRenderer(renderer);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Inicio();
            }
        });
    }
}
