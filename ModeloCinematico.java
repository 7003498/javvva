/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java3dejemplo2;

import java.util.TimerTask;

/**
 *
 * @author Computador
 */
public class ModeloCinematico extends TimerTask {
    
private double r=23;
private double l=10;
private float wr;
private float wl;
    private double omega;
    private double velocidad = 0.0;
    private double deltaT = 0.1;
    private double theta0 = 0.0;
    private double theta;
    private float xf;
    private float xi = 0f;
    private float yf;
    private float yi = 0f;
    private CuboCanvas3D cubo;

    public ModeloCinematico(CuboCanvas3D cubo) {
        this.cubo = cubo;
    }

    @Override
    public void run() {

        xf=(float) ((r/2)*Math.cos(theta)*wr+(r/2)*Math.cos(theta)*wl);
        yf=(float) ((r/2)*Math.sin(theta)*wr+(r/2)*Math.sin(theta)*wl);
        theta=(r/l)*wr+(r/l)*wl;
        
        
        
        //xf = (float) (velocidad * Math.cos(theta) * deltaT) + xi;
        //yf = (float) (velocidad * Math.sin(theta) * deltaT) + yi;
        //theta = omega * deltaT + theta0;

        cubo.setRotarY(theta - theta0);
        cubo.setTransladarX(xf - xi);
        cubo.setTransladarZ(yf - yi);

        theta0 = theta;
        xi = xf;
        yi = yf;

        //cubo.setRotarY(theta);
        // cubo.setTransladarX(xf);
        //cubo.setTransladarZ(yf);
        //System.out.println("Estados" + "Theta " + theta + " X " + xf + " Y " + yf);

    }

    public void setWr(float wr) {
        this.wr = wr;
    }

    public void setWl(float wl) {
        this.wl = wl;
    }
    

    public double getOmega() {
        return omega;
    }

    public void setOmega(double omega) {
        this.omega = omega;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

}
