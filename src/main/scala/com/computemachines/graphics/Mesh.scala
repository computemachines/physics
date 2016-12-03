package com.computemachines.graphics

import java.nio.ByteBuffer
import java.nio.ByteOrder.nativeOrder

class Mesh(val vertices: Array[Float]) {
  lazy val buffer = ByteBuffer.allocateDirect(vertices.length * 4)
    .order(nativeOrder())
    .asFloatBuffer().put(vertices)
}
