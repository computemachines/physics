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

class Texture(val pixels: ByteBuffer, val width: Int, val height: Int, val channels: Int) {
  val gl_texture: Int = glGenTextures()
  def check() {
    val error = glGetError()
    if(error != GL_NO_ERROR) {
      println(s"glGetError() = $error")
      System.out.flush()
      throw new RuntimeException()
    }
  }
  def init() {
    glActiveTexture(GL_TEXTURE0)
    withBinding {
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)

      glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels)
    }
  }
  def withBinding(op: => Unit) {
    glBindTexture(GL_TEXTURE_2D, gl_texture)
    op
    glBindTexture(GL_TEXTURE_2D, 0)
  }
}

object Texture {
  def apply(resource: String): Texture = {
    val args = loadImage(resource)
    val tex = new Texture(args._1, args._2._1, args._2._2, args._2._3)
    tex.init()
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
