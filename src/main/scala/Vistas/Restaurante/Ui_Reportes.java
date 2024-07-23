package Vistas.Restaurante;

import scala.Int;

import javax.management.remote.JMXConnectorFactory;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Ui_Reportes extends JFrame {
    private JPanel grbPrincipal;
    private JLabel lblMesInicio;
    public JComboBox<Integer> cmbMesInicio;
    public JButton btnObtenerReporte;
    private JTable reportes;
    public DefaultTableModel jgdReportes;
    private JLabel lblAnoInicio;
    public JComboBox<Integer> cmbAnoInicio;
    private JLabel lblMesFin;
    private JLabel lblAnoFin;
    public JComboBox<Integer> cmbMesFin;
    public JComboBox<Integer> cmbAnoFin;

    public Ui_Reportes(){
        initPanelPrincipal();
    }

    private void initPanelPrincipal(){
        setTitle("Reportes");
        setVisible(true);
        setResizable(false);
        setSize(1200, 500);

        setLocationRelativeTo(null);
        setContentPane(grbPrincipal);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public void initTableReporteDinero(){
        jgdReportes = new DefaultTableModel(new String[]{"Mes-Año", "Dinero generado"}, 0);
        reportes.setModel(jgdReportes);
    }

    public void initTableReporteProductos(){
        jgdReportes = new DefaultTableModel(new String[]{"Mes-Año", "Producto", "Cantidad producto"}, 0);
        reportes.setModel(jgdReportes);
    }

    public void initTableReporteConcurrencia(){
        jgdReportes = new DefaultTableModel(new String[]{"Mes-Año", "Día", "Cantidad reservas"}, 0);
        reportes.setModel(jgdReportes);
    }

}
