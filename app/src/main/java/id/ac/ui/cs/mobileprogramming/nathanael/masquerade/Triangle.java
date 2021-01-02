package id.ac.ui.cs.mobileprogramming.nathanael.masquerade;

import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {

    static final int COORDS_PER_VERTEX = 3;
    static float[] triangleCoords = {
            0.25f, -1f, 0.0f, // bottom left
            0.5f, -1f, 0.0f,  // bottom right
            0.5f, -0.75f, 0.0f, // top left
    };
    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";
    private final String fragmentShaderCode = "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}";
    private final int program;
    private final FloatBuffer vertexBuffer;
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4;
    float[] color = {1, 1, 1, 1.0f};
    private int vPMatrixHandle;
    private int positionHandle;
    private int colorHandle;

    public Triangle() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(
                triangleCoords.length * 4
        );
        byteBuffer.order(ByteOrder.nativeOrder());

        vertexBuffer = byteBuffer.asFloatBuffer();

        vertexBuffer.put(triangleCoords);

        vertexBuffer.position(0);

        int vertexShader = MyGLSurfaceView.MyGLRenderer.loadShader(GLES30.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLSurfaceView.MyGLRenderer.loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentShaderCode);

        program = GLES30.glCreateProgram();

        GLES30.glAttachShader(program, vertexShader);
        GLES30.glAttachShader(program, fragmentShader);
        GLES30.glLinkProgram(program);
    }

    public void draw(float[] mvpMatrix) {
        GLES30.glUseProgram(program);

        positionHandle = GLES30.glGetAttribLocation(program, "vPosition");

        GLES30.glEnableVertexAttribArray(positionHandle);

        GLES30.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES30.GL_FLOAT, false, vertexStride, vertexBuffer);

        colorHandle = GLES30.glGetUniformLocation(program, "vColor");

        GLES30.glUniform4fv(colorHandle, 1, color, 0);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);

        GLES30.glDisableVertexAttribArray(positionHandle);

        vPMatrixHandle = GLES30.glGetUniformLocation(program, "uMVPMatrix");

        GLES30.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);

        GLES30.glDisableVertexAttribArray(positionHandle);
    }
}
