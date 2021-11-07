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

    //offset and scale
    coord=(coord-vec2(0., .3))*scale(vec2(.77, .8));

    //create the heart shape
    float heart=length(coord)-abs(atan(coord.x, coord.y)*.15);

    //for the gradient outside heart
    heart=sqrt(smoothstep(0., heart, .01));

    /**
    * create waves with diffferent amplitude and mask using the shape
    **/
    for (int index=1;index<=6;index++){
        float waves1=smoothstep(coord.y,
        (float(index)*.02*cos((u_time*4.)+(coord.x*30.)))-.18,
        coord.y-.003
        );
        waves1=(waves1-.06)*step(heart, 0.);
        color+=waves1*vec3(1., .4039, .0039);
    }

    color+=(smoothstep(.9, heart-.2, .6)*vec3(.2941, .0824, .7882));

    gl_FragColor=vec4(color, 1.);
}