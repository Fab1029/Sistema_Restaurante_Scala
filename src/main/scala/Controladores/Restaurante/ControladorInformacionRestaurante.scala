package Controladores.Restaurante

import Modelos.Restaurante.Restaurante
import Vistas.Restaurante.Ui_InformacionRestaurante

import java.awt.event.{ActionEvent, ActionListener, KeyEvent, KeyListener, WindowAdapter, WindowEvent}
import javax.swing.{JFrame, JOptionPane}

class ControladorInformacionRestaurante (private val controladorAnterior:JFrame) extends ActionListener with KeyListener{
  private var restaurante = Restaurante
  private val ui_InformacionRestaurante:Ui_InformacionRestaurante = new Ui_InformacionRestaurante()

  initSeccion()
  initAction()



  private def initAction(): Unit = {
    ui_InformacionRestaurante.txtRuc.addKeyListener(this)
    ui_InformacionRestaurante.txtTelefono.addKeyListener(this)
    ui_InformacionRestaurante.btnGuardarCambios.addActionListener(this)

  }

  private def initSeccion(): Unit = {
    ui_InformacionRestaurante.txtRuc.setText(restaurante.ruc)
    ui_InformacionRestaurante.txtEmail.setText(restaurante.email)
    ui_InformacionRestaurante.txtTelefono.setText(restaurante.telefono)
    ui_InformacionRestaurante.txtDireccion.setText(restaurante.ubicacion)
    ui_InformacionRestaurante.txtRazonSocial.setText(restaurante.razonSocial)
    ui_InformacionRestaurante.txtNombreRestaurante.setText(restaurante.nombre)
  }

  private def guardarCambiosAction(): Unit = {
    if(ui_InformacionRestaurante.txtNombreRestaurante.getText.nonEmpty && ui_InformacionRestaurante.txtRuc.getText.nonEmpty && ui_InformacionRestaurante.txtRazonSocial.getText.nonEmpty && ui_InformacionRestaurante.txtDireccion.getText.nonEmpty && ui_InformacionRestaurante.txtEmail.getText.nonEmpty && ui_InformacionRestaurante.txtTelefono.getText.nonEmpty){
      restaurante.ruc_(ui_InformacionRestaurante.txtRuc.getText)
      restaurante.email_(ui_InformacionRestaurante.txtRuc.getText)
      restaurante.telefono_(ui_InformacionRestaurante.txtTelefono.getText)
      restaurante.ubicacion_(ui_InformacionRestaurante.txtDireccion.getText)
      restaurante.nombre_(ui_InformacionRestaurante.txtNombreRestaurante.getText)
      restaurante.razonSocial_(ui_InformacionRestaurante.txtRazonSocial.getText)
      dialogoInformacion("Éxito", "Cambios guardados")
    }
    else dialogoInformacion("Alerta", "No se realizaron los cambios")

  }


  private def dialogoInformacion(titulo:String, cadena:String):Unit = {
    JOptionPane.showMessageDialog(null, cadena, titulo, JOptionPane.INFORMATION_MESSAGE)
  }

  //Cerrar ventana
  ui_InformacionRestaurante.addWindowListener(new WindowAdapter() {
    override def windowClosing(e: WindowEvent): Unit = {
      controladorAnterior.setVisible(true)
      ui_InformacionRestaurante.dispose()
    }
  })

  override def actionPerformed(e: ActionEvent): Unit = {
    if(e.getSource == ui_InformacionRestaurante.btnGuardarCambios) guardarCambiosAction()
  }


  override def keyTyped(e: KeyEvent): Unit = {
    if(e.getSource == ui_InformacionRestaurante.txtRuc){
      val caracter = e.getKeyChar
      if(ui_InformacionRestaurante.txtRuc.getText.length > 9 || Character.isLetter(caracter)) e.consume()
    }
    else if(e.getSource == ui_InformacionRestaurante.txtTelefono){
      val caracter = e.getKeyChar
      if(ui_InformacionRestaurante.txtTelefono.getText.length > 9 || Character.isLetter(caracter)) e.consume()

    }
  }

  override def keyPressed(e: KeyEvent): Unit = {}

  override def keyReleased(e: KeyEvent): Unit = {}
}
