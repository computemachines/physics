#version 120

uniform vec4 u_vec4_color;

float random(vec2 coord) {
    return fract(sin(dot(coord ,vec2(12.9898,78.233))) * 43758.5453);
}

void
main()
{
  gl_FragColor = vec4(1.0)*random(floor(gl_FragCoord.xy/10.0));
}
