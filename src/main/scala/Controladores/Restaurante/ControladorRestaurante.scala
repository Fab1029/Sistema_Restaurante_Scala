package Controladores.Restaurante

import Controladores.Comida.{ControladorIngrediente, ControladorProducto}
import Controladores.Usuario.{ControladorAcercaDe, ControladorGestionEncargado}
import Modelos.Restaurante.Restaurante
import Modelos.Usuario.GestionUsuarios
import Vistas.Restaurante.Ui_Main

import java.awt.event.{ActionEvent, ActionListener, WindowAdapter, WindowEvent}
import javax.swing.{JFrame, JOptionPane}

class ControladorRestaurante (private val isAdmin:Boolean, private val controladorAnterior:JFrame) extends ActionListener {
  private val restaurante = Restaurante
  private val ui_Main:Ui_Main = new Ui_Main()

  initVentana()
  initActions()
  restaurante.cargarDatosRestaurante(1)()
  restaurante.cargarDatosRestaurante(2)()

  def initVentana():Unit ={
    if(!isAdmin){
      ui_Main.jmbCarta.setEnabled(false)
      ui_Main.jmbPersonal.setEnabled(false)
      ui_Main.jmbReportes.setEnabled(false)
      ui_Main.jmbConfiguracion.setEnabled(false)
    }
  }

  private def initActions(): Unit = {
    // Inicializar acciones de los componentes de la interfaz
    ui_Main.jmbSalir.addActionListener(this)
    ui_Main.jmbCartaIngresar.addActionListener(this)
    ui_Main.jmbReservaIngresar.addActionListener(this)
    ui_Main.jmbPersonalIngresar.addActionListener(this)
    ui_Main.jmbProductoIngresar.addActionListener(this)
    ui_Main.jmbIngredienteIngresar.addActionListener(this)
    ui_Main.jmbDisponibilidadIngresar.addActionListener(this)

    ui_Main.jmbCartaModificar.addActionListener(this)
    ui_Main.jmbReservaModificar.addActionListener(this)
    ui_Main.jmbPersonalModificar.addActionListener(this)
    ui_Main.jmbProductoModificar.addActionListener(this)
    ui_Main.jmbIngredienteModificar.addActionListener(this)
    ui_Main.jmbDisponibilidadModificar.addActionListener(this)

    ui_Main.jmbCartaEliminar.addActionListener(this)
    ui_Main.jmbReservaEliminar.addActionListener(this)
    ui_Main.jmbPersonalEliminar.addActionListener(this)
    ui_Main.jmbProductoEliminar.addActionListener(this)
    ui_Main.jmbIngredienteEliminar.addActionListener(this)
    ui_Main.jmbDisponibilidadEliminar.addActionListener(this)

    ui_Main.jmbCartaListar.addActionListener(this)
    ui_Main.jmbReservaListar.addActionListener(this)
    ui_Main.jmbPersonalListar.addActionListener(this)
    ui_Main.jmbProductoListar.addActionListener(this)
    ui_Main.jmbIngredienteListar.addActionListener(this)
    ui_Main.jmbDisponibilidadListar.addActionListener(this)

    ui_Main.jmbAcercaDe.addActionListener(this)
    ui_Main.jmbConfiguracion.addActionListener(this)
    ui_Main.jmbReporteDinero.addActionListener(this)
    ui_Main.jmbReporteProducto.addActionListener(this)
    ui_Main.jmbReporteConcurrencia.addActionListener(this)

    ui_Main.btnDisponibilidadIngresar.addActionListener(this)
    ui_Main.btnReservaIngresar.addActionListener(this)
  }

  override def actionPerformed(e: ActionEvent): Unit = {

    if(e.getSource == ui_Main.jmbIngredienteIngresar) initControlador("controlador_ingrediente")(0)
    else if(e.getSource == ui_Main.jmbIngredienteModificar || e.getSource == ui_Main.jmbIngredienteEliminar || e.getSource == ui_Main.jmbIngredienteListar){
      if(restaurante.ingredientes.nonEmpty){
        if(e.getSource == ui_Main.jmbIngredienteModificar) initControlador("controlador_ingrediente")(1)
        else if (e.getSource == ui_Main.jmbIngredienteEliminar) initControlador("controlador_ingrediente")(2)
        else initControlador("controlador_ingrediente")(3)
      }
      else dialogoInformacion("Información", "Ingredientes, aun no ingresados al sistema")
    }

    else if(e.getSource == ui_Main.jmbProductoIngresar) initControlador("controlador_producto")(0)
    else if(e.getSource == ui_Main.jmbProductoModificar || e.getSource == ui_Main.jmbProductoEliminar|| e.getSource == ui_Main.jmbProductoListar){
      if(restaurante.productos.nonEmpty){
        if(e.getSource == ui_Main.jmbProductoModificar) initControlador("controlador_producto")(1)
        else if(e.getSource == ui_Main.jmbProductoEliminar) initControlador("controlador_producto")(2)
        else initControlador("controlador_producto")(3)
      }
      else dialogoInformacion("Información", "Productos, aun no ingresados al sistema")
    }

    else if(e.getSource == ui_Main.jmbPersonalIngresar) initControlador("controlador_gestion_encargado")(0)
    else if(e.getSource == ui_Main.jmbPersonalModificar || e.getSource == ui_Main.jmbPersonalEliminar|| e.getSource == ui_Main.jmbPersonalListar){
      if(GestionUsuarios.encargados.nonEmpty){
        if(e.getSource == ui_Main.jmbPersonalModificar) initControlador("controlador_gestion_encargado")(1)
        else if(e.getSource == ui_Main.jmbPersonalEliminar) initControlador("controlador_gestion_encargado")(2)
        else initControlador("controlador_gestion_encargado")(3)
      }
      else dialogoInformacion("Información", "Personal, aun no ingresados al sistema")
    }

    else if(e.getSource == ui_Main.jmbCartaIngresar) initControlador("controlador_carta")(0)
    else if(e.getSource == ui_Main.jmbCartaModificar || e.getSource == ui_Main.jmbCartaEliminar || e.getSource == ui_Main.jmbCartaListar){
      if(restaurante.cartas.nonEmpty){
        if(e.getSource == ui_Main.jmbCartaModificar) initControlador("controlador_carta")(1)
        else if(e.getSource == ui_Main.jmbCartaEliminar) initControlador("controlador_carta")(2)
        else initControlador("controlador_carta")(3)
      }
      else dialogoInformacion("Información", "Cartas, aun no ingresadas al sistema")
    }

    else if(e.getSource == ui_Main.jmbDisponibilidadIngresar) initControlador("controlador_dia_disponibilidad")(0)
    else if(e.getSource == ui_Main.jmbDisponibilidadModificar || e.getSource == ui_Main.jmbDisponibilidadEliminar || e.getSource == ui_Main.jmbDisponibilidadListar){
      if(restaurante.diasDisponibilidad.nonEmpty){
        if(e.getSource == ui_Main.jmbDisponibilidadModificar) initControlador("controlador_dia_disponibilidad")(1)
        else if(e.getSource == ui_Main.jmbDisponibilidadEliminar) initControlador("controlador_dia_disponibilidad")(2)
        else initControlador("controlador_dia_disponibilidad")(3)
      }
      else dialogoInformacion("Información", "Disponibilidad, aun no ingresadas al sistema")
    }

    else if(e.getSource == ui_Main.jmbReservaIngresar) initControlador("controlador_reserva")(0)
    else if(e.getSource == ui_Main.jmbReservaModificar || e.getSource == ui_Main.jmbReservaEliminar || e.getSource == ui_Main.jmbReservaListar){
      if(restaurante.reservas.nonEmpty){
        if(e.getSource == ui_Main.jmbReservaModificar) initControlador("controlador_reserva")(1)
        else if(e.getSource == ui_Main.jmbReservaEliminar) initControlador("controlador_reserva")(2)
        else initControlador("controlador_reserva")(3)
      }
      else dialogoInformacion("Información", "Reservas, aun no ingresadas al sistema")
    }

    else if(e.getSource == ui_Main.jmbReporteDinero) initControlador("controlador_reportes")(0)
    else if(e.getSource == ui_Main.jmbReporteProducto) initControlador("controlador_reportes")(1)
    else if(e.getSource == ui_Main.jmbReporteConcurrencia) initControlador("controlador_reportes")(2)

    else if(e.getSource == ui_Main.jmbAcercaDe) initControlador("controlador_acerca_de")(0)
    else if(e.getSource == ui_Main.btnReservaIngresar) initControlador("controlador_reserva")(0)
    else if(e.getSource == ui_Main.jmbConfiguracion) initControlador("controlador_configuracion")(0)
    else if(e.getSource == ui_Main.btnDisponibilidadIngresar) initControlador("controlador_dia_disponibilidad")(0)

    else if(e.getSource == ui_Main.jmbSalir) salir()

  }


  private def initControlador(tipoControlador:String): Int => Unit ={
    ui_Main.setVisible(false)

    tipoControlador match {
      case "controlador_ingrediente" => (seccion: Int) => {
        new ControladorIngrediente(seccion, ui_Main)
      }
      case "controlador_producto" => (seccion:Int) => {
        new ControladorProducto(seccion, ui_Main)
      }
      case "controlador_carta" => (seccion:Int) =>{
        new ControladorCarta(seccion, ui_Main)
      }
      case "controlador_dia_disponibilidad" => (seccion:Int) => {
       new ControladorDiaDisponibilidad(seccion, ui_Main)
      }
      case "controlador_gestion_encargado" => (seccion:Int) =>{
        new ControladorGestionEncargado(seccion, ui_Main)
      }
      case "controlador_reserva" => (seccion:Int) =>{
        new ControladorReserva(seccion, ui_Main)
      }
      case "controlador_configuracion" => (seccion:Int) =>{
        new ControladorInformacionRestaurante(ui_Main)
      }
      case "controlador_acerca_de" => (seccion:Int) =>{
        new ControladorAcercaDe(ui_Main)
      }
      case "controlador_reportes" => (seccion:Int) => {
        new ControladorReportes(seccion, ui_Main)
      }
    }
  }


  private def dialogoInformacion(titulo:String, cadena:String):Unit = {
    JOptionPane.showMessageDialog(null, cadena, titulo, JOptionPane.WARNING_MESSAGE)
  }

  private def salir(): Unit = {
    GestionUsuarios.guardar()
    restaurante.guardarDatosRestaurante(1)()
    restaurante.guardarDatosRestaurante(2)()
    ui_Main.dispose()
    controladorAnterior.dispose()
  }
  //Cerrar ventana
  ui_Main.addWindowListener(new WindowAdapter() {
    override def windowClosing(e: WindowEvent): Unit = {
      GestionUsuarios.guardar()
      restaurante.guardarDatosRestaurante(1)()
      restaurante.guardarDatosRestaurante(2)()
    }
  })



}
