package Modelos.Usuario

class Encargado(_cedula: String, _nombre: String, _direccion: String, private var _password:String) extends Usuario(_cedula, _nombre, _direccion) with Serializable{
  def password:String = _password
  def password_=(password:String):Unit = _password = password
}
