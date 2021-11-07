#version 100

#ifdef GL_ES
precision mediump float;
#endif

uniform float u_time;
uniform vec2 u_resolution;
uniform vec2 u_mouse;

const float scale=10.;

mat2 rotate(float angle){
    return mat2(cos(angle), -sin(angle), sin(angle), cos(angle));
}

void main(){
    vec2 coord=((gl_FragCoord.xy*2.)/u_resolution)-1.;
    coord.x*=u_resolution.x/u_resolution.y;
    vec3 color=vec3(.1);

    //Rotate the coordinate system by
    //angle equal to magnitute of each coords
    vec2 newCoords=coord*rotate(
    u_time+length((coord)*exp2(sin(u_time)*2.6)
    ));

    float x=abs(sin(newCoords.x*3.14))+.3;
    float y=abs(sin(newCoords.y))*.1+.3;
    color+=(1.-(x/y))*30.;

    gl_FragColor=vec4(color, 1.);
}