package com.computemachines.graphics

import java.nio.FloatBuffer
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._

import com.computemachines.graphics.Util._


// wraps program on the gl.
// contains all meshes to be drawn with this program.
// can draw any mesh type.
abstract class Program(
  vertex: VertexShader,
  fragment: FragmentShader){
  var meshes = Set[Mesh]()
  val gl_program: Int = Program.join(vertex, fragment)
  val attributeOf: Map[AttributeType, Int]
  val uniformOf: Map[UniformType, Int]

  def useProgram(): Unit = glUseProgram(gl_program)

  // align per vertex attributes with buffer in client memory
  def vertexAttrib(mesh: Mesh) {
    mesh.attributes foreach {
      case Attribute(attributeType, components, gl_type, start, stride) => {
        mesh.buffer.position(start)

        glVertexAttribPointer(
          attributeOf(attributeType),
          components,
          gl_type,
          false,
          stride,
          mesh.buffer
        )
        glEnableVertexAttribArray(attributeOf(attributeType))
      }
    }
  }

  def draw() {
    useProgram()
    for (mesh <- meshes) {
      vertexAttrib(mesh)
      mesh match {
        case mesh: TextureMesh => {
          glUniform1i(uniformOf(Texture2DUniform), mesh.texture.unit.index)

          glBindTexture(GL_TEXTURE_2D, mesh.texture.gl_texture)
          glDrawArrays(mesh.gl_drawType, 0, mesh.numVertices)
          glBindTexture(GL_TEXTURE_2D, 0)
        }
        case _ => {
          glDrawArrays(mesh.gl_drawType, 0, mesh.numVertices)
        }
      }
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
