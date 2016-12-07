package com.computemachines.graphics

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.ByteOrder.nativeOrder

// Meshes only care about attributes. They do not care about uniforms.

abstract class Mesh(val buffer: FloatBuffer, val attributes: List[Attribute]) {
  val numVertices: Int
  def gl_drawType: Int
  def draw() {
    glDrawArrays(gl_drawType, 0, numVertices)
  }
}

abstract class TextureMesh(buffer: FloatBuffer, attributes: List[Attribute]) extends Mesh(buffer, attributes) {
  val texture: Texture2D
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

class TextureTriangleMesh(val dimension: Int)(data: Array[Float])(val texture: Texture2D)
    extends TextureMesh(Util.floatBuffer(data),
      List(
        Attribute(Position, dimension, GL_FLOAT, 0, (dimension+2)*4),
        Attribute(UVCoords, 2, GL_FLOAT, dimension, (dimension+2)*4)
      ))
    with Triangles {
  override val numVertices = (data.length/(dimension+2)).toInt
}

class DefaultTextureSquare extends TextureTriangleMesh(2)(
  Array[Float](
    -1, -1, 0, 0,
    1, -1,  1, 0,
    1, 1,   1, 1,
    -1, -1, 0, 0,
    1, 1,   1, 1,
    -1, 1,  0, 1)
)(Texture2D("basn6a08.png"))
