#version 100

#ifdef GL_ES
precision mediump float;
#endif

uniform float u_time;
uniform vec2 u_resolution;
uniform vec2 u_mouse;

void main(){
    vec2 coord=(gl_FragCoord.xy/u_resolution)*2.-1.;

    vec3 color=vec3(0.);

    for (float index=1.;index<=10.;index++){
        float wave=smoothstep(
        coord.x,
        (sin((coord.y*index)+u_time)*(coord.y*8.)*.1),
        coord.x-.001
        );
        color+=wave;
    }

    gl_FragColor=vec4(color, 1.);
}