#version 100

#ifdef GL_ES
precision mediump float;
#endif

uniform float u_time;
uniform vec2 u_resolution;
uniform vec2 u_mouse;

float createSquare(vec2 position, float mid, float epsilon){
    return step(epsilon, abs(position.x-mid))*step(epsilon, abs(position.y-mid));
}

mat2 rotate(float angle){
    return mat2(cos(angle), -sin(angle), sin(angle), cos(angle));
}

mat2 scale(vec2 scale){
    return mat2(scale.x, 0., 0., scale.y);
}

const float scaleFactor=20.;

void main(){
    vec2 coord=gl_FragCoord.xy/u_resolution;
     coord.x*=u_resolution.x/u_resolution.y;
    vec3 color=vec3(0.);

    //scale coords
    vec2 scaledCoords=coord*scaleFactor;

    //create mini coordinate systems
    scaledCoords.x=fract(scaledCoords.x);
    scaledCoords.y=fract(scaledCoords.y);

    // rotate each coordinate systems
    scaledCoords=(scaledCoords-.5)*rotate((coord.y)+u_time);

    //then translate back to origin by adding 0.5
    scaledCoords+=vec2(.5);

    float squares=createSquare(scaledCoords, .5, .4);

    color+=squares;

    gl_FragColor=vec4(color, 1.);
}