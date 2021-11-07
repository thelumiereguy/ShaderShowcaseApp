#version 100

#ifdef GL_ES
precision mediump float;
#endif

uniform float u_time;
uniform vec2 u_resolution;
uniform vec2 u_mouse;

void main(){
    float mx=(u_resolution.x/u_resolution.y);
    vec2 uv=(gl_FragCoord.xy/u_resolution-.5);
    uv.x*=u_resolution.x/u_resolution.y;
    vec3 color=vec3(0.);

    float angle=u_time*.1;

    uv=uv*mat2(cos(angle), -sin(angle), sin(angle), cos(angle));

    float radius=smoothstep(.13,
    pow(length(uv)-(
    (sin(u_time)*.1)*cos(12.*atan(uv.y, uv.x))
    ), .7),
    .14);

    float radius2=smoothstep(.16,
    pow(length(uv)-(
    (.1)*sin(12.*(atan(uv.y, uv.x)))
    ), .9),
    .1);

    float radius3=smoothstep(.13,
    pow(length(uv)-(
    -(sin(u_time)*.1)*cos(12.*atan(uv.y, uv.x))
    ), .8),
    .14);

    float center=smoothstep(.16,
    pow(length(uv), .5),
    .14);

    vec3 color1=vec3(1., .6, 1.);
    vec3 color2=vec3(1., .6, .8);

    color=(color1*radius2+color2*(radius+radius3))+
    (center*vec3(.9, .7, .7));

    //color/=length(uv);

    gl_FragColor=vec4(color, 1.);
}