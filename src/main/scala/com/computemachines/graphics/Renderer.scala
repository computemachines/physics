package com.computemachines.graphics

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._

trait Renderer {
  def draw(): Unit
}


// must be instantiated in drawing thread
class EmptyRender extends Renderer {
  val verts = Array[Float](-1, -1, 1, -1, 1, 1)
  val mesh = new TriangleMesh(2)(verts)

  val program = new SimpleProgram(List(mesh))
  glClearColor(0, 0, 0, 0)
  override def draw() {
    glClear(GL_COLOR_BUFFER_BIT)
    program.useProgram()
    program.setUniform()
    program.draw()
  }
}
