package Modelos.Usuario

class Usuario (private val _cedula:String, private var _nombre:String, private var _direccion:String) extends Serializable{
  //Getters
  def cedula:String = _cedula
  def nombre:String = _nombre
  def direccion:String = _direccion

  //Setters
  def nombre_=(nombre: String):Unit = _nombre = nombre
  def direccion_=(direccion: String):Unit = _direccion = direccion

}
