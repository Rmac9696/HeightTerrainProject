package HeightTerrian

import com.jme3.math.ColorRGBA
import com.jme3.terrain.noise.basis.ImprovedNoise

trait HeightMapper {
  def getHeight(x:Float,y:Float): Float
  def scale(scale:Float): HeightMapper = (x,y) => this.getHeight(x * scale, y *scale)
  def mul(mul:Float): HeightMapper = (x, y) => this.getHeight(x,y) * mul
  def noise(): HeightMapper = (x,y) => ImprovedNoise.noise(x,y, 87f)
  def toHeightMap(x:Float,y:Float,size:Int): Array[Float] = {
    val floats: Array[Float] = Array.tabulate(size * size)(n => this.getHeight(x + n % size, y + n / size))
    floats
  }
  def max(x:Float, y:Float):Float = {
    if (x > y) x else y
  }
  def min(x:Float, y:Float):Float = {
    if(x < y) x else y
  }

  def toColor(upper:Float, lower:Float, n:Float): ColorRGBA = {
    //todo CREATE FUNCTION TO GET BYTE COLORS OF X PIXEL FORMAT
    n match {
      case x if (x > lower) && (x < upper) => ColorRGBA.Green
      case x if x < lower => ColorRGBA.Blue
      case x if x > upper => ColorRGBA.Red
    }
  }
  def toTextureMap(x:Float,y:Float,size:Int): Array[ColorRGBA] = {
    val floats = toHeightMap(x,y,size)
    val dist = floats.sum / floats.length

    val upper = dist - dist*2f
    val lower = dist + dist*2f
    println("avg = " + dist)
    println("upper = " + upper)
    println("lower = " + lower)
    val colors: Array[ColorRGBA] = Array.tabulate(size * size)(n => toColor(upper, lower, floats(n)))
    colors

  }
}
