package Vistas.Restaurante;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Ui_Reserva extends JFrame{
    private JPanel grbPrincipal;
    public JTabbedPane tbReserva;
    private JPanel grbIngresar;
    private JPanel grbModificar;
    private JPanel grbEliminar;
    private JPanel grbListar;
    private JLabel lblFechaIngresar;
    private JLabel lblAgregarProductoIngresar;
    private JLabel lblEliminarPedidoIngresar;
    private JPanel grbFechaIngresar;
    public JDateChooser dtpFechaIngresar;
    public JComboBox<String> cmbAgregarProductosIngresar;
    public JSpinner sbCantidadProductoIngresar;
    public JButton btnAgregarProductoIngresar;
    public JTextField txtNumeroReservaIngresar;
    private JLabel lblNumeroRservaIngresar;
    private JLabel lblTurnoIngresar;
    private JLabel lblNumeroComensalesIngresar;
    public JComboBox<String> cmbTurnoIngresar;
    public JSpinner sbNumeroComensalesIngresar;
    public JComboBox<String> cmbPedidoIngresar;
    public JSpinner sbCantidadPedidoIngresar;
    public JButton btnGuardarCambiosPedidoIngresar;
    public JButton btnEliminarPedidoIngresar;
    public JButton btnReservar;
    private JLabel lblNumeroReservaModificar;
    public JComboBox<String> cmbNumeroReservaModificar;
    private JPanel grbFechaModificar;
    public JDateChooser dtpFechaModificar;
    private JLabel lblFechaModificar;
    private JLabel lblTurnoModificar;
    private JLabel lblNumeroComensalesModificar;
    public JComboBox<String> cmbTurnoModificar;
    public JSpinner sbNumeroComensalesModificar;
    private JLabel lblAgregarProductoModificar;
    public JComboBox<String> cmbAgregarProductoModificar;
    public JSpinner sbCantidadProductoModificar;
    public JButton btnAgregarProductoModificar;
    private JLabel lblEliminarProductoModificar;
    public JComboBox<String> cmbPedidoModificar;
    public JSpinner sbCantidadPedidoModificar;
    public JButton btnGuardarCambiosPedidoModificar;
    public JButton btnEliminarPedidoModificar;
    public JButton btnGuardarCambios;
    private JLabel lblNumeroReservaEliminar;
    public JComboBox<String> cmbNumeroReservaEliminar;
    public JButton btnEliminarReserva;
    private JTable reservas;
    public DefaultTableModel jgdReservas;

    public Ui_Reserva(){
        initPanelPrincipal();
        initTable();
        initSpinner();
        initDates();
    }

    private void initPanelPrincipal(){
        setTitle("Reserva");
        setVisible(true);
        setResizable(false);
        setSize(1200, 500);

        setLocationRelativeTo(null);
        setContentPane(grbPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    private void initTable() {
        jgdReservas = new DefaultTableModel(new String[]{"Número reserva", "Fecha", "Turno", "Número comensales"}, 0);
        reservas.setModel(jgdReservas);
    }

    private void initSpinner(){
        sbCantidadPedidoIngresar.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        sbCantidadPedidoModificar.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        sbNumeroComensalesIngresar.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        sbCantidadProductoIngresar.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        sbNumeroComensalesModificar.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        sbCantidadProductoModificar.setModel(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
    }

    private void initDates(){
        dtpFechaIngresar = new JDateChooser();
        dtpFechaIngresar.setDateFormatString("yyyy-MM-dd");
        grbFechaIngresar.add(dtpFechaIngresar);

        dtpFechaModificar = new JDateChooser();
        dtpFechaModificar.setDateFormatString("yyyy-MM-dd");
        grbFechaModificar.add(dtpFechaModificar);

    }

}
