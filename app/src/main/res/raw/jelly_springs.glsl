#version 100

#ifdef GL_ES
precision mediump float;
#endif

uniform float u_time;
uniform vec2 u_resolution;
uniform vec2 u_mouse;

const vec3 backgroundColor = vec3(255./255., 235./255., 219./255.);
const vec3 foreground = vec3(24./255., 23./255., 20./255.);

void main(){
  vec2 coord=(gl_FragCoord.xy/u_resolution)*2.-1.;
  coord.x*=u_resolution.x/u_resolution.y;
    vec3 color=vec3(backgroundColor);

    for (float index=1.;index<=10.;index++){
        float wave=smoothstep(
        coord.x,
        (sin((coord.y*index*0.7)+u_time)*cos(coord.y*10.)*((cos(u_time + coord.y)*0.1))),
        coord.x-.002
        );
        color-=(vec3(wave))*foreground;
    }

    gl_FragColor=vec4(color, 1.);
}