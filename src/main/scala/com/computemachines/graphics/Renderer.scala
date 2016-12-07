package com.computemachines.graphics

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL13._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._

trait Renderer {
  def draw(): Unit
}


// must be instantiated in drawing thread
class EmptyRender extends Renderer {
  // val verts = Array[Float](-1, -1, 1, -1, 1, 1)
  // val mesh = new SimpleTriangleMesh(2)(verts)

  glEnable(GL_TEXTURE_2D)
  val square = new DefaultTextureSquare()

  val program = new MVPTextureProgram
  program.meshes += square
  glClearColor(0, 0, 0, 0)
  program.useProgram()
  program.vertexAttrib(square)
  
  override def draw() {
    glClear(GL_COLOR_BUFFER_BIT)
    program.draw()
  }
}
