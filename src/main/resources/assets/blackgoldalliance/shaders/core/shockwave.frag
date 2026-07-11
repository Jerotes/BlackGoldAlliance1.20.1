#version 150 core

in vec2 texCoord;
in vec4 vertexColor;

uniform sampler2D Sampler0;
uniform float GameTime;

out vec4 fragColor;

void main() {
    vec4 tex = texture(Sampler0, texCoord);

    vec2 center = vec2(0.5, 0.5);
    float dist = distance(texCoord, center);
    float wave = sin(dist * 20.0 + GameTime * 10.0) * 0.1;
    vec2 distortedUV = texCoord + (texCoord - center) * wave;
    tex = texture(Sampler0, distortedUV);

    float glow = smoothstep(0.3, 0.7, dist);
    tex.rgb *= 2.0;

    tex.a *= (1.0 - dist) * vertexColor.a;

    fragColor = tex;
}