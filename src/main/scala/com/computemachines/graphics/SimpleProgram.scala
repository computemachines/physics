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
  private def bind(buffer: FloatBuffer, attribute: Attribute): Unit =
    attribute match {
      case Attribute(attributeType, components, gl_type, start, stride) => {
        buffer.position(start)
        glVertexAttribPointer(
          attributeTypes(attributeType),
          components,
          gl_type,
          false,
          stride,
          buffer
        )
        glEnableVertexAttribArray(attributeTypes(attributeType))
      }
    }

  private def bind(mesh: Mesh) {
    mesh.attributes foreach { bind(mesh.buffer, _) }
  }

  def draw() {
    for (mesh <- meshes) {
      bind(mesh)
      glDrawArrays(mesh.gl_drawType, 0, mesh.numVertices)        
    }
  }

  def setUniform() {
    glUniform4f(uColor, 1, 1, 0, 1)
  }
}
