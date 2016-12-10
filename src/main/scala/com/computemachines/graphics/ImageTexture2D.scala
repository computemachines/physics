package com.computemachines.graphics

import java.io.File
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL13._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL30._
import scala.io.Source
import java.io.InputStreamReader
import java.nio.{Buffer,ByteBuffer,FloatBuffer}
import java.nio.ByteOrder.nativeOrder

import ar.com.hjg.pngj._

case class Image2D(
  val pixels: ByteBuffer,
  val gl_format: Int,
  val gl_datatype: Int,
  val width: Int,
  val height: Int
)

class ImageTexture2D(override val unit: TextureUnit,
  image: Image2D)
    extends Texture {

  override val gl_type = GL_TEXTURE_2D

  bind()
  glTexParameteri(gl_type, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
  glTexParameteri(gl_type, GL_TEXTURE_MAG_FILTER, GL_NEAREST)

  image.pixels.position(0)
  glTexImage2D(gl_type, 0, GL_RGBA, image.width, image.height, 0,
    image.gl_format, image.gl_datatype, image.pixels)
  unbind()
}


object ImageTexture2D {
  def apply(resource: String): ImageTexture2D = {
    val image = loadImage(resource)
    val tex = new ImageTexture2D(TextureUnit(0), image)
    tex
  }
  def loadImage(resource: String): Image2D = {
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
    val format = info.channels match {
      case 1 => GL_R
      case 2 => GL_RG
      case 3 => GL_RGB
      case 4 => GL_RGBA
    }
    return Image2D(buffer, format, GL_UNSIGNED_BYTE, info.cols, info.rows)
  }
}
