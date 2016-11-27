package com.computemachines.plot

import org.lwjgl.glfw._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL

class Window {
  glfwInit()
  lazy val window = glfwCreateWindow(600, 600, "Physics",
    null.asInstanceOf[Int], null.asInstanceOf[Int])
  glfwMakeContextCurrent(window)
  glfwSwapInterval(1);
  GL.createCapabilities();
  glfwShowWindow(window)
  def loop(opengl: =>Unit) = {
    while (!glfwWindowShouldClose(window)) {
      glfwSwapBuffers(window)
      glfwPollEvents()
    }
    glfwTerminate()
  }
}

class EscWindow extends Window {
  glfwSetKeyCallback(window,
    new GLFWKeyCallbackI {
      override def invoke(window: Long, key: Int, scancode: Int,
        action: Int, mods: Int) = {
        if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
          glfwSetWindowShouldClose(window, true)
      }
    })
}
