package Vistas.Restaurante;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

public class Ui_Disponibilidad extends JFrame{
    private JPanel grbPrincipal;
    public JTabbedPane tbDisponibilidad;
    private JPanel grbIngresar;
    private JPanel grbModificar;
    private JPanel grbEliminar;
    private JPanel grbListar;
    private JLabel lblFechaIngresar;
    public JButton btnIngresarDisponibilidad;
    public JComboBox<String> cmbTurnoIngresar;
    public JSpinner sbNumeroPlazasIngresar;
    private JPanel grbFechaIngresar;
    public JDateChooser dtpFechaIngresar;
    private JLabel lblTurnoIngresar;
    private JLabel lblNumeroPlazasIngresar;
    private JLabel lblFechaModificar;
    private JPanel grbFechaModificar;
    public JDateChooser dtpFechaModificar;
    private JLabel lblTurnoModificar;
    public JComboBox<String> cmbTurnoModificar;
    private JLabel lblNumeroPlazasModificar;
    public JSpinner sbNumeroPlazasModificar;
    public JButton btnGuardarCambios;
    private JPanel grbFechaEliminar;
    public  JDateChooser dtpFechaEliminar;
    public JTextField txtNumeroPlazasEliminar;
    private JLabel lblFechaEliminar;
    private JLabel lblTurnoEliminar;
    private JLabel lblNumeroPlazasEliminar;
    public JComboBox<String> cmbTurnoEliminar;
    public JButton btnEliminar;
    private JTable disponibilidad;
    public DefaultTableModel jgdDisponibilidad;

    public Ui_Disponibilidad(){
        initPanelPrincipal();
        initTable();
        initDates();
        initSpinner();
    }

    private void initPanelPrincipal(){
        setTitle("Disponibilidad");
        setVisible(true);
        setResizable(false);
        setSize(1200, 500);

        setLocationRelativeTo(null);
        setContentPane(grbPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void initTable() {
        jgdDisponibilidad = new DefaultTableModel(new String[]{"Fecha", "Turno", "Número plazas"}, 0);
        disponibilidad.setModel(jgdDisponibilidad);
    }

    private void initSpinner(){
        sbNumeroPlazasIngresar.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        sbNumeroPlazasModificar.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
    }

    private void initDates(){
        dtpFechaIngresar = new JDateChooser();
        dtpFechaIngresar.setDateFormatString("yyyy-MM-dd");
        grbFechaIngresar.add(dtpFechaIngresar);

        dtpFechaModificar = new JDateChooser();
        dtpFechaModificar.setDateFormatString("yyyy-MM-dd");
        grbFechaModificar.add(dtpFechaModificar);

        dtpFechaEliminar = new JDateChooser();
        dtpFechaEliminar.setDateFormatString("yyyy-MM-dd");
        grbFechaEliminar.add(dtpFechaEliminar);

    }

}
