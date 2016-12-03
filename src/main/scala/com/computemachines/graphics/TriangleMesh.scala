package com.computemachines.graphics

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._

class TriangleMesh(val dimension: Int)(vertices: Array[Float])
    extends Mesh(vertices) {
  require(vertices.length % dimension == 0)
  def bindTo(attribute: Int): Unit = {
    buffer.position(0)
    glVertexAttribPointer(attribute, dimension, GL_FLOAT, false, 0, buffer)
    glEnableVertexAttribArray(attribute)
  }
  def draw(): Unit = glDrawArrays(GL_TRIANGLES, 0, 3)
  def setUniform(uniform: Int): Unit = glUniform4f(uniform, 1, 0, 0, 1)
}

