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

abstract class TextureUnit(val gl_label: Int, val index: Int)
object TextureUnit {
  def apply(index: Int): TextureUnit = index match {
    case 0 => TextureUnit0
  }
}
object TextureUnit0 extends TextureUnit(GL_TEXTURE0, 0)

abstract class Texture {
  val gl_texture: Int = glGenTextures()
  val unit: TextureUnit
  val gl_type: Int

  def bind() {
    glActiveTexture(unit.gl_label)
    glBindTexture(gl_type, gl_texture)
  }
  def unbind() {
    glBindTexture(gl_type, 0)
  }
}
