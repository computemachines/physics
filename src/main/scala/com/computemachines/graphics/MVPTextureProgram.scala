package com.computemachines.graphics

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import java.nio.FloatBuffer

class MVPTextureProgram
    extends Program(MVPTextureVertexShader, TextureFragmentShader) {
  override val attributeOf: Map[AttributeType, Int] =
    Map(
      Position -> glGetAttribLocation(gl_program, "a_vec4_position"),
      UVCoords -> glGetAttribLocation(gl_program, "a_vec2_texCoord")
    )
  // val gl_mvp = glGetUniformLocation(gl_program, "u_mat4_mvp")
  override val uniformOf: Map[UniformType, Int] = Map(
    Texture2DUniform -> glGetUniformLocation(gl_program, "u_sampler2D_textureUnit")
  )
  
}

