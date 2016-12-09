package com.computemachines.graphics

import java.io.File
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL13._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL30._
import scala.io.Source
import java.io.InputStreamReader
import java.nio.{ByteBuffer,FloatBuffer}
import java.nio.ByteOrder.nativeOrder

import ar.com.hjg.pngj._

abstract class TextureUnit(val gl_label: Int, val index: Int)
object TextureUnit {
  def apply(index: Int): TextureUnit = index match {
    case 0 => TextureUnit0
  }
}
object TextureUnit0 extends TextureUnit(GL_TEXTURE0, 0)

class Texture2D(val unit: TextureUnit)(val pixels: ByteBuffer, val width: Int, val height: Int, val channels: Int) {
  val gl_texture: Int = glGenTextures()
  def bufferTexture() {
    glActiveTexture(unit.gl_label)
    glBindTexture(GL_TEXTURE_2D, gl_texture)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

    pixels.position(0)
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, pixels)
    glBindTexture(GL_TEXTURE_2D, 0)
  }
}

object Texture2D {
  def apply(resource: String): Texture2D = {
    val args = loadImage(resource)
    val tex = new Texture2D(TextureUnit(0))(args._1, args._2._1, args._2._2, args._2._3)
    tex.bufferTexture()
    tex
  }
  def loadImage(resource: String): (ByteBuffer, (Int, Int, Int)) =  {
    val pngReader = new PngReaderByte(new File(Util.resBaseName + resource))
    val info = pngReader.imgInfo
    println(s"width: ${info.cols}")
    println(s"height: ${info.rows}")
    println(s"bitdepth: ${info.bitDepth}")
    println(s"channels: ${info.channels}")
    println(s"bytesPerRow: ${info.bytesPerRow}")
    val buffer = ByteBuffer.allocateDirect(
      info.rows*info.bytesPerRow
    )//.order(nativeOrder()).asFloatBuffer()
    for( i <- 1 to info.rows ) {
      val raw = new Array[Byte](info.bytesPerRow)

      pngReader.readRow() match {
        case line: ImageLineByte => {
          buffer.put(line.getScanline)
        }
        case _ => throw new ClassCastException()
      }
    }
    buffer.flip()
    return (buffer, (info.cols, info.rows, info.channels))
  }
}
