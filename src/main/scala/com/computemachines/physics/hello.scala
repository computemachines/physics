package com.computemachines.physics;

import org.lwjgl._
import org.lwjgl.glfw._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl._
import org.lwjgl.opengl.GL11._

import scala.Null

object Hello extends App{
  glfwInit()
  
  val window: Long = glfwCreateWindow(400, 400, "Hello worlds",
    null.asInstanceOf[Int], null.asInstanceOf[Int])
  glfwMakeContextCurrent(window)
  glfwMakeContextCurrent(window);
  glfwSwapInterval(1);

  
  glfwSetKeyCallback(window,
    new GLFWKeyCallbackI {
      override def invoke(window: Long, key: Int, scancode: Int,
        action: Int, mods: Int) = {
        if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
          glfwSetWindowShouldClose(window, true)
      }
    })
  
  GL.createCapabilities();
  glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
  while (!glfwWindowShouldClose(window)) {
    glClear(GL_COLOR_BUFFER_BIT);
    glfwSwapBuffers(window)
    glfwPollEvents()
  }

  glfwTerminate()
  println("done")
}
