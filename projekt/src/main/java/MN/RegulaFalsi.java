package MN;

public class RegulaFalsi extends Method {
    private double xu;//end of interval [xi,xu]


    public RegulaFalsi(ScalarFunction f, double xi, double givenEa, double e, double a, double oT, double xu) {
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

        double xi = getXi();//start of interval
        double xu = getXu();
        if(getF().f(xi,t,getE(),getoT()) * getF().f(xu,t,getE(),getoT()) > 0.0) {
            throw new IllegalArgumentException("Function has same signs at ends of interval");//to start searching function values must have different signs
                                                                                            //because we're trying to find root x=0

        }

        double xr=0; //root
        double xrprev=getXi(); //previous value of x: the first one is the value of the beginning of interval which is xi

        do{

            xr = xu - (getF().f(xu,t,getE(),getoT())*(xi-xu))/(getF().f(xi,t,getE(),getoT())-getF().f(xu,t,getE(),getoT()));



            if(getF().f(xi,t,getE(),getoT()) * getF().f(xr,t,getE(),getoT()) < 0.0)  //change end of interval,narrowing interval
                xu = xr;
            else if (getF().f(xi,t,getE(),getoT()) * getF().f(xr,t,getE(),getoT()) > 0.0) //chang the beginning of interval
                xi = xr;
            else//if we find the exact root exit the loop
                break;

            xrprev=xr;//change previous x to current a
            xr = xu - (getF().f(xu,t,getE(),getoT())*(xi-xu))/(getF().f(xi,t,getE(),getoT())-getF().f(xu,t,getE(),getoT()));//count next x

        }  while(ea(xr,xrprev) > getGivenEa());//find the root with given precision

        return xr; //return root
    }


}
