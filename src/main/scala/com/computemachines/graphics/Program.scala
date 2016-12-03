package com.computemachines.graphics

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._

import com.computemachines.graphics.Util._

class Shader(gl_type: Int)(resource: String) {
  val shader = makeShader(resource, gl_type)
}

class VertexShader(resource: String) extends
    Shader(GL_VERTEX_SHADER)(resource)
class FragmentShader(resource: String) extends
    Shader(GL_FRAGMENT_SHADER)(resource)

object PassVertexShader extends VertexShader("id.vert")
object UniformColorFragmentShader extends FragmentShader("const.frag")

class Program(
  val vertex: VertexShader,
  val fragment: FragmentShader) {
  val program = Program.join(vertex, fragment)
  def useProgram(): Unit = glUseProgram(program)
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

class SimpleProgram(val meshes: List[Mesh])
    extends Program(PassVertexShader, UniformColorFragmentShader) {
  val aPosition = glGetAttribLocation(program, "a_vec4_position")
  val uColor = glGetUniformLocation(program, "u_vec4_color")
  
  private def bind(mesh: TriangleMesh): Unit = {
    mesh.buffer.position(0)
    glVertexAttribPointer(
      aPosition, mesh.dimension, GL_FLOAT, false, 0, mesh.buffer)
    glEnableVertexAttribArray(aPosition)
    return ()
  }

  def draw() {
    meshes foreach {
      case mesh: TriangleMesh => {
        bind(mesh.asInstanceOf[TriangleMesh])
        glDrawArrays(GL_TRIANGLES, 0, mesh.vertices.length)
        return ()
      }
      case mesh: Mesh => println("cannot draw "+mesh)
    }
  }

  def setUniform() {
    glUniform4f(uColor, 1, 1, 0, 1)
  }
}
