#version 100

#ifdef GL_ES
precision mediump float;
#endif

uniform float u_time;
uniform vec2 u_resolution;
uniform vec2 u_mouse;

mat2 scale(vec2 scale){
    return mat2(scale.x, 0., 0., scale.y);
}

void main(){
    vec2 coord=gl_FragCoord.xy/u_resolution;
    coord=(coord*2.)-1.;
    coord.x*=u_resolution.x/u_resolution.y;

    vec3 color=vec3(0.);

    coord=(coord-vec2(0., .3))*scale(vec2(.7, .8));

    float heart=length(coord)-abs(atan(coord.x, coord.y)*.15);

    heart=sqrt(smoothstep(0., heart, .05));

    for (int index=1;index<=10;index++){
        float waves=smoothstep(coord.x, float(index)*.03*sin(u_time+coord.y), coord.x-.001);
        waves=waves*step(heart, 0.);
        color+=waves;
    }

    color+=((heart)*vec3(.8549, 0., .298));

    gl_FragColor=vec4(color, 1.);
}