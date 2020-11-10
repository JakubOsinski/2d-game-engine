package renderer;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shader
{

    private int shaderProgramID;
    private String vertexSource;
    private String fragmentSource;
    private String filepath;

    private boolean beingUsed = false;

    public Shader(String filepath) {
this.filepath=filepath;
        try{
        String source = new String(Files.readAllBytes(Paths.get(filepath)));
        String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

        //find the first pattern after #type 'pattern'
        int index = source.indexOf("#type") + 6;
        int eol  = source.indexOf( "\r\n", index); //eol = end of the line
            String firstPattern = source.substring(index, eol).trim();

            index = source.indexOf("#type", eol) + 6;
            eol = source.indexOf("\r\n", index);
            String secondPattern = source.substring(index, eol).trim();

            if(firstPattern.equals("vertex")) {
                vertexSource=splitString[1];
            } else if(firstPattern.equals("fragment")) {
                fragmentSource = splitString[1];
            } else {
                throw new IOException("Unexpected token '" + firstPattern + "'");
            }
            if(secondPattern.equals("vertex")) {
                vertexSource=splitString[2];
            } else if(secondPattern.equals("fragment")) {
                fragmentSource = splitString[2];
            } else {
                throw new IOException("Unexpected token '" + secondPattern + "'");
            }


        }catch(IOException e) {
    e.printStackTrace(); assert  false: "Error could not open file for shader: '" + filepath + "'";
        }
    // //   System.out.println(vertexSource);
    //    System.out.println(fragmentSource);
    } // end Shader constructor

    public void compile() {
        //**********************************
        //**** compile and link shaders ****
        //**********************************
        int vertexId, fragmentId;
        //compile and link shaders, then check for errors
        //first load and compile vertex shader
        vertexId = glCreateShader(GL_VERTEX_SHADER);
        //pass the shader source code to the GPU
        glShaderSource(vertexId, vertexSource);
        glCompileShader(vertexId);
        //check for errors in compilation process
        int success = glGetShaderi(vertexId, GL_COMPILE_STATUS);
        if(success == GL_FALSE) { // 0
            int stringLength = glGetShaderi(vertexId, GL_INFO_LOG_LENGTH);
            System.out.println("Error : '" + filepath + "'\nvertex shader compilation failed");
            System.out.println(glGetShaderInfoLog(vertexId, stringLength));
            assert false: ""; }

        //first load and compile fragment shader
        fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        //pass the shader source code to the GPU
        glShaderSource(fragmentId, fragmentSource);
        glCompileShader(fragmentId);
        //check for errors in compilation process
        success = glGetShaderi(fragmentId, GL_COMPILE_STATUS);
        if(success == GL_FALSE) { // 0
            int stringLength = glGetShaderi(fragmentId, GL_INFO_LOG_LENGTH);
            System.out.println("Error : '" + filepath+ "'\nFragment shader compilation failed");
            System.out.println(glGetShaderInfoLog(fragmentId, stringLength));
            assert false: ""; }

        //LINK shaders and check for errors

        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexId);
        glAttachShader(shaderProgramID, fragmentId);
        glLinkProgram(shaderProgramID);
        //check for linking errors
        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if(success == GL_FALSE) {
            int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("Error : '" + filepath + "'\nLinking of shaders failed");
            System.out.println(glGetProgramInfoLog(shaderProgramID, len));
            assert false: "";
        }
    }
    public void use() {
        if(!beingUsed) {
            glUseProgram(shaderProgramID);
            beingUsed = true;
        }

    }
    public void detatch() {
glUseProgram(0);
beingUsed = false;
    }

    public void uploadMat4f(String varName, Matrix4f mat4) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16); //4*4
        mat4.get(matBuffer);

        glUniformMatrix4fv(varLocation, false, matBuffer);
    }
    public void uploadMat3f(String varName, Matrix3f mat3) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9); //4*4
        mat3.get(matBuffer);

        glUniformMatrix3fv(varLocation, false, matBuffer);
    }

    public void uploadVec4f(String varName, Vector4f vec) // for vec3 change uploadVec4f to uploadVec3f + glUniform3f
    {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform4f(varLocation, vec.x, vec.y, vec.z, vec.w);
    }
    public void uploadFloat(String varName, float val) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1f(varLocation, val);
    }
    public void uploadInt(String varName, int val) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1i(varLocation, val);
    }
    public void uploadVec3f(String varName, Vector3f vec) // for vec3 change uploadVec4f to uploadVec3f + glUniform3f
    {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform3f(varLocation, vec.x, vec.y, vec.z);
    }
    public void uploadVec2f(String varName, Vector2f vec) // for vec3 change uploadVec4f to uploadVec3f + glUniform3f
    {
        int varLocation = glGetUniformLocation( shaderProgramID, varName);
        use();
        glUniform2f(varLocation, vec.x, vec.y);
    }

    public void uploadTexture(String varName, int slot)
    {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1i(varLocation, slot);
    }

    public void uploadIntArray(String varName, int[]array)
    {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1iv(varLocation, array);
    }

}
