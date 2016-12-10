package com.computemachines.graphics

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL13._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL30._

import scala.math.exp

object GaussianPlot2DTexture {
  def sq(x: Int) = x*x
  def apply(): Texture = {
    val z = new Array[Float](32*32)
    for {
      i <- 0 to 31
      j <- 0 to 31
    } z(i*32 + j) = exp(-(sq(i-16)+sq(j-16)).toDouble/(2*16*16)).toFloat
    val image = Image2D(Util.byteBuffer(z), GL_RED, GL_FLOAT, 32, 32)
    new ImageTexture2D(TextureUnit(0), image)
  }
}
