package com.computemachines.graphics

import java.nio.ByteBuffer
import java.nio.ByteOrder.nativeOrder

trait Mesh {
  val vertices: Array[Float]
  def vertexCount = vertices.length
  lazy val buffer = ByteBuffer.allocateDirect(vertices.length * 4)
    .order(nativeOrder())
    .asFloatBuffer().put(vertices)
}
