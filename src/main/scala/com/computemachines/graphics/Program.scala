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

// should extend something like List[Mesh]
abstract class Program(
    vertex: VertexShader,
  fragment: FragmentShader){
  var meshes = Set[Mesh]()
  val gl_program: Int = Program.join(vertex, fragment)
  val attributeTypes: Map[AttributeType, Int]
  def useProgram(): Unit = glUseProgram(gl_program)
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
