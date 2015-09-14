package com.example.user.solarsystem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

/**
 * Created by user on 8/30/2015.
 */
public class Planet {
    FloatBuffer m_VertexData;
    FloatBuffer m_NormalData;
    FloatBuffer m_ColorData;

    FloatBuffer m_textureData;    // for earths texture;

    float m_Scale;
    float m_Squash;
    float m_Radius;
    int m_Stacks,m_Slices;

    public float [] m_Pos = {0.0f,0.0f,0.0f};



    private int [] textures = new int[1];

    public  FloatBuffer mTextureBuffer;

    int m_BumpMapId;

    float [] textureCoords=

            {
                    0.0f,0.0f,
                    1.0f,0.0f,
                    0.0f,1.0f,
                    1.0f,1.0f
            };

    float texincrease=0.3f;

    public Planet(int stacks,int slices,float radius,float squash,GL10 gl,Context context,boolean imageId,int resourceID,int normResID)
    {
        this.m_Stacks=stacks;
        this.m_Slices=slices;
        this.m_Radius=radius;
        this.m_Squash=squash;

      //  init(m_Stacks,m_Slices,radius,squash,"dummy");

        init_earthTexture(m_Stacks,m_Slices,radius,squash,gl,context,imageId,resourceID,normResID);
    }

    public void setPosition(float a,float b,float c)
    {
        m_Pos[0]=a;
        m_Pos[1]=b;
        m_Pos[2]=c;

    }



    private void init_earthTexture(int stacks,int slices,float radius,float sqaush,
                                   GL10 gl, Context contex,boolean imageID,int resourceID,int textureID)
    {
        float[] VertexData;
        float[] colorData;
        float[] normalData;
        float [] texdata=null;
        float colorIncrement=0f;

        float blue=0f;
        float red=1.0f;
        int numVertices=0;
        int vIndex=0;
        int cIndex=0;
        int nIndex=0;

        int tIndex =0;    // texture index

        if(imageID==true)
        {
            m_BumpMapId= createTexture(gl,contex,textureID);
            textures[0]=createTexture(gl, contex,resourceID );
        }

        m_Scale=radius;
        m_Squash=sqaush;
        colorIncrement=1.0f/(float)stacks;
        {
            m_Stacks=stacks;
            m_Slices=slices;

            VertexData=new float[3*((m_Slices*2+2)*m_Stacks)];

            colorData= new float[(4*(m_Slices*2+2)*m_Slices)];

            normalData=new float[3*((m_Slices*2+2)*m_Stacks)];

            if(imageID==true)
            {
                texdata = new float[2*((m_Slices*2+2)*(m_Stacks))];
            }


            int phiIdx,thetaIdx;
            //latitude
            for(phiIdx=0;phiIdx<m_Stacks;phiIdx++)
            {
                //
                float phi0= (float)Math.PI*((float)(phiIdx+0)*(1.0f/(float)(m_Stacks))-0.5f);

                float phi1 = (float)Math.PI * ((float)(phiIdx+1)*(1.0f/(float)(m_Stacks))-0.5f);

                float cosPhi0 = (float)Math.cos(phi0);
                float sinPhi0 = (float)Math.sin(phi0);
                float cosPhi1=(float)Math.cos(phi1);
                float sinPhi1=(float)Math.sin(phi1);

                float cosTheta,sinTheta;

                //longitude

                for(thetaIdx=0;thetaIdx< m_Slices;thetaIdx++)
                {
                    //increment aong

                    float theta =(float)(-2.0f*(float)Math.PI*((float)thetaIdx)*(1.0/(float)(m_Slices-1)));
                    cosTheta=(float)Math.cos(theta);
                    sinTheta=(float)Math.sin(theta);

                    VertexData[vIndex+0]=m_Scale*cosPhi0*cosTheta;
                    VertexData[vIndex+1]=m_Scale*(sinPhi0*m_Squash);
                    VertexData[vIndex+2]=m_Scale*(cosPhi0*sinTheta);

                    VertexData[vIndex+3]=m_Scale*cosPhi1*cosTheta;
                    VertexData[vIndex+4]=m_Scale*(sinPhi1*m_Squash);

                    VertexData[vIndex+5]=m_Scale*(cosPhi1*sinTheta);

                    colorData[cIndex+0]=(float)red;
                    colorData[cIndex+1]=(float)0f;
                    colorData[cIndex+2]=(float)blue;
                    colorData[cIndex+3]=(float)red;
                    colorData[cIndex+4]=(float)0f;
                    colorData[cIndex+5]=(float)blue;
                    colorData[cIndex+6]=(float)1.0;
                    colorData[cIndex+7]=(float)1.0f;

                    normalData[nIndex + 0] = cosPhi0*cosTheta;
                    normalData[nIndex + 1] = sinPhi0;
                    normalData[nIndex + 2] = cosPhi0*sinTheta;
                    normalData[nIndex + 3] = cosPhi1*cosTheta;
                    normalData[nIndex + 4] = sinPhi1;
                    normalData[nIndex + 5] = cosPhi1*sinTheta;

                    if(texdata != null)
                    {
                        float texX =(float) thetaIdx * (1.0f/(float)(m_Slices-1));
                        texdata[tIndex+0]=texX;
                        texdata[tIndex+1]= (float)(phiIdx+0) * (1.0f/(float)(m_Stacks));
                        texdata[tIndex+2]=texX;
                        texdata[tIndex+3]=(float)(phiIdx+1)*(1.0f/(float)(m_Stacks));
                    }

                    colorData[cIndex+0]=(float)red;
                    colorData[cIndex+1]=(float)0f;
                    colorData[cIndex+2]=(float)blue;
                    colorData[cIndex+4]=(float)red;
                    colorData[cIndex+5]=(float)0f;
                    colorData[cIndex+6]=(float)blue;
                    colorData[cIndex+3]=(float)1.0f;
                    colorData[cIndex+7]=(float)1.0f;



                    cIndex+=2*4;
                    vIndex+=2*3;
                    nIndex+=2*3;

                    if(texdata != null)
                    {
                        tIndex += 2*2;
                    }
                }
                //  blue+=colorIncrement;
                red-=colorIncrement;

                //create a degene

                VertexData[vIndex+0]=VertexData[vIndex+3]=VertexData[vIndex-3];
                VertexData[vIndex+1]=VertexData[vIndex+4]=VertexData[vIndex-2];
                VertexData[vIndex+2]=VertexData[vIndex+5]=VertexData[vIndex-1];

                normalData[nIndex+0] = normalData[nIndex+3]=normalData[nIndex-3];
                normalData[nIndex+1]=normalData[nIndex+4]=normalData[nIndex-2];
                normalData[nIndex+2]=normalData[nIndex+5]=normalData[nIndex-1];

                if (texdata != null)
                {
                    texdata[tIndex+0] = texdata[tIndex+2]=texdata[tIndex-2];

                    texdata[tIndex+1] = texdata[tIndex+3]=texdata[tIndex-1];


                }

            }

        }
        m_VertexData=makeFloaBuffer(VertexData);
        m_ColorData=makeFloaBuffer(colorData);
        m_NormalData=makeFloaBuffer(normalData);

        if(texdata != null)
        {
            m_textureData = makeFloaBuffer(texdata);
        }
    }


    static float lightAngle=30.0f;


    public int createTexture(GL10 gl,Context contextRegf,int resource)
    {
        Bitmap image = BitmapFactory.decodeResource(contextRegf.getResources(), resource);

        int[] textures_ = new int[1];
        gl.glGenTextures(1,textures_,0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures_[0]);

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, image, 0);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        image.recycle();
        return  textures_[0];
    }


    protected static FloatBuffer makeFloaBuffer(float[] arr)
    {
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(arr);
        fb.position(0);
        return fb;
    }

    public void draw(GL10 gl)
    {


        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);
        gl.glEnable(GL10.GL_LIGHTING);

        gl.glFrontFace(GL10.GL_CW);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, m_VertexData);

        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glClientActiveTexture(GL10.GL_TEXTURE0);


        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, m_textureData);



        gl.glClientActiveTexture(GL10.GL_TEXTURE1);
        gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,m_textureData);

        gl.glMatrixMode(GL10.GL_MODELVIEW);

        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glNormalPointer(GL10.GL_FLOAT, 0, m_NormalData);

        gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, m_ColorData);
        multiTextureBumpMap(gl, m_BumpMapId,textures[0]);

        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,(m_Slices+1)*2*(m_Stacks-1)+2);


    }

    public void multiTextureBumpMap(GL10 gl,int mainTexture,int normalTexture)
    {
        float x,y,z;
        lightAngle+= 0.5f;

        if(lightAngle > 180)
            lightAngle=0;

        //set up the light vector.
        x=(float)Math.sin(lightAngle*(3.14159/180.0f));
        y=0.0f;
        z=(float)Math.cos(lightAngle*(3.141559/180.0f));

        //half shifting
        x= x*0.5f + 0.5f;
        y= y*0.5f + 0.5f;
        z= z*0.5f + 0.5f;

     //   gl.glColor4f(x,y,z,1.0f);

        //the color and normal map are combined.

        gl.glActiveTexture(GL10.GL_TEXTURE0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, mainTexture);

        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL11.GL_COMBINE);
        gl.glTexEnvf(GL10.GL_TEXTURE_ENV,GL11.GL_COMBINE_RGB, GL11.GL_DOT3_RGB);
        gl.glTexEnvf(GL10.GL_TEXTURE_ENV,GL11.GL_SRC0_RGB, GL11.GL_TEXTURE);
        gl.glTexEnvf(GL10.GL_TEXTURE_ENV,GL11.GL_SRC1_RGB, GL11.GL_PREVIOUS);

        //set up the second texture and combine it with the result of thr DOT3 combination.

        gl.glActiveTexture(GL10.GL_TEXTURE1);
        gl.glBindTexture(GL10.GL_TEXTURE_2D,normalTexture);

        gl.glTexEnvf(GL10.GL_TEXTURE_ENV,GL10.GL_TEXTURE_ENV_MODE,GL10.GL_MODULATE);
    }
}
