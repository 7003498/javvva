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
    
private final double r=0.023;
private final double l=0.1;
private float wr;
private float wl;
    private double omega;
    private double velocidad = 0.0;
    private double deltaT = 0.1;
    private double theta0 = 0.0;
    private double theta = 0.0;
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

        xf=(float) (((r/2)*Math.cos(theta)*wr)+xi);
        yf=(float) (((r/2)*Math.sin(theta)*wl)+yi);
        theta=(r/l)*wr-(r/l)*wl;
        //theta0=90;
        
        
        //xf = (float) (velocidad * Math.cos(theta) * deltaT) + xi;
        //yf = (float) (velocidad * Math.sin(theta) * deltaT) + yi;
        //theta = omega * deltaT + theta0;

        cubo.setRotarY(theta0);
        cubo.setTransladarX(xf - xi);
        cubo.setTransladarZ(yf - yi);

        //theta0 = theta;
        xi = xf;
        yi = yf;

        //cubo.setRotarY(theta);
        // cubo.setTransladarX(xf);
        //cubo.setTransladarZ(yf);
        //System.out.println("Estados" + "Theta " + theta + " X " + xf + " Y " + yf);

    }

    public double getTheta0() {
        return theta0;
    }

    public void setTheta0(double theta0) {
        this.theta0 = theta0;
    }

    public float getXf() {
        return xf;
    }

    public void setXf(float xf) {
        this.xf = xf;
    }

    public float getYf() {
        return yf;
    }

    public void setYf(float yf) {
        this.yf = yf;
    }
    
    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
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