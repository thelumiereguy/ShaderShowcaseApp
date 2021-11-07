#version 100

#ifdef GL_ES
precision mediump float;
#endif

uniform float u_time;
uniform vec2 u_resolution;
uniform vec2 u_mouse;

const float PI=3.14159265359;

mat2 scale(vec2 scale){
    return mat2(scale.x, 0., 0., scale.y);
}

mat2 rotate(float angle){
    return mat2(cos(angle), -sin(angle), sin(angle), cos(angle));
}

void main(){
    //normalize coords
    vec2 coord=gl_FragCoord.xy*2./u_resolution.xy-1.;
    coord.x*=u_resolution.x/u_resolution.y;

    //base color
    vec3 color=vec3(0.);

    //transform coords
    coord*=scale(
    vec2(
    clamp(abs(1.-(u_time*.1)), 0., 1.)
    )
    )*rotate(-u_time);

    //angle of each coordinate against horizontal axes
    float angle=(atan(coord.y, coord.x)+PI)/3.*PI;

    //divide angle further according to magnitude
    angle=fract(angle/length(coord*25.));

    color+=angle;

    gl_FragColor=vec4(color, 1.);
}