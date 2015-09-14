package com.example.user.solarsystem;

/**
 * Created by user on 9/1/2015.
 */


import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SolarSystemRenderer implements GLSurfaceView.Renderer {

    public SolarSystemRenderer(Context context)
    {

        mContext =context;

    }

    static float angle = 0.0f;

    public void onDrawFrame(GL10 gl)
    {

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(0.0f, 0.25f, 0.35f, 1.0f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glTranslatef(0.0f, (float) Math.sin(mTransY), -4.0f);

        gl.glRotatef(mAngle, 1, 0, 0);
        gl.glRotatef(mAngle, 0, 1, 0);

        execute(gl);

    }

    private  void executePlanet(Planet _planet,GL10 gl)
    {
        float posX,posY,posZ;
        posX = _planet.m_Pos[0];
        posY = _planet.m_Pos[1];
        posZ = _planet.m_Pos[2];

        gl.glPushMatrix();
        gl.glTranslatef(posX, posX, posZ);

        _planet.draw(gl);
        gl.glPopMatrix();

    }
    public void onSurfaceChanged(GL10 gl,int width,int height)
    {
        Log.e("namit","onSurfaceChnaged width ="+width+"  height= "+height);
        gl.glViewport(0, 0, width, height);
        float ratio = (float) width/height;
        float aspectRatio ;
        float zNear=0.2f;
        float zfar=1000;
        float fieldOfView = 30.0f/57.3f;
        float size;
        gl.glEnable(GL10.GL_NORMALIZE);
        aspectRatio = (float) width /(float)height;

        gl.glMatrixMode(GL10.GL_PROJECTION);
        size= zNear * (float)(Math.tan((double)(fieldOfView/2.0f)));

        gl.glFrustumf(-size, size, -size / aspectRatio, size / aspectRatio, zNear, zfar);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
    }

    private void initLighting(GL10 gl) {

        /*
        float[] sunPos = { 0.0f, 0.0f, 0.0f, 1.0f };
        float[] posFill1 = { -15.0f, 15.0f, 0.0f, 1.0f };
        float[] posFill2 = { -10.0f, -4.0f, 1.0f, 1.0f };

        float[] white = { 1.0f, 1.0f, 1.0f, 1.0f };
        float[] dimblue = { 0.0f, 0.0f, .2f, 1.0f };

        float[] cyan = { 0.0f, 1.0f, 1.0f, 1.0f };
        float[] yellow = { 1.0f, 1.0f, 0.0f, 1.0f };
        float[] magenta = { 1.0f, 0.0f, 1.0f, 1.0f };
        float[] dimmagenta = { .75f, 0.0f, .25f, 1.0f };

        float[] dimcyan = { 0.0f, .5f, .5f, 1.0f };

        // lights go here

        gl.glLightfv(SS_SUNLIGHT, GL10.GL_POSITION, makeFloaBuffer(sunPos));
        gl.glLightfv(SS_SUNLIGHT, GL10.GL_DIFFUSE, makeFloaBuffer(white));
        //gl.glLightfv(SS_SUNLIGHT, GL10.GL_SPECULAR, makeFloaBuffer(yellow));

        gl.glLightfv(SS_FILLLIGHT1, GL10.GL_POSITION, makeFloaBuffer(posFill1));
        gl.glLightfv(SS_FILLLIGHT1, GL10.GL_DIFFUSE, makeFloaBuffer(dimblue));
       // gl.glLightfv(SS_FILLLIGHT1, GL10.GL_SPECULAR, makeFloaBuffer(dimcyan));

        gl.glLightfv(SS_FILLLIGHT2, GL10.GL_POSITION, makeFloaBuffer(posFill2));
       // gl.glLightfv(SS_FILLLIGHT2, GL10.GL_SPECULAR, makeFloaBuffer(dimmagenta));
        gl.glLightfv(SS_FILLLIGHT2, GL10.GL_DIFFUSE, makeFloaBuffer(dimblue));

        // materials go here

        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,
                makeFloaBuffer(cyan));
//		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, makeFloatBuffer(white));

//		gl.glLightf(SS_SUNLIGHT, GL10.GL_QUADRATIC_ATTENUATION, .001f);

        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 25);

        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glLightModelf(GL10.GL_LIGHT_MODEL_TWO_SIDE, 0.0f);

        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(SS_SUNLIGHT);
        gl.glEnable(SS_FILLLIGHT1);
        gl.glEnable(SS_FILLLIGHT2);
        gl.glLoadIdentity();


        */



        float[] sunPos = { 0.0f, 0.0f, 0.0f, 1.0f };
        float[] posFill1 = { -15.0f, 15.0f, 0.0f, 1.0f };
        float[] posFill2 = { -10.0f, -4.0f, 1.0f, 1.0f };

        float[] white = { 1.0f, 1.0f, 1.0f, 1.0f };
        float[] dimblue = { 0.0f, 0.0f, .2f, 1.0f };

        float[] cyan = { 0.0f, 1.0f, 1.0f, 1.0f };
        float[] yellow = { 1.0f, 1.0f, 0.0f, 1.0f };
        float[] magenta = { 1.0f, 0.0f, 1.0f, 1.0f };
        float[] dimmagenta = { .75f, 0.0f, .25f, 1.0f };

        float[] dimcyan = { 0.0f, .5f, .5f, 1.0f };

        // lights go here

        gl.glLightfv(SS_SUNLIGHT, GL10.GL_POSITION, makeFloaBuffer(sunPos));
        gl.glLightfv(SS_SUNLIGHT, GL10.GL_DIFFUSE, makeFloaBuffer(white));
        gl.glLightfv(SS_SUNLIGHT, GL10.GL_SPECULAR, makeFloaBuffer(yellow));

        gl.glLightfv(SS_FILLLIGHT1, GL10.GL_POSITION, makeFloaBuffer(posFill1));
        gl.glLightfv(SS_FILLLIGHT1, GL10.GL_DIFFUSE, makeFloaBuffer(dimblue));
        gl.glLightfv(SS_FILLLIGHT1, GL10.GL_SPECULAR, makeFloaBuffer(dimcyan));

        gl.glLightfv(SS_FILLLIGHT2, GL10.GL_POSITION, makeFloaBuffer(posFill2));
        gl.glLightfv(SS_FILLLIGHT2, GL10.GL_SPECULAR, makeFloaBuffer(dimmagenta));
        gl.glLightfv(SS_FILLLIGHT2, GL10.GL_DIFFUSE, makeFloaBuffer(dimblue));

        // materials go here

        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,
                makeFloaBuffer(cyan));
//		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, makeFloatBuffer(white));

//		gl.glLightf(SS_SUNLIGHT, GL10.GL_QUADRATIC_ATTENUATION, .001f);

        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 25);

        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glLightModelf(GL10.GL_LIGHT_MODEL_TWO_SIDE, 0.0f);

        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(SS_SUNLIGHT);
        gl.glEnable(SS_FILLLIGHT1);
        gl.glEnable(SS_FILLLIGHT2);
        gl.glLoadIdentity();
    }


    public void onSurfaceCreated(GL10 gl,EGLConfig config)
    {




        //gl.glDepthMask(false);

        gl.glDepthMask(true);


        initGeometry(gl);
        initLighting(gl);

        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);




    }

    private void execute(GL10 gl)
    {

        float posFill1[]={-8.0f, 0.0f, 7.0f, 1.0f};
        float cyan[]={0.0f, 1.0f, 1.0f, 1.0f};
        float orbitalIncrement=0.5f;
        float sunPos[]={0.0f, 0.0f, 0.0f, 1.0f};

        gl.glLightfv(SS_FILLLIGHT1, GL10.GL_POSITION, makeFloaBuffer(posFill1));

        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glClearColor(0.0f, 0.25f, 0.35f, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glPushMatrix();

        gl.glTranslatef(-m_EyePosition[X_VALUE], -m_EyePosition[Y_VALUE], -m_EyePosition[Z_VALUE]);
        gl.glLightfv(SS_SUNLIGHT, GL10.GL_POSITION, makeFloaBuffer(sunPos));

        gl.glEnable(SS_FILLLIGHT1);
        gl.glEnable(SS_FILLLIGHT2);

        gl.glPushMatrix();

        angle+=orbitalIncrement;
        gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);
        executePlanet(m_Earth, gl);
        gl.glPopMatrix();
        gl.glPopMatrix();
    }

    private void initGeometry(GL10 gl)
    {

        m_EyePosition[X_VALUE]=0.0f;
        m_EyePosition[Y_VALUE]=0.0f;
        m_EyePosition[Z_VALUE]=5.0f;

        int normresID = R.drawable.earth_normal_hc;
        int resID=R.drawable.earth_light;
        m_Earth = new Planet(50,50,1.0f,1.0f,gl,mContext,true,resID,normresID);
        m_Earth.setPosition(0.0f,0.0f,0.0f);

    }

    protected static FloatBuffer makeFloaBuffer(float[] arr)
    {
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(arr);
        fb.position(0);
        return fb;
    }
    public final static int SS_SUNLIGHT =  GL10.GL_LIGHT0;
    // 5-September-2015
    public final static int SS_FILLLIGHT1 =  GL10.GL_LIGHT1;
    public final static int SS_FILLLIGHT2 =  GL10.GL_LIGHT2;
    private boolean mTranslucentBackgroung;
    private Planet mPlanet;
    private float mTransY;
    private float mAngle;

    public final static int X_VALUE =0;
    public final  static  int Y_VALUE = 1;
    public final  static  int Z_VALUE = 2;

    Planet m_Earth;
    Planet m_Sun;

    float [] m_EyePosition = {0.0f,0.0f,0.0f};

    public Context mContext;
}
