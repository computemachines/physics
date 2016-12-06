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

object MVPTextureVertexShader extends VertexShader("mvp_texture.vert")
object TextureFragmentShader extends FragmentShader("texture.frag")
