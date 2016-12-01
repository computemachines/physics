package com.computemachines.plot

import org.lwjgl.opengl.GL11._

trait Renderer {
  def init(): Unit
  def draw(): Unit
}

class EmptyRender extends Renderer {
  lazy val program = Util.makeProgram("id.vert", "const.frag")
  println("Renderer constructor?: "+Thread.currentThread.getName)
  override def init() {
    println("Renderer#init: "+Thread.currentThread.getName)
  }

  override def draw() {
    glClear(GL_COLOR_BUFFER_BIT)
    println("Renderer#draw: "+Thread.currentThread.getName)
  }
}
