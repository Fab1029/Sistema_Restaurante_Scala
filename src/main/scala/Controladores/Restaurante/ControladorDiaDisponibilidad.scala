package Controladores.Restaurante

import Modelos.Restaurante.{DiaDisponibilidad, Restaurante}
import Vistas.Restaurante.Ui_Disponibilidad

import java.awt.event.{ActionEvent, ActionListener, ItemEvent, ItemListener, WindowAdapter, WindowEvent}
import java.beans.{PropertyChangeEvent, PropertyChangeListener}
import java.text.SimpleDateFormat
import java.util.Date
import javax.swing.{JFrame, JOptionPane}
import javax.swing.event.{ChangeEvent, ChangeListener}
import scala.collection.mutable

class ControladorDiaDisponibilidad (private val seccion:Int, private val controladorAnterior:JFrame) extends ActionListener with ChangeListener{
  private var restaurante = Restaurante
  private val ui_Disponibilidad:Ui_Disponibilidad = new Ui_Disponibilidad()

  verificarPestana()
  initSeccion(seccion)()
  initActions()
  addDateListeners()

  private def initActions(): Unit = {
    ui_Disponibilidad.btnEliminar.addActionListener(this)
    ui_Disponibilidad.tbDisponibilidad.addChangeListener(this)
    ui_Disponibilidad.btnGuardarCambios.addActionListener(this)
    ui_Disponibilidad.btnIngresarDisponibilidad.addActionListener(this)
  }

  private def cambioFechaAction(accion:Int): () => Unit = {
    accion match {
      //Cambio fecha ingresar
      case 1 => () =>{
        ui_Disponibilidad.sbNumeroPlazasIngresar.setValue(0)
      }
      //Cambio fecha modificar
      case 2 => () => {
        if(ui_Disponibilidad.dtpFechaModificar.getDate != null && restaurante.diasDisponibilidad.contains(new SimpleDateFormat(ui_Disponibilidad.dtpFechaModificar.getDateFormatString).format(ui_Disponibilidad.dtpFechaModificar.getDate)) && restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Disponibilidad.dtpFechaModificar.getDateFormatString).format(ui_Disponibilidad.dtpFechaModificar.getDate)).contains(ui_Disponibilidad.cmbTurnoModificar.getSelectedItem.toString)){
          ui_Disponibilidad.sbNumeroPlazasModificar.setValue(restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Disponibilidad.dtpFechaModificar.getDateFormatString).format(ui_Disponibilidad.dtpFechaModificar.getDate))(ui_Disponibilidad.cmbTurnoModificar.getSelectedItem.toString).numeroPlazas)
        }
        else ui_Disponibilidad.sbNumeroPlazasModificar.setValue(0)
      }
      //Cambio fecha eliminar
      case 3 => () => {
        if(ui_Disponibilidad.dtpFechaEliminar.getDate != null && restaurante.diasDisponibilidad.contains(new SimpleDateFormat(ui_Disponibilidad.dtpFechaEliminar.getDateFormatString).format(ui_Disponibilidad.dtpFechaEliminar.getDate)) && restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Disponibilidad.dtpFechaEliminar.getDateFormatString).format(ui_Disponibilidad.dtpFechaEliminar.getDate)).contains(ui_Disponibilidad.cmbTurnoEliminar.getSelectedItem.toString)){
          ui_Disponibilidad.txtNumeroPlazasEliminar.setText(restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Disponibilidad.dtpFechaEliminar.getDateFormatString).format(ui_Disponibilidad.dtpFechaEliminar.getDate))(ui_Disponibilidad.cmbTurnoEliminar.getSelectedItem.toString).numeroPlazas.toString)
        }
        else ui_Disponibilidad.txtNumeroPlazasEliminar.setText("No se tiene información sobre este día")

      }
    }
  }

  private def eliminarDiaDisponibilidadAction(): Unit = {
    if(ui_Disponibilidad.dtpFechaEliminar.getDate != null && restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Disponibilidad.dtpFechaEliminar.getDateFormatString).format(ui_Disponibilidad.dtpFechaEliminar.getDate)).contains(ui_Disponibilidad.cmbTurnoEliminar.getSelectedItem.toString)){
      restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Disponibilidad.dtpFechaEliminar.getDateFormatString).format(ui_Disponibilidad.dtpFechaEliminar.getDate)) -= ui_Disponibilidad.cmbTurnoEliminar.getSelectedItem.toString
      initSeccion(2)()
      verificarPestana()
      dialogoInformacion("Éxito", "Eliminación exitosa")
    }
    else dialogoInformacion("Alerta", "No existe información de disponibilidad")
  }

  private def ingresarDiaDisponibilidadAction(): Unit = {
    if(ui_Disponibilidad.dtpFechaIngresar.getDate != null && !restaurante.diasDisponibilidad.contains(new SimpleDateFormat(ui_Disponibilidad.dtpFechaIngresar.getDateFormatString).format(ui_Disponibilidad.dtpFechaIngresar.getDate))){
      restaurante.diasDisponibilidad += (new SimpleDateFormat(ui_Disponibilidad.dtpFechaIngresar.getDateFormatString).format(ui_Disponibilidad.dtpFechaIngresar.getDate) -> mutable.TreeMap[String, DiaDisponibilidad]())
    }
    if(ui_Disponibilidad.dtpFechaIngresar.getDate != null) {
      restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Disponibilidad.dtpFechaIngresar.getDateFormatString).format(ui_Disponibilidad.dtpFechaIngresar.getDate)) += (ui_Disponibilidad.cmbTurnoIngresar.getSelectedItem.toString -> new DiaDisponibilidad(new SimpleDateFormat(ui_Disponibilidad.dtpFechaIngresar.getDateFormatString).format(ui_Disponibilidad.dtpFechaIngresar.getDate), ui_Disponibilidad.cmbTurnoIngresar.getSelectedItem.toString, ui_Disponibilidad.sbNumeroPlazasIngresar.getValue.asInstanceOf[Int]))
      initSeccion(0)()
      verificarPestana()
      dialogoInformacion("Éxito", "Ingreso de disponibilidad exitosamente")
    }

  }

  private def modificarDiaDisponibilidadAction(): Unit = {
    if(ui_Disponibilidad.dtpFechaModificar.getDate != null && restaurante.diasDisponibilidad.contains(new SimpleDateFormat(ui_Disponibilidad.dtpFechaModificar.getDateFormatString).format(ui_Disponibilidad.dtpFechaModificar.getDate)) && restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Disponibilidad.dtpFechaModificar.getDateFormatString).format(ui_Disponibilidad.dtpFechaModificar.getDate)).contains(ui_Disponibilidad.cmbTurnoModificar.getSelectedItem.toString)){
      restaurante.diasDisponibilidad(new SimpleDateFormat(ui_Disponibilidad.dtpFechaModificar.getDateFormatString).format(ui_Disponibilidad.dtpFechaModificar.getDate))(ui_Disponibilidad.cmbTurnoModificar.getSelectedItem.toString).numeroPlazas = ui_Disponibilidad.sbNumeroPlazasModificar.getValue.asInstanceOf[Int]
      initSeccion(1)()
      verificarPestana()
      dialogoInformacion("Éxito", "Se ha modficado la disponibilidad del día")
    }
    else dialogoInformacion("Alerta", "No existe información de disponibilidad para este día")
  }

  private def listarDiaDisponibilidadAction(): Unit = {
    ui_Disponibilidad.jgdDisponibilidad.setRowCount(0)
    for(fecha <- restaurante.diasDisponibilidad.keys){
      for(turno <- restaurante.diasDisponibilidad(fecha).keys){
        ui_Disponibilidad.jgdDisponibilidad.addRow(Array[Object](restaurante.diasDisponibilidad(fecha)(turno).fecha, restaurante.diasDisponibilidad(fecha)(turno).turno, restaurante.diasDisponibilidad(fecha)(turno).numeroPlazas.toString))
      }
    }
  }

  private def initSeccion(seccion:Int): () => Unit ={
    seccion match {
      //Ingresar
      case 0 => () => {
        ui_Disponibilidad.tbDisponibilidad.setSelectedIndex(0)
        ui_Disponibilidad.cmbTurnoIngresar.removeAllItems()
        ui_Disponibilidad.sbNumeroPlazasIngresar.setValue(0)
        ui_Disponibilidad.dtpFechaIngresar.setDate(null)
        ui_Disponibilidad.dtpFechaIngresar.setMinSelectableDate(new Date())
        restaurante.turnos.foreach(turno => ui_Disponibilidad.cmbTurnoIngresar.addItem(turno))

        cambioFechaAction(1)()
      }
      //Modificar
      case 1 => () => {
        ui_Disponibilidad.tbDisponibilidad.setSelectedIndex(1)
        ui_Disponibilidad.cmbTurnoModificar.removeAllItems()
        ui_Disponibilidad.sbNumeroPlazasModificar.setValue(0)
        restaurante.turnos.foreach(turno => ui_Disponibilidad.cmbTurnoModificar.addItem(turno))
        ui_Disponibilidad.dtpFechaModificar.setDate(null)
        ui_Disponibilidad.dtpFechaModificar.setMinSelectableDate(new Date())

        cambioFechaAction(2)()
      }
      //Eliminar
      case 2 => () => {
        ui_Disponibilidad.tbDisponibilidad.setSelectedIndex(2)
        ui_Disponibilidad.cmbTurnoEliminar.removeAllItems()
        restaurante.turnos.foreach(turno => ui_Disponibilidad.cmbTurnoEliminar.addItem(turno))

        cambioFechaAction(3)()
      }
      //Listar
      case 3 => () => {
        ui_Disponibilidad.tbDisponibilidad.setSelectedIndex(3)
        listarDiaDisponibilidadAction()
      }

    }
  }


  private def verificarPestana():Unit ={
    if(restaurante.diasDisponibilidad.nonEmpty) (1 until 4).foreach(indice => ui_Disponibilidad.tbDisponibilidad.setEnabledAt(indice, true))
    else{
      ui_Disponibilidad.tbDisponibilidad.setSelectedIndex(0)
      (1 until 4).foreach(indice => ui_Disponibilidad.tbDisponibilidad.setEnabledAt(indice, false))
    }
  }

  private def dialogoInformacion(titulo:String, cadena:String):Unit = {
    JOptionPane.showMessageDialog(null, cadena, titulo, JOptionPane.INFORMATION_MESSAGE)
  }
  //Cerrar ventana
  ui_Disponibilidad.addWindowListener(new WindowAdapter() {
    override def windowClosing(e: WindowEvent): Unit = {
      controladorAnterior.setVisible(true)
      ui_Disponibilidad.dispose()
    }
  })


  override def actionPerformed(e: ActionEvent): Unit = {
    if(e.getSource == ui_Disponibilidad.btnEliminar) eliminarDiaDisponibilidadAction()
    else if(e.getSource == ui_Disponibilidad.btnGuardarCambios) modificarDiaDisponibilidadAction()
    else if(e.getSource == ui_Disponibilidad.btnIngresarDisponibilidad) ingresarDiaDisponibilidadAction()

  }

  override def stateChanged(e: ChangeEvent): Unit = {
    if(e.getSource == ui_Disponibilidad.tbDisponibilidad) initSeccion(ui_Disponibilidad.tbDisponibilidad.getSelectedIndex)()
  }

  private def addDateListeners(): Unit = {
    ui_Disponibilidad.dtpFechaIngresar.addPropertyChangeListener("date", new PropertyChangeListener() {
      override def propertyChange(evt: PropertyChangeEvent): Unit = {
        if ("date" == evt.getPropertyName) cambioFechaAction(1)()
      }
    })
    ui_Disponibilidad.dtpFechaModificar.addPropertyChangeListener("date", new PropertyChangeListener() {
      override def propertyChange(evt: PropertyChangeEvent): Unit = {
        if ("date" == evt.getPropertyName) cambioFechaAction(2)()
      }
    })
    ui_Disponibilidad.dtpFechaEliminar.addPropertyChangeListener("date", new PropertyChangeListener() {
      override def propertyChange(evt: PropertyChangeEvent): Unit = {
        if ("date" == evt.getPropertyName) cambioFechaAction(3)()

      }
    })
  }

}
