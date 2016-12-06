#version 120

uniform sampler2D u_sampler2D_textureUnit;
varying vec2 v_vec2_texCoord;

void
main()
{
  gl_FragColor = texture2D(u_sampler2D_textureUnit, v_vec2_texCoord);
}
