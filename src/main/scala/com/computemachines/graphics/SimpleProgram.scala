package com.computemachines.graphics

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import java.nio.FloatBuffer

class SimpleProgram extends Program(PassVertexShader, UniformColorFragmentShader) {
  override val attributeTypes: Map[AttributeType, Int] =
    Map(Position -> glGetAttribLocation(gl_program, "a_vec4_position"))


  val uColor = glGetUniformLocation(gl_program, "u_vec4_color")
  // bind mesh attribute to gl_program attribute

  def setUniform() {
    glUniform4f(uColor, 1, 1, 0, 1)
  }
}
