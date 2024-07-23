package Vistas.Restaurante;

import javax.swing.*;

public class Ui_Main extends JFrame{
    private JPanel grbPrincipal;
    private JMenuBar jmbMenu;
    private JMenu jmbMenuPrincipal;
    public JMenuItem jmbConfiguracion;
    public JMenuItem jmbAcercaDe;
    public JMenuItem jmbSalir;
    private JMenu jmbReserva;
    private JMenu jmbDisponibilidad;
    public JMenu jmbCarta;
    public JMenuItem jmbReservaIngresar;
    public JMenuItem jmbReservaModificar;
    public JMenuItem jmbReservaEliminar;
    public JMenuItem jmbReservaListar;
    private JMenu jmbProducto;
    private JMenu jmbIngrediente;
    public JMenu jmbPersonal;
    public JMenu jmbReportes;
    public JMenuItem jmbDisponibilidadIngresar;
    public JMenuItem jmbDisponibilidadModificar;
    public JMenuItem jmbDisponibilidadEliminar;
    public JMenuItem jmbDisponibilidadListar;
    public JMenuItem jmbCartaIngresar;
    public JMenuItem jmbCartaModificar;
    public JMenuItem jmbCartaEliminar;
    public JMenuItem jmbCartaListar;
    public JMenuItem jmbProductoIngresar;
    public JMenuItem jmbProductoModificar;
    public JMenuItem jmbProductoEliminar;
    public JMenuItem jmbProductoListar;
    public JMenuItem jmbIngredienteIngresar;
    public JMenuItem jmbIngredienteModificar;
    public JMenuItem jmbIngredienteEliminar;
    public JMenuItem jmbIngredienteListar;
    public JMenuItem jmbPersonalIngresar;
    public JMenuItem jmbPersonalModificar;
    public JMenuItem jmbPersonalEliminar;
    public JMenuItem jmbPersonalListar;
    public JMenuItem jmbReporteDinero;
    public JMenuItem jmbReporteProducto;
    public JMenuItem jmbReporteConcurrencia;
    public JButton btnDisponibilidadIngresar;
    public JButton btnReservaIngresar;
    private JToolBar jtbAccesosRapidos;

    public Ui_Main(){
        initPanelPrincipal();
    }

    private void initPanelPrincipal(){
        setTitle("Restaurante");
        setVisible(true);
        setResizable(false);
        setSize(1280, 600);

        setLocationRelativeTo(null);
        setContentPane(grbPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
