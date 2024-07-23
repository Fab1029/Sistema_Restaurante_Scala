package Modelos.Restaurante

class DiaDisponibilidad (private val _fecha:String, private val _turno:String, private var _numeroPlazas:Int) extends Serializable{
  //Getters
  def fecha:String = _fecha
  def turno:String = _turno
  def numeroPlazas:Int = _numeroPlazas

  //Setters
  def numeroPlazas_=(numeroPlazas: Int): Unit = _numeroPlazas = numeroPlazas

}
