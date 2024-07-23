package Modelos.Comida

class Ingrediente (private var _nombre: String, private var _tipo: String, private var _medida:String) extends Serializable{
  //Getters
  def tipo:String = _tipo
  def nombre:String = _nombre
  def medida:String = _medida

  //Setters
  def tipo_= (tipo: String):Unit = _tipo = tipo
  def nombre_= (nombre: String):Unit = _nombre = nombre
  def medida_= (medida: String):Unit = _medida = medida

}
