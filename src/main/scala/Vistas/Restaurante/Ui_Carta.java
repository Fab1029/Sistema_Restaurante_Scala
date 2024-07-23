package Vistas.Restaurante;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Ui_Carta extends JFrame{
    private JPanel grbPrincipal;
    public JTabbedPane tbCarta;
    private JPanel grbIngresar;
    private JPanel grbModificar;
    private JPanel grbEliminar;
    private JPanel grbListar;
    private JLabel lblNombre;
    public JTextField txtNombreIngresar;
    public JComboBox<String> cmbProductosIngresar;
    public JButton btnAgregarProductoIngresar;
    public JComboBox<String> cmbEliminarProductoIngresar;
    public JButton btnEliminarProductoIngresar;
    private JLabel lblAgregarProductoIngresar;
    private JLabel lblEliminarProductoIngresar;
    private JLabel lblFechaValidezIngresar;
    private JPanel grbFechaValidezIngresar;
    public JDateChooser dtpFechaValidezIngresar;
    public JButton btnIngresarCarta;
    public JButton btnGuardarCambios;
    private JLabel lblNombreModificar;
    public JComboBox<String> cmbNombreModificar;
    private JLabel lblAgregarProductoModificar;
    private JLabel lblEliminarProductoModificar;
    private JLabel lblFechaValidezModificar;
    public JComboBox<String> cmbProductosModificar;
    public JButton btnAgregarProductoModificar;
    public JComboBox<String> cmbEliminarProductoModificar;
    public JButton btnEliminarProductoModificar;
    private JPanel grbFechaValidezModificar;
    public JDateChooser dtpFechaValidezModificar;
    private JLabel lblNombreEliminar;
    public JComboBox<String> cmbNombreEliminar;
    public JButton btnEliminarCarta;
    private JTable cartas;
    public DefaultTableModel jgdCartas;

    public Ui_Carta(){
        initPanelPrincipal();
        initTable();
        initDates();
    }

    private void initPanelPrincipal(){
        setTitle("Carta");
        setVisible(true);
        setResizable(false);
        setSize(1200, 500);

        setLocationRelativeTo(null);
        setContentPane(grbPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void initTable() {
        jgdCartas = new DefaultTableModel(new String[]{"Nombre carta", "Fecha validez"}, 0);
        cartas.setModel(jgdCartas);
    }

    private void initDates(){
        dtpFechaValidezIngresar = new JDateChooser();
        dtpFechaValidezIngresar.setDateFormatString("yyyy-MM-dd");
        grbFechaValidezIngresar.add(dtpFechaValidezIngresar);

        dtpFechaValidezModificar = new JDateChooser();
        dtpFechaValidezModificar.setDateFormatString("yyyy-MM-dd");
        grbFechaValidezModificar.add(dtpFechaValidezModificar);

    }

}
