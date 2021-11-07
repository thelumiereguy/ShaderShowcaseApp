#version 100

#ifdef GL_ES
precision mediump float;
#endif

uniform float u_time;
uniform vec2 u_resolution;
uniform vec2 u_mouse;

float createTorus(vec2 position, float radius, float epsilon){
    return step(epsilon, abs(radius-length(position-.5)));
}

void main(){
    vec2 coord=gl_FragCoord.xy/u_resolution;
    coord.x*=u_resolution.x/u_resolution.y;
    vec3 color=vec3(0.);

    //scale coordinates
    coord*=10.;

    vec2 scaledCoords=coord;

    //get length from origin
    float scaledLength=cos(scaledCoords.y+u_time)*.2;
    float dividedLength=mod(-scaledLength, 4.)/8.;

    //divide into multiple smaller coordinate systems
    coord=vec2(fract(coord.x), fract(coord.y));

    //Create torus in all coordinate systems
    color+=createTorus(coord, .1, dividedLength);

    gl_FragColor=vec4(color, 1.);
}