#version 100

#ifdef GL_ES
precision mediump float;
#endif

uniform float u_time;
uniform vec2 u_resolution;
uniform vec2 u_mouse;

mat2 rotate(float angle){
    return mat2(cos(angle), -sin(angle), sin(angle), cos(angle));
}

void main(){
    vec2 coord=(gl_FragCoord.xy/u_resolution)-.5;
    coord.x*=u_resolution.x/u_resolution.y;
    vec3 color=vec3(0.);

    vec2 newCoords=fract(coord)-.5;

    float flower=step(
    (sin(coord.x*100.)*cos(coord.y*100.+(u_time*5.)))*.27,
    coord.y
    );

    color=(flower*vec3(.9216, 1., .2235));

    gl_FragColor=vec4(color, 1.);
}
