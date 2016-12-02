#version 120

attribute vec4 a_vec4_position;

void
main()
{
  gl_Position = a_vec4_position;
}
