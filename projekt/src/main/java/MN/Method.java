package MN;

import java.util.ArrayList;

public abstract class Method {
    private ScalarFunction f;//function
    private double xi;//start value for searching for interval
    private double givenEa;//given precision with root should be found
    private double e; //eccentrity
    private double a; //distance, 1 for Earth
    private double oT; //orbital time, 365 for Earth

    public Method(ScalarFunction f, double xi, double givenEa, double e, double a, double oT) {
        this.f = f;
        this.xi = xi;
        this.givenEa = givenEa;
        this.e = e;
        this.a = a;
        this.oT = oT;
    }

    public ScalarFunction getF() {
        return f;
    }

    public void setF(ScalarFunction f) {
        this.f = f;
    }

    public double getXi() {
        return xi;
    }

    public void setXi(double xi) {
        this.xi = xi;
    }

    public double getGivenEa() {
        return givenEa;
    }

    public void setGivenEa(double givenEa) {
        this.givenEa = givenEa;
    }

    public double getE() {
        return e;
    }

    public void setE(double e) {
        this.e = e;
    }


    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getoT() {
        return oT;
    }

    public void setoT(double oT) {
        this.oT = oT;
    }

    public double ea(double present, double previous) {
        return Math.abs((present-previous)/present) *100;
    }//calculating precision

    public abstract double solve(double t); //method for finding the root


    public double[] position(double t) { //method for calculating trajectory which is the set of x,y
        double x = a*Math.cos(solve(t)-e); //detrminating location,x coordinate
        double y = a*Math.sqrt(1-Math.pow(e,2))*Math.sin(solve(t)); //detrminating location,x coordinate

        double[] coordinates= {x,y};

        return coordinates;
    }

    public ArrayList<double[]> trajectory() {
        ArrayList<double[]> trajectory = new ArrayList<>();

        for(double t=1;t<=oT;t++){
            trajectory.add(position(t));
        }

        return trajectory;
    }

}
