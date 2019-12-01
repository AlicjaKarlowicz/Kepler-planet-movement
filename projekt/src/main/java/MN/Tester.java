package MN;

import java.util.ArrayList;
import java.util.Arrays;

public class Tester {

    public static void main(String[] args) {
        double ea = 1.0e-6;
        double e = 0.0167;
        double oT = 365;
        double a=1;


        //just to test if computing coordinates is working

            Bisection bisection = new Bisection(new MyFunction(), 0, ea, e, a, oT, 2* Math.PI);
            RegulaFalsi regulaFalsi = new RegulaFalsi(new MyFunction(), 0, ea, e, a, oT, 2 * Math.PI);
            FixedPointIteration fixedPoint = new FixedPointIteration(new MyFunction(), 0.5, ea, e, a, oT);
            NewtonRaphson newtonRaphson = new NewtonRaphson(new MyFunction(), 0, ea, e, a, oT);
            Secant secant = new Secant(new MyFunction(), Math.PI/2, ea, e, a, oT, 0);

            ArrayList<Method> methods = new ArrayList<>();
            methods.add(bisection);
            methods.add(regulaFalsi); methods.add(fixedPoint); methods.add(newtonRaphson);
            methods.add(secant);

        for (Method m: methods) {
            for(double t=1;t<=oT;t++)
                System.out.println(bisection.solve(t));
//            for(int i=0;i<m.trajectory().size();i++)
//            System.out.println(Arrays.toString(m.trajectory().get(i)));
        }


        }



    }

