package OpenGL;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;


/**
 * An OpenGL ES representation of a
 * Square.
 *
 * Example is taken from Pro OpenFL ES for Android textbook
 *
 *
 * @author Jeton Sinoimeri
 * @version 1.0
 * @since 2014-12-29
 *
 */

public class Square
{

    /**
     * mFVertexBuffer: FloatBuffer instance representing the vertices of
     *                 the square.
     *
     */

    private FloatBuffer mFVertexBuffer;


    /**
     * mColourBuffer: ByteBuffer instance representing the colours of each
     *                vertex in the square.
     *
     * mIndexBuffer: ByteBuffer instance representing the indices of the
     *               two triangles that will make this square.
     *
     */

    private ByteBuffer mColourBuffer,
                       mIndexBuffer;


    /**
     * Constructor for the Square class
     *
     */

    public Square()
    {

        // the vertices of a square at (-1, -1), (1, -1), (-1, 1), (1, 1)
        float[] vertices = {
                             -1.0f, -1.0f,
                              1.0f, -1.0f,
                             -1.0f,  1.0f,
                              1.0f,  1.0f
                           };

        // the maximum colour value as a byte
        byte maxColour = (byte)255;


        // colours are values for red, green, blue, and alpha/transparency
        byte[] colours = {
                            maxColour, maxColour,         0, maxColour,
                                    0, maxColour, maxColour, maxColour,
                                    0,         0,         0, maxColour,
                            maxColour,         0, maxColour, maxColour
                         };


        /*
         * OpenGL ES is limited to drawing only triangles, therefore the indices below
         * indicate the vertices of the two triangles that will make up this square
         *
         */

        byte[] indices = {
                           0, 3, 1,
                           0, 2, 3
                         };


        /*
         * Converting Java representation of the colours, vertices and indices into
         * values OpenGL understands.
         *
         */

        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());

        this.mFVertexBuffer = vertexByteBuffer.asFloatBuffer();
        this.mFVertexBuffer.put(vertices);
        this.mFVertexBuffer.position(0);

        this.mColourBuffer = ByteBuffer.allocateDirect(colours.length);
        this.mColourBuffer.put(colours);
        this.mColourBuffer.position(0);

        this.mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        this.mIndexBuffer.put(indices);
        this.mIndexBuffer.position(0);


    }


    /**
     * Draws the Square onto the screen
     *
     * @param gl10: GL10 instance representing OpenGl ES 1.0
     *
     */

    public void draw(GL10 gl10)
    {
        // Tells OpenGl how the vertices are ordering their faces, CW means Clockwise
        gl10.glFrontFace(GL11.GL_CW);


        // Stride is the number of user info bytes packed between GL data that system can skip over

        // the methods specify the number of elements/vertex, data type, the stride size, the array
        gl10.glVertexPointer(2, GL11.GL_FLOAT, 0, this.mFVertexBuffer);
        gl10.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, 0, this.mColourBuffer);

        // draws the elements to screen, method takes in format of geometry, size, data type, array
        gl10.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, this.mIndexBuffer);

        // reset the ordering of the vertices to default, CCW means CounterClockwise
        gl10.glFrontFace(GL11.GL_CCW);
    }
}
