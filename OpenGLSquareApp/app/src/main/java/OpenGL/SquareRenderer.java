package OpenGL;

import android.opengl.GLSurfaceView;

import java.lang.Math;import java.lang.Override;import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * SquareRenderer renders the Square to the screen.
 *
 * @see android.opengl.GLSurfaceView.Renderer
 *
 *
 * Example is taken from Pro OpenFL ES for Android textbook
 *
 *
 * @author Jeton Sinoimeri
 * @version 1.0
 * @since 2014-12-29
 *
 */

public class SquareRenderer implements GLSurfaceView.Renderer
{

    /**
     * mTranslucentBackground: boolean value indicating the use of the
     *                         translucent background.
     *
     */

    private boolean mTranslucentBackground;


    /**
     * mSquare: Square instance representing the Square to be drawn
     *          to screen.
     *
     */

    private Square mSquare;


    /**
     * mTransY: float value representing the y-coordinate of the
     *          square's translation.
     *
     */

    private float mTransY;



    /**
     * Constructor for the SquareRenderer class.
     *
     * @param useTranslucentBackground: boolean value indicating whether or not
     *                                  to use the translucent background.
     *
     */

    public SquareRenderer(boolean useTranslucentBackground)
    {
        this.mTranslucentBackground = useTranslucentBackground;
        this.mSquare = new Square();
    }


    /**
     * Initializes the surface creation line.
     *
     * @see android.opengl.GLSurfaceView.Renderer
     *
     *
     * @param gl: GL10 instance representing OpenGL ES 1.0
     * @param config: EGLConfig instance resenting the configuration
     *                of OpenGL ES.
     *
     */

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        /*
         * Dithering in OpenGL makes screens with limited colour palettes look somewhat nicer
         * but it has performance issues.
         *
         */

        // ensures that an dithering is turned off
        gl.glDisable(GL10.GL_DITHER);

        // tells OpenGL ES to choose between speed or quality
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);


        // depending on mTranslucentBackground value, background is set to black or white
        if (this.mTranslucentBackground)
            gl.glClearColor(0, 0, 0, 0);

        else
            gl.glClearColor(1, 1, 1, 1);


        // tells the triangles to be aimed away from the user
        gl.glEnable(GL10.GL_CULL_FACE);

        // tells the triangles to use smooth shading so colours blend across the surface
        gl.glShadeModel(GL10.GL_SMOOTH);

        // enables depth testing also known as z-buffering
        gl.glEnable(GL10.GL_DEPTH_TEST);

    }


    /**
     * Responsible for initial setup by finding the screen's width and height.
     * It is also called whenever the screen changes size.
     *
     * @see android.opengl.GLSurfaceView.Renderer
     *
     *
     * @param gl: GL10 instance representing OpenGL ES 1.0
     * @param width: integer value representing the width of the screen.
     * @param height: integer value representing the height of the screen.
     *
     */

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        // specifies the actual dimensions and placement of the OpenGL window
        gl.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // sets the mode of the matrix to GL_PROJECTION which is responsible for projecting 3D to 2D
        gl.glMatrixMode(GL10.GL_PROJECTION);

        // resets the matrix to its initial values to erase any previous settings
        gl.glLoadIdentity();

        // sets the actual frustum using the ratio and the planes (left/right, top/bottom, near/far)
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);

    }


    /**
     * The root refresh method, it constructs the image each time,
     * many times a second.
     *
     * @see android.opengl.GLSurfaceView.Renderer
     *
     *
     * @param gl: GL10 instance representing OpenGL ES 1.0
     *
     */

    @Override
    public void onDrawFrame(GL10 gl)
    {
        // clears the screen each time onDrawFrame() is called.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // sets the values to ensure that the geometry is immediately visible
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        // translates the box up and down
        gl.glTranslatef(0.0f, (float)Math.sin(mTransY), -3.0f);

        // tells OpenGL to expect both vertex and colour data
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        // calls the draw method of Square to draw to screen
        this.mSquare.draw(gl);

        this.mTransY += 0.075f;

    }
}
