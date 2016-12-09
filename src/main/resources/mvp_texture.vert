#version 120

//uniform mat4 u_mat4_mvp;
attribute vec4 a_vec4_position;
attribute vec2 a_vec2_texCoord;

varying vec2 v_vec2_texCoord;

void
main()
{
  v_vec2_texCoord = a_vec2_texCoord;
  //gl_Position = u_mat4_mvp * a_vec4_position;
  gl_Position = a_vec4_position;
}
