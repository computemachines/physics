package com.computemachines.graphics

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
    val shaderSource = Source.fromFile(resBaseName+res).mkString
    val shader = glCreateShader(shaderType)
    glShaderSource(shader, shaderSource)
    glCompileShader(shader)
    if(!shaderCompileStatus(shader)) {
      println(s"Shader $res info log: \n" ++glGetShaderInfoLog(shader))
      println(s"Shader $res source: \n"++shaderSource)
      System.out.flush()
      throw new ShaderCompileException
    }
    shader
  }
}
