package com.computemachines.graphics

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import java.nio.FloatBuffer

class SimpleProgram extends Program(PassVertexShader, UniformColorFragmentShader) {
  override val attributeOf: Map[AttributeType, Int] =
    Map(Position -> glGetAttribLocation(gl_program, "a_vec4_position"))
  override val uniformOf: Map[UniformType, Int] = Map(ColorUniform -> glGetUniformLocation(gl_program, "u_vec4_color"))
}
