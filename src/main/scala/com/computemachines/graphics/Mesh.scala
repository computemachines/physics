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
