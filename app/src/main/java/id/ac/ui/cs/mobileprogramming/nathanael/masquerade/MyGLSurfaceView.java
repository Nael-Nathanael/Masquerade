package id.ac.ui.cs.mobileprogramming.nathanael.masquerade;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLSurfaceView extends GLSurfaceView {

    private final GLSurfaceView.Renderer renderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(3);

        renderer = new MyGLRenderer();

        setRenderer(renderer);
    }

    public static class MyGLRenderer implements GLSurfaceView.Renderer {

        private final float[] vPMatrix = new float[16];
        private final float[] projectionMatrix = new float[16];
        private final float[] viewMatrix = new float[16];
        private long start = SystemClock.uptimeMillis();
        private float posx = 0f;
        private Triangle triangle;
        private float posy = 0f;
        private String moving = "top";

        public static int loadShader(int type, String shaderCode) {
            int shader = GLES30.glCreateShader(type);

            GLES30.glShaderSource(shader, shaderCode);
            GLES30.glCompileShader(shader);

            return shader;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES30.glClearColor(0f, 0f, 0f, 1f);

            triangle = new Triangle();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);

            float ratio = (float) width / height;

            Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        }

        @Override
        public void onDrawFrame(GL10 gl) {

            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

            Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1f, 0f);

            // Create a translation transformation for the triangle
            long time = SystemClock.uptimeMillis() - start;
            switch (moving) {
                case "top": {
                    posy = posy + 0.0001f * ((int) time);
                    if (posy >= 1.75f) {
                        posy = 1.75f;
                        moving = "right";
                        start = SystemClock.uptimeMillis() + 17;
                    }
                    break;
                }
                case "right": {
                    posx = posx - 0.0001f * ((int) time);
                    if (posx <= -0.75f) {
                        posx = -0.75f;
                        moving = "down";
                        start = SystemClock.uptimeMillis() + 17;
                    }
                    break;
                }
                case "down": {
                    posy = posy - 0.0001f * ((int) time);
                    if (posy <= 0f) {
                        posy = 0f;
                        moving = "left";
                        start = SystemClock.uptimeMillis() + 17;
                    }
                    break;
                }
                case "left": {
                    posx = posx + 0.0001f * ((int) time);
                    if (posx >= 0f) {
                        posx = 0f;
                        moving = "top";
                        start = SystemClock.uptimeMillis() + 17;
                    }
                    break;
                }
            }
            Matrix.translateM(viewMatrix, 0, posx, posy, 0);

            Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

            triangle.draw(vPMatrix);
        }
    }
}
