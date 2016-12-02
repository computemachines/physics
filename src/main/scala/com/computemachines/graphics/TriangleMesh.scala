package com.computemachines.graphics

import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._

class TriangleMesh(val dimension: Int)(val vertices: Array[Float]) extends Mesh{
  require(vertices.length % dimension == 0)
}

