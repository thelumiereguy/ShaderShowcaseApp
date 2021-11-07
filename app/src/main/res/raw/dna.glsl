#version 100

#ifdef GL_ES
precision mediump float;
#endif

uniform float u_time;
uniform vec2 u_resolution;
uniform vec2 u_mouse;

const float scaleFactor=20.;

const float PI=3.14159265359;

float getCircle(vec2 position, float radius){
    return step(radius, length(position-vec2(.5)));
}

float getSineWave(vec2 position, float epsilon, float offset){
    return step(abs(sin(position.y*3.14*3.+(u_time*2.)+offset)*.2+.5-(position.x)), epsilon);
}

void main(){
    vec2 coord=gl_FragCoord.xy/u_resolution;
    vec3 color=vec3(0.);

    //scale coordinates
    vec2 scaledCoords=coord*scaleFactor;

    scaledCoords.x*=u_resolution.x/u_resolution.y;
    //divide into multiple smaller coordinate systems
    scaledCoords=vec2(fract(scaledCoords.x), fract(scaledCoords.y));

    float sinX=getSineWave(coord, .02, .0);
    float sinX2=getSineWave(coord, .02, PI);

    color+=(sinX+sinX2)*getCircle(scaledCoords, .6);

    // color+=getCircle(scaledCoords,.2);

    gl_FragColor=vec4(color, 1.);
}
