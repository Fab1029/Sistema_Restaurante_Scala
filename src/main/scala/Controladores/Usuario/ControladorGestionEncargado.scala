package Controladores.Usuario

import Modelos.Usuario.{Encargado, GestionUsuarios}
import Vistas.Usuario.Ui_GestionEncargado

import java.awt.event.{ActionEvent, ActionListener, KeyEvent, KeyListener, WindowAdapter, WindowEvent}
import javax.swing.{JFrame, JOptionPane}
import javax.swing.event.{ChangeEvent, ChangeListener}

class ControladorGestionEncargado (private val seccion:Int, private val controladorAnterior:JFrame) extends ActionListener with ChangeListener with KeyListener{
  private var gestionUsuarios = GestionUsuarios
  private val ui_GestionEncargado:Ui_GestionEncargado = new Ui_GestionEncargado()

  verificarPestana()
  initSeccion(seccion)()
  initActions()

  private def initActions(): Unit = {
    ui_GestionEncargado.txtCedulaIngresar.addKeyListener(this)
    ui_GestionEncargado.txtCedulaEliminar.addKeyListener(this)
    ui_GestionEncargado.txtCedulaModificar.addKeyListener(this)

    ui_GestionEncargado.tbEncargado.addChangeListener(this)
    ui_GestionEncargado.btnEliminar.addActionListener(this)
    ui_GestionEncargado.btnLimpiarCampos.addActionListener(this)
    ui_GestionEncargado.btnGuardarCambios.addActionListener(this)
    ui_GestionEncargado.btnIngresarEncargado.addActionListener(this)
  }

  def validarCedulaEcuatoriana(cedula: String): Boolean = {
    ((verificador: Int) => (verificador == 0 && verificador == cedula.last.asDigit) || (10 - verificador == cedula.last.asDigit))(
      cedula.zip(List(2, 1, 2, 1, 2, 1, 2, 1, 2, 1)).map {
          case (valorCedula, valorMascara) =>
          val multiplicacion = valorCedula.asDigit * valorMascara
          if (multiplicacion > 9) multiplicacion - 9 else multiplicacion
        }
        .take(9)
        .sum % 10
    )
  }

  def eliminarEncargadoAction(): Unit = {
    if(ui_GestionEncargado.txtCedulaEliminar.getText.nonEmpty && gestionUsuarios.encargados.contains(ui_GestionEncargado.txtCedulaEliminar.getText)){
      gestionUsuarios.encargados -= (ui_GestionEncargado.txtCedulaEliminar.getText)
      dialogoInformacion("Éxito", "Eliminación exitosa")
      initSeccion(2)()
      verificarPestana()
    }
    else dialogoInformacion("Alerta", "No se realizó la eliminación")
  }

  def ingresarEncargadoAction(): Unit = {
    if(ui_GestionEncargado.txtCedulaIngresar.getText.nonEmpty && ui_GestionEncargado.txtNombreIngresar.getText.nonEmpty && ui_GestionEncargado.txtDireccionIngresar.getText.nonEmpty && ui_GestionEncargado.txtClaveIngresar.getPassword.nonEmpty && validarCedulaEcuatoriana(ui_GestionEncargado.txtCedulaIngresar.getText)){
      gestionUsuarios.encargados += (ui_GestionEncargado.txtCedulaIngresar.getText -> new Encargado(ui_GestionEncargado.txtCedulaIngresar.getText, ui_GestionEncargado.txtNombreIngresar.getText, ui_GestionEncargado.txtDireccionIngresar.getText, new String(ui_GestionEncargado.txtClaveIngresar.getPassword)))
      dialogoInformacion("Éxito", "Ingreso exitoso")
      initSeccion(0)()
      verificarPestana()

    }
    else dialogoInformacion("Alerta", "Campos no validos")
  }

  def modificarEncargadoAction(): Unit = {
    if(ui_GestionEncargado.txtCedulaModificar.getText.nonEmpty && ui_GestionEncargado.txtNombreModifcar.getText.nonEmpty && ui_GestionEncargado.txtDireccionModificar.getText.nonEmpty && ui_GestionEncargado.txtClaveModificar.getPassword.nonEmpty){
      gestionUsuarios.encargados(ui_GestionEncargado.txtCedulaModificar.getText).nombre = ui_GestionEncargado.txtNombreModifcar.getText
      gestionUsuarios.encargados(ui_GestionEncargado.txtCedulaModificar.getText).direccion = ui_GestionEncargado.txtDireccionModificar.getText
      gestionUsuarios.encargados(ui_GestionEncargado.txtCedulaModificar.getText).password = new String(ui_GestionEncargado.txtClaveModificar.getPassword)
      dialogoInformacion("Éxito", "Se han guardado los cambios")
      initSeccion(1)()
      verificarPestana()
    }
    else dialogoInformacion("Alerta", "Ingrese todos los campos")

  }

  private def listarEncargadosAction(): Unit = {
    ui_GestionEncargado.jgdEncargados.setRowCount(0)
    gestionUsuarios.encargados.values.foreach(encargado => ui_GestionEncargado.jgdEncargados.addRow(Array[Object](encargado.cedula, encargado.nombre, encargado.direccion)))
  }

  private def initSeccion(seccion:Int): () => Unit ={
    seccion match {
      //Ingresar
      case 0 => () => {
        ui_GestionEncargado.tbEncargado.setSelectedIndex(0)
        setDefaultValues(1)()
      }
      //Modificar
      case 1 => () => {
        ui_GestionEncargado.tbEncargado.setSelectedIndex(1)
        ui_GestionEncargado.txtCedulaModificar.setText("")
        setDefaultValues(2)()

      }
      //Eliminar
      case 2 => () => {
        ui_GestionEncargado.tbEncargado.setSelectedIndex(2)
        ui_GestionEncargado.txtCedulaEliminar.setText("")
        setDefaultValues(3)()
      }
      //Listar
      case 3 => () => {
        ui_GestionEncargado.tbEncargado.setSelectedIndex(3)
        listarEncargadosAction()
      }

    }
  }

  private def setDefaultValues(accion:Int): () => Unit ={
    accion match {
      //Ingresar
      case 1 => () => {
        ui_GestionEncargado.txtClaveIngresar.setText("")
        ui_GestionEncargado.txtNombreIngresar.setText("")
        ui_GestionEncargado.txtCedulaIngresar.setText("")
        ui_GestionEncargado.txtDireccionIngresar.setText("")
      }
      //Modificar
      case 2 => () => {
        ui_GestionEncargado.txtClaveModificar.setText("")
        ui_GestionEncargado.txtNombreModifcar.setText("")
        ui_GestionEncargado.txtDireccionModificar.setText("")

      }
      //Eliminar
      case 3 => () => {
        ui_GestionEncargado.txtNombreEliminar.setText("")
        ui_GestionEncargado.txtDireccionEliminar.setText("")
      }

    }
  }

  private def busquedaCedulaAction(accion:Int): () => Unit ={
    accion match {
      //Cedula modificar
      case 1 => () => {
        if(gestionUsuarios.encargados.contains(ui_GestionEncargado.txtCedulaModificar.getText)){
          ui_GestionEncargado.txtNombreModifcar.setText(gestionUsuarios.encargados(ui_GestionEncargado.txtCedulaModificar.getText).nombre)
          ui_GestionEncargado.txtClaveModificar.setText(gestionUsuarios.encargados(ui_GestionEncargado.txtCedulaModificar.getText).password)
          ui_GestionEncargado.txtDireccionModificar.setText(gestionUsuarios.encargados(ui_GestionEncargado.txtCedulaModificar.getText).direccion)
        }
      }
      //Cedula eliminar
      case 2 => () => {
        if(gestionUsuarios.encargados.contains(ui_GestionEncargado.txtCedulaEliminar.getText)){
          ui_GestionEncargado.txtNombreEliminar.setText(gestionUsuarios.encargados(ui_GestionEncargado.txtCedulaEliminar.getText).nombre)
          ui_GestionEncargado.txtDireccionEliminar.setText(gestionUsuarios.encargados(ui_GestionEncargado.txtCedulaEliminar.getText).direccion)
        }

      }
    }

  }

  private def dialogoInformacion(titulo:String, cadena:String):Unit = {
    JOptionPane.showMessageDialog(null, cadena, titulo, JOptionPane.INFORMATION_MESSAGE)
  }

  private def verificarPestana():Unit ={
    if(gestionUsuarios.encargados.nonEmpty) (1 until 4).foreach(indice => ui_GestionEncargado.tbEncargado.setEnabledAt(indice, true))
    else{
      ui_GestionEncargado.tbEncargado.setSelectedIndex(0)
      (1 until 4).foreach(indice => ui_GestionEncargado.tbEncargado.setEnabledAt(indice, false))
    }
  }

  //Cerrar ventana
  ui_GestionEncargado.addWindowListener(new WindowAdapter() {
    override def windowClosing(e: WindowEvent): Unit = {
      controladorAnterior.setVisible(true)
      ui_GestionEncargado.dispose()
    }
  })

  override def actionPerformed(e: ActionEvent): Unit = {
    if(e.getSource == ui_GestionEncargado.btnIngresarEncargado) ingresarEncargadoAction()
    else if(e.getSource == ui_GestionEncargado.btnLimpiarCampos) initSeccion(0)()
    else if(e.getSource == ui_GestionEncargado.btnGuardarCambios) modificarEncargadoAction()
    else if(e.getSource == ui_GestionEncargado.btnEliminar) eliminarEncargadoAction()

  }

  override def stateChanged(e: ChangeEvent): Unit = {
    if(e.getSource == ui_GestionEncargado.tbEncargado) initSeccion(ui_GestionEncargado.tbEncargado.getSelectedIndex)()
  }
  override def keyTyped(e: KeyEvent): Unit = {
    if(e.getSource == ui_GestionEncargado.txtCedulaIngresar){
      val caracter = e.getKeyChar
      if(ui_GestionEncargado.txtCedulaIngresar.getText.length > 9 || Character.isLetter(caracter)) e.consume()
    }

    if(e.getSource == ui_GestionEncargado.txtCedulaModificar){
      val caracter = e.getKeyChar
      if(ui_GestionEncargado.txtCedulaModificar.getText.length > 9 || Character.isLetter(caracter)) e.consume()
      if (ui_GestionEncargado.txtCedulaModificar.getText.length == 10) busquedaCedulaAction(1)()
      else setDefaultValues(2)()
    }

    if(e.getSource == ui_GestionEncargado.txtCedulaEliminar){
      val caracter = e.getKeyChar
      if(ui_GestionEncargado.txtCedulaEliminar.getText.length > 9 || Character.isLetter(caracter)) e.consume()
      if (ui_GestionEncargado.txtCedulaEliminar.getText.length == 10) busquedaCedulaAction(2)()
      else setDefaultValues(3)()
    }

  }

  override def keyPressed(e: KeyEvent): Unit = {}

  override def keyReleased(e: KeyEvent): Unit = {}
}
