package Controladores.Usuario

import Vistas.Usuario.Ui_AcercaDe

import java.awt.event.{WindowAdapter, WindowEvent}
import javax.swing.JFrame

class ControladorAcercaDe (private val controladorAnterior:JFrame){
  private val ui_AcercaDe:Ui_AcercaDe = new Ui_AcercaDe()


  //Cerrar ventana
  ui_AcercaDe.addWindowListener(new WindowAdapter() {
    override def windowClosing(e: WindowEvent): Unit = {
      controladorAnterior.setVisible(true)
      ui_AcercaDe.dispose()
    }
  })

}
