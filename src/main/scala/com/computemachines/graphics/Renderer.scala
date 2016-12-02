package com.computemachines.plot

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import java.nio.ByteBuffer
import java.nio.ByteOrder.nativeOrder
import java.lang.System.currentTimeMillis

trait Renderer {
  def draw(): Unit
}

// must be instantiated in drawing thread
class EmptyRender extends Renderer {
  val program = Util.makeProgram("id.vert", "const.frag")

  val verts = Array[Float](0.1f, 0.1f, 1, 0.1f, 1, 1)
  val vertsData = ByteBuffer.allocateDirect(verts.length * 4)
    .order(nativeOrder())
    .asFloatBuffer()
  vertsData.put(verts)
  vertsData.position(0)

  val positionAttrib = glGetAttribLocation(program, "position")
  val timeUniform = glGetUniformLocation(program, "time")

  val time_0 = currentTimeMillis()
  def time = (currentTimeMillis() - time_0).toFloat/1000

  glClearColor(1, 0, 0, 0)

  override def draw() {
    glClear(GL_COLOR_BUFFER_BIT)
    glUseProgram(program)
    glVertexAttribPointer(positionAttrib, 2, GL_FLOAT, false, 0, vertsData)
    glEnableVertexAttribArray(positionAttrib)
    
    glUniform1f(timeUniform, time)
    glDrawArrays(GL_TRIANGLES, 0, 3)
  }
}
