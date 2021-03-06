package com.computemachines.graphics

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.ByteOrder.nativeOrder

// Meshes only care about attributes. They do not care about uniforms.

// Meshes should not have any draw commands

abstract class Mesh(val buffer: FloatBuffer, val attributes: List[Attribute]) {
  val numVertices: Int
  def gl_drawType: Int
}

abstract class TextureMesh(buffer: FloatBuffer, attributes: List[Attribute]) extends Mesh(buffer, attributes) {
  val texture: Texture
}

trait Triangles {
  def gl_drawType: Int = GL_TRIANGLES
}

class SimpleTriangleMesh(val dimension: Int)(vertices: Array[Float])
    extends Mesh(Util.floatBuffer(vertices),
      List(Attribute(Position, dimension, GL_FLOAT, 0, 0)))
    with Triangles {
  override val numVertices = vertices.length
}

class SimpleSquare extends SimpleTriangleMesh(2)(
  Array[Float](
    -1, -1, 1, -1, 1, 1,
    -1, -1, 1, 1, -1, 1)
)

class TextureTriangleMesh(val dimension: Int)(data: Array[Float])(val texture: Texture)
    extends TextureMesh(Util.floatBuffer(data),
      List(
        Attribute(Position, dimension, GL_FLOAT, 0, (dimension+2)*4),
        Attribute(UVCoords, 2, GL_FLOAT, dimension, (dimension+2)*4)
      ))
    with Triangles {
  override val numVertices = (data.length/(dimension+2)).toInt
}

class DefaultTextureSquare(texture: Texture) extends TextureTriangleMesh(2)(
  Array[Float](
    -1, -1, 0, 1,
    1, -1,  1, 1,
    1, 1,   1, 0,
    -1, -1, 0, 1,
    1, 1,   1, 0,
    -1, 1,  0, 0)
)(texture)

class GaussianTextureSquare extends DefaultTextureSquare(GaussianPlot2DTexture())

class TestTextureSquare extends DefaultTextureSquare(ImageTexture2D("test.png"))
