package com.computemachines.plot

import org.lwjgl.opengl.GL11._

trait Renderer {
  def draw(): Unit
}

// must be instantiated in drawing thread
class EmptyRender extends Renderer {
  val program = Util.makeProgram("id.vert", "const.frag")
  val verts = List[Float](-1, -1, 1, -1, 1, 1)
  println("Renderer constructor?: "+Thread.currentThread.getName)
  glClearColor(1, 0, 0, 0)

  override def draw() {
    glClear(GL_COLOR_BUFFER_BIT)
    println("Renderer#draw: "+Thread.currentThread.getName)
  }
}
