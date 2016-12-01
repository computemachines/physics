package com.computemachines.plot

import scala.io.Source
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL11.GL_TRUE

class ShaderCompileException extends RuntimeException
class ProgramLinkException extends RuntimeException

package object Util {
  val resBaseName = "src/main/resources/"
  def shaderCompileStatus(shader: Int): Boolean = {
    glGetShaderi(shader, GL_COMPILE_STATUS) == GL_TRUE
  }
  def programLinkStatus(program: Int): Boolean = {
    glGetProgrami(program, GL_LINK_STATUS) == GL_TRUE
  }

  def makeShader(res: String, shaderType: Int): Int = {
    val shaderSource = Source.fromFile(resBaseName+res) mkString "\n"
    val shader = glCreateShader(shaderType)
    glShaderSource(shader, shaderSource)
    if(!shaderCompileStatus(shader)) {
      println(glGetShaderInfoLog(shader))
      throw new ShaderCompileException
    }
    shader
  }

  def makeProgram(vertRes: String, fragRes: String): Int = {
    val vertex = makeShader(vertRes, GL_VERTEX_SHADER)
    val fragment = makeShader(fragRes, GL_FRAGMENT_SHADER)
    val program = glCreateProgram()
    glAttachShader(program, vertex)
    glAttachShader(program, fragment)
    glLinkProgram(program)
    if(!programLinkStatus(program)){
      println(glGetProgramInfoLog(program))
      throw new ProgramLinkException
    }
    program
  }
}
