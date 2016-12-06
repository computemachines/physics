package com.computemachines.graphics

import org.lwjgl.opengl.GL20._
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.ByteOrder.nativeOrder

abstract class AttributeType
object Position extends AttributeType
object UVCoords extends AttributeType

case class Attribute(
  attributeType: AttributeType,
  components: Int,
  gl_type: Int,
  start: Int,
  stride: Int) // TODO check if stride counts bytes or components
  // buffer: FloatBuffer) 
