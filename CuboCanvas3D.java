/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java3dejemplo2;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.awt.GraphicsConfiguration;
import java.io.FileNotFoundException;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedQuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import org.jdesktop.j3d.loaders.vrml97.VrmlLoader;

/**
 *
 * @author Computador
 */
public class CuboCanvas3D extends JPanel {

    private static final int BOUNDSIZE = 100;
    private double rotarX;
    private double rotarY;
    private double rotarZ;
    private float transladarX;
    private float transladarY;
    private float transladarZ;
    private TransformGroup tgCaja;
    private SimpleUniverse simpleU;
    BoundingSphere bounds;
    
    public CuboCanvas3D(JPanel jpanel) {
        
        bounds = new BoundingSphere(new Point3d(0, 0, 0), BOUNDSIZE);
        GraphicsConfiguration config
                = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(config);
        jpanel.add("Center", canvas3D);
        BranchGroup scene = createSceneGraph();
        simpleU = new SimpleUniverse(canvas3D);
        orbitControls(canvas3D);
        simpleU.getViewingPlatform().setNominalViewingTransform();
        simpleU.addBranchGraph(scene);

    }

    private BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup(); // Raíz de la rama de contenido
// Rotaciones

        tgCaja = new TransformGroup(); // Crear objeto de transformación
        tgCaja.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgCaja.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

        tgCaja.addChild(loadGeometryWRL("minisumopiece.wrl"));





        objRoot.addChild(tgCaja);
        objRoot.addChild(piso());
        objRoot.addChild(fondo());
        objRoot.compile(); // Compilar la rama de contenido

        return objRoot;
    }
    public BranchGroup loadGeometryWRL(String geometryURL) {
        BranchGroup objLoad = new BranchGroup();

        VrmlLoader wrl = new VrmlLoader();
        try {
            objLoad = wrl.load(geometryURL).getSceneGroup();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (ParsingErrorException ex) {
            ex.printStackTrace();
        } catch (IncorrectFormatException ex) {
            ex.printStackTrace();
        }
        return objLoad;
    }
    private TransformGroup piso() {
        TransformGroup sueloTransf = new TransformGroup();

        int tamano = 100;
        Point3f[] vertices = new Point3f[tamano * tamano];

        float inicio = -20.0f;
        float x = inicio;
        float z = inicio;

        float salto = 1.0f;

        int[] indices = new int[(tamano - 1) * (tamano - 1) * 4];
        int n = 0;

        Color3f blanco = new Color3f(0.0f, 0.1f, 0.4f);
        Color3f negro = new Color3f(0.0f, 0.5f, 0.1f);
        Color3f[] colors = {blanco, negro};

        int[] colorindices = new int[indices.length];

        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                vertices[i * tamano + j] = new Point3f(x, -0.1f, z);
                z += salto;
                if (i < (tamano - 1) && j < (tamano - 1)) {
                    int cindex = (i % 2 + j) % 2;
                    colorindices[n] = cindex;
                    indices[n++] = i * tamano + j;
                    colorindices[n] = cindex;
                    indices[n++] = i * tamano
                            + (j + 1);
                    colorindices[n] = cindex;
                    indices[n++] = (i + 1)
                            * tamano + (j + 1);
                    colorindices[n] = cindex;
                    indices[n++] = (i + 1)
                            * tamano + j;
                }
            }
            z = inicio;
            x += salto;
        }

        IndexedQuadArray geom = new IndexedQuadArray(vertices.length,
                GeometryArray.COORDINATES
                | GeometryArray.COLOR_3,
                indices.length);
        geom.setCoordinates(0, vertices);
        geom.setCoordinateIndices(0, indices);
        geom.setColors(0, colors);
        geom.setColorIndices(0, colorindices);

        Shape3D suelo = new Shape3D(geom);
        sueloTransf.addChild(suelo);

        return sueloTransf;
    }

    private TransformGroup fondo() {
        TransformGroup objRoot = new TransformGroup();
        Background font = new Background(new Color3f(0.17f, 0.65f, 0.92f));
        font.setApplicationBounds(new BoundingSphere(new Point3d(), 100.0));
        objRoot.addChild(font);
        return objRoot;
    }

    private void orbitControls(Canvas3D c) /* OrbitBehaviour allows the user to rotate around the scene, and to
     zoom in and out.  */ {
        OrbitBehavior orbit
                = new OrbitBehavior(c, OrbitBehavior.REVERSE_ALL);
        orbit.setSchedulingBounds(bounds);

        ViewingPlatform vp = simpleU.getViewingPlatform();
        vp.setViewPlatformBehavior(orbit);
    }  // end of orbitControls()

    public void setRotarX(double rotarX) {
        this.rotarX = rotarX;
        Transform3D rotacionX = new Transform3D();
        rotacionX.rotX(rotarX);
        tgCaja.setTransform(rotacionX);
    }

    public void setRotarY(double rotarY) {
        this.rotarY = rotarY;

        Transform3D actual = new Transform3D();
        tgCaja.getTransform(actual);
        Transform3D incremento = new Transform3D();
        incremento.rotY(-rotarY);
        actual.mul(incremento);
        tgCaja.setTransform(actual);
        
//        Transform3D rotacionY = new Transform3D();
//        rotacionY.rotY(rotarY);
//        tgCaja.setTransform(rotacionY);
    }

    public void setRotarZ(double rotarZ) {
        this.rotarZ = rotarZ;
        Transform3D rotacionZ = new Transform3D();
        rotacionZ.rotZ(rotarZ);
        tgCaja.setTransform(rotacionZ);
    }

    public void setTransladarX(float transladarX) {
         this.transladarX = transladarX;
        Transform3D actual = new Transform3D();
        tgCaja.getTransform(actual);
        Vector3f vectoractual = new Vector3f();
        actual.get(vectoractual);
        Vector3f vector = new Vector3f(this.transladarX, 0f, 0f);
        vector.add(vectoractual);
        actual.setTranslation(vector);
        tgCaja.setTransform(actual);

    }

    public void setTransladarY(float transladarY) {
        this.transladarY = transladarY;
        Transform3D actual = new Transform3D();
        tgCaja.getTransform(actual);
        Vector3f vectoractual = new Vector3f();
        actual.get(vectoractual);
        Vector3f vector = new Vector3f(0.0f, transladarY, 0f);
        vector.add(vectoractual);
        actual.setTranslation(vector);
        tgCaja.setTransform(actual);

    }

    public void setTransladarZ(float transladarZ) {
        this.transladarZ = transladarZ;
         Transform3D actual = new Transform3D();
        tgCaja.getTransform(actual);
        Vector3f vectoractual = new Vector3f();
        actual.get(vectoractual);
        Vector3f vector = new Vector3f(0f, 0f, this.transladarZ );
        vector.add(vectoractual);
        actual.setTranslation(vector);
        tgCaja.setTransform(actual);

    }

    public double getRotarX() {
        return rotarX;
    }

    public double getRotarY() {
        return rotarY;
    }

    public double getRotarZ() {
        return rotarZ;
    }

    public float getTransladarX() {
        return transladarX;
    }

    public float getTransladarY() {
        return transladarY;
    }

    public float getTransladarZ() {
        return transladarZ;
    }

}
