package Controladores.Usuario

import Controladores.Restaurante.ControladorRestaurante
import Modelos.Usuario.GestionUsuarios
import Vistas.Usuario.Ui_InicioSesion

import java.awt.event.{ActionEvent, ActionListener, KeyEvent, KeyListener}
import javax.swing.JOptionPane

class ControladorInicioSesion extends ActionListener with KeyListener{
  private val gestionEncargados = GestionUsuarios
  private val ui_InicioSesion: Ui_InicioSesion = new Ui_InicioSesion()

  //initAction
  ui_InicioSesion.txtUsuario.addKeyListener(this)
  ui_InicioSesion.btnEntrar.addActionListener(this)


  private def entrarAction():Unit = {
    if(ui_InicioSesion.txtUsuario.getText.nonEmpty && ui_InicioSesion.txtClave.getPassword.nonEmpty && gestionEncargados.encargados.contains(ui_InicioSesion.txtUsuario.getText) && gestionEncargados.encargados(ui_InicioSesion.txtUsuario.getText).password == new String(ui_InicioSesion.txtClave.getPassword)){
      ui_InicioSesion.setVisible(false)
      if(ui_InicioSesion.txtUsuario.getText == "admin") new ControladorRestaurante(true, ui_InicioSesion) else new ControladorRestaurante(false, ui_InicioSesion)
    }
    else{
      JOptionPane.showMessageDialog(null, "Usuario o contraseña no validos", "Alerta", JOptionPane.WARNING_MESSAGE)
    }

  }

  override def actionPerformed(e: ActionEvent): Unit = {
    if (e.getSource == ui_InicioSesion.btnEntrar)
      entrarAction()
  }


  override def keyTyped(e: KeyEvent): Unit = {
    if (e.getSource == ui_InicioSesion.txtUsuario) {

      if (ui_InicioSesion.txtUsuario.getText.length > 9) e.consume()
    }
  }

  override def keyPressed(e: KeyEvent): Unit = {}

  override def keyReleased(e: KeyEvent): Unit = {}
}
