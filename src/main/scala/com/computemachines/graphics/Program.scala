package com.computemachines.graphics

import java.nio.FloatBuffer
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._

import com.computemachines.graphics.Util._


// should extend something like List[Mesh]
abstract class Program(
  vertex: VertexShader,
  fragment: FragmentShader){
  var meshes = Set[Mesh]()
  val gl_program: Int = Program.join(vertex, fragment)
  val attributeOf: Map[AttributeType, Int]
  val uniformOf: Map[UniformType, Int]
  def useProgram(): Unit = glUseProgram(gl_program)
  private def vertexAttrib(buffer: FloatBuffer, attribute: Attribute): Unit =
    attribute match {
      case Attribute(attributeType, components, gl_type, start, stride) => {
        buffer.position(start)
        glVertexAttribPointer(
          attributeOf(attributeType),
          components,
          gl_type,
          false,
          stride,
          buffer
        )
        glEnableVertexAttribArray(attributeOf(attributeType))
      }
    }

  def vertexAttrib(mesh: Mesh) {
    mesh.attributes foreach { vertexAttrib(mesh.buffer, _) }
  }

  def draw() {
    for (mesh <- meshes) {
      vertexAttrib(mesh)
      mesh match {
        case mesh: TextureMesh => glUniform1i(uniformOf(Texture2DUniform), mesh.texture.unit.index)
        case _ => Unit
      }
      mesh.draw()
    }
  }
}
object Program {
  def join(vertex: VertexShader, fragment: FragmentShader): Int = {
    val program = glCreateProgram()
    glAttachShader(program, vertex.shader)
    glAttachShader(program, fragment.shader)
    glLinkProgram(program)
    if(!programLinkStatus(program)){
      println(glGetProgramInfoLog(program))
      System.out.flush()
      throw new ProgramLinkException
    }
    program
  }
}
