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
  glEnable(GL_TEXTURE_2D)
  val square = new TestTextureSquare()

  val program = new MVPTextureProgram
  program.meshes += square
  glClearColor(0, 0, 0, 0)
  
  override def draw() {
    glClear(GL_COLOR_BUFFER_BIT)
    program.draw()
  }
}
