#version 100

#ifdef GL_ES
precision mediump float;
#endif

uniform float u_time;
uniform vec2 u_resolution;
uniform vec2 u_mouse;

const vec3 light_green=vec3(106./255., 231./255., 90./255.);

void main(){
    vec2 coord=(gl_FragCoord.xy/u_resolution)*2.-1.;
    coord.x*=u_resolution.x/u_resolution.y;
    vec3 color=vec3(.1059, .0863, .0706);

    for (float index=1.;index<=5.;index++){
        float inverseY=exp((coord.y*(index*.4)));

        float sinX=smoothstep(
        coord.x,
        inverseY*sin(
        coord.y*(u_time/index)-u_time
        )*index*.03
        , coord.x-(.002)*(coord.y+20.)
        );

        color+=sinX*
        mix(vec3(.0902, .1725, .3529), vec3(.9804, .549, .2588), (coord.y+.5));;
    }

    gl_FragColor=vec4(color, 1.);
}