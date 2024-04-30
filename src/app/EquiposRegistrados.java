package app;

import equiposService.EquiposService;
import equiposService.EquiposServiceException;
import model.Equipo;

import javax.swing.*;

public class EquiposRegistrados extends JFrame {
    private JLabel equiposRegistradosLabel;
    private JList<Equipo> listaEquipos;
    private JPanel equiposRegistrados;
    private static final EquiposService equiposService = new EquiposService();

    public EquiposRegistrados() {
        setTitle("Gestión de equipos - Equipos Registrados");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(equiposRegistrados);
        setSize(500,200);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
        DefaultListModel<Equipo> modelo = new DefaultListModel<>();
        try {
            equiposService.consultarEquipos().forEach(modelo::addElement);
        } catch (EquiposServiceException e) {
            System.err.println(e.getMessage());
        }
        listaEquipos.setModel(modelo);
        listaEquipos.setCellRenderer(new DefaultListCellRenderer());

    }
}
