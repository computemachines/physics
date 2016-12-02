package com.computemachines.plot

import org.lwjgl.glfw._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL


object GLINIT {
  glfwInit()
}

class Window(rendererWrapper: => Renderer) extends Thread{
  GLINIT

  val window = glfwCreateWindow(600, 600, "Physics",
    null.asInstanceOf[Int], null.asInstanceOf[Int])
  glfwSetKeyCallback(window,
    new GLFWKeyCallbackI {
      override def invoke(window: Long, key: Int, scancode: Int,
        action: Int, mods: Int) = {
        if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
          glfwSetWindowShouldClose(window, true)
      }
    })

  override def run(): Unit = {
    activate()
    val renderer = rendererWrapper
    while(!glfwWindowShouldClose(window)){
      renderer.draw()
      glfwSwapBuffers(window)
      glfwPollEvents()
    }
    glfwTerminate()
  }

  def activate() = {
    glfwMakeContextCurrent(window)
    glfwSwapInterval(1);
    GL.createCapabilities();
    glfwShowWindow(window)
  }
}

object Window {
  val mWindow = new Window(
    new EmptyRender()
  )
  def apply() = {
    mWindow
  }
  
}
