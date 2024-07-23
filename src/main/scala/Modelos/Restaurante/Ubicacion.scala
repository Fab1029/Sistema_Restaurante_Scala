package Modelos.Restaurante

class Ubicacion (private var _direccion:String){

  def direccion:String = _direccion

  def direccion_=(direccion:String):Unit = _direccion = direccion

}
