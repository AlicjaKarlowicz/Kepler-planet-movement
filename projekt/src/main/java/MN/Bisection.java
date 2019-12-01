package MN;

import java.util.ArrayList;

public class Bisection extends Method {
    private double xu; //end of search root interval [xi,xu]

    public Bisection(ScalarFunction f, double xi, double givenEa, double e, double a, double oT, double xu) {
        super(f, xi, givenEa, e, a, oT);
        this.xu = xu;
    }

    public double getXu() {
        return xu;
    }

    public void setXu(double xu) {
        this.xu = xu;
    }

    @Override
    public double solve(double t) {

        double xi = getXi(); //start of interval
        double xu = getXu();
        if(getF().f(xi,t,getE(),getoT()) * getF().f(xu,t,getE(),getoT()) > 0.0) {
            throw new IllegalArgumentException("Function has same signs at ends of interval"); //to start searching function values must have different signs
                                                                                                //because we're trying to find root x=0
        }

        double xr=0; //root

        while(ea(xi,xu) > getGivenEa()) { //find the root with given precision

            xr = (xi + xu)/2.0;


            if(getF().f(xi,t,getE(),getoT()) * getF().f(xr,t,getE(),getoT()) < 0.0) //change end of interval,narrowing interval
                xu = xr;
            else if (getF().f(xi,t,getE(),getoT()) * getF().f(xr,t,getE(),getoT()) > 0.0)//change start of interval
                xi = xr;
            else {//if we find the exact root exit the loop
                break;
            }
        }

        return xr; //return root
    }



}
