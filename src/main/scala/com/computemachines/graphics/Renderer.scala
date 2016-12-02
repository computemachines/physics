package com.computemachines.graphics

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._

trait Renderer {
  def draw(): Unit
}


// must be instantiated in drawing thread
class EmptyRender extends Renderer {
  val program = SimpleProgram.program

  val verts = Array[Float](-1, -1, 1, -1, 1, 1)
  val mesh: Mesh = new TriangleMesh(2)(verts)
  mesh.buffer.position(0)

  val aPosition = glGetAttribLocation(program, "a_vec4_position")
  val uColor = glGetUniformLocation(program, "u_vec4_color")

  glClearColor(0, 0, 0, 0)

  override def draw() {
    glClear(GL_COLOR_BUFFER_BIT)
    glUseProgram(program)
    glVertexAttribPointer(aPosition, 2, GL_FLOAT, false, 0, mesh.buffer)
    glEnableVertexAttribArray(aPosition)
    
    glUniform4f(uColor, 1.0f, 0.0f, 0.0f, 1.0f)
    glDrawArrays(GL_TRIANGLES, 0, 3)
  }
}
