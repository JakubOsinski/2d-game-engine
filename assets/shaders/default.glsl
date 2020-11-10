//vertex shader
#type vertex
#version 330 core
layout (location=0) in vec3 aPos;//a = attribiute
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aTexCoords;
layout (location=3) in float aTexId;

uniform mat4 uProjection;
uniform mat4 uView;
//passing to fragment shader
out vec4 fColor; //f = fragment
out vec2 fTexCoords;
out float fTexId;

void main() {
    fColor = aColor;
    fTexCoords = aTexCoords;
    fTexId = aTexId;

        gl_Position =uProjection * uView *  vec4(aPos, 1.0);
}

    //fragment shader \/
    #type fragment
    #version 330 core


in vec4 fColor;
in vec2 fTexCoords;
in float fTexId;

uniform sampler2D uTextures[8];


out vec4 color;
void main() {
    //float avg = ((fColor.r+ fColor.g + fColor.b) / 3);//black and white
    //color = vec4(avg, avg,avg, 1); //black and white
//    color = fColor;
//    color = texture(TEX_SAMPLER, fTexCoords);
//    // color = sin(uTime) * fColor;

    if(fTexId > 0) {
    int id = int(fTexId);
    color = fColor * texture(uTextures[id], fTexCoords);
   //     color = vec4(fTexCoords, 0 ,1); // change position of colors in SpriteRenderer
    } else {
        color = fColor;
    }
}