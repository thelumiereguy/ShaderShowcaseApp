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

float getAngle(vec2 position, float slices){
    return
    abs(
    fract(
    atan(position.y, position.x)*3.14*slices
    )-.5
    );
}

void main(){
    vec2 coord=gl_FragCoord.xy/u_resolution-.5;
     coord.x*=u_resolution.x/u_resolution.y;
    vec3 color=vec3(0.);
    vec2 newCoords=coord*3.5;
    newCoords=vec2(fract(newCoords.x), fract(newCoords.y));
    float circles=length(newCoords-.5);

    float centerCircle=length(coord);

    coord*=rotate(u_time*.2);
    float angle=getAngle(coord, 1.);
    color+=((angle-circles)*4.);
    gl_FragColor=vec4(color, 1.);
}