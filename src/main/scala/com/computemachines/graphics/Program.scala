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
}
object Program {
  def join(vertex: VertexShader, fragment: FragmentShader): Int = {
    val program = glCreateProgram()
    glAttachShader(program, vertex.shader)
    glAttachShader(program, fragment.shader)
    glLinkProgram(program)
    println(glGetProgramInfoLog(program))
    if(!programLinkStatus(program)){
      
      System.out.flush()
      throw new ProgramLinkException
    }
    program
  }
}

object SimpleProgram
    extends Program(PassVertexShader, UniformColorFragmentShader)
