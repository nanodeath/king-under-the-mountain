#ifdef GL_ES
    precision mediump float;
#endif

varying vec2 v_position;
//varying vec4 v_color;
//varying vec2 v_texCoords;
//uniform sampler2D u_texture;
uniform sampler2D u_textureDiffuse;
uniform sampler2D u_textureLighting;
uniform float u_alpha;

void main() {
    vec2 correctedCoords = vec2(
        (v_position.x + 1.0) / 2.0,
        (v_position.y + 1.0) / 2.0
    );

    vec4 diffuseColor = texture2D(u_textureDiffuse, correctedCoords);
    vec4 lightingColor = texture2D(u_textureLighting, correctedCoords);

    vec4 combined = diffuseColor * lightingColor;

    gl_FragColor = combined * u_alpha;
}
