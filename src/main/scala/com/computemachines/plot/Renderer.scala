package com.computemachines.plot

trait Renderer {
  def init(): Unit
  def draw(): Unit
}

class EmptyRender extends Renderer {
  override def init() {
  }

  override def draw() {

  }
}
