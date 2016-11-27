package com.computemachines.physics;

import org.lwjgl._
import org.lwjgl.glfw._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl._
import org.lwjgl.opengl.GL11._

import com.computemachines.plot._

object Hello extends App{
  val window = new EscWindow()
  window.loop(() => ())
}
