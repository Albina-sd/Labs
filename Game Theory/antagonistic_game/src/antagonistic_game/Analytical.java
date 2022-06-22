package antagonistic_game;

public class Analytical {
    private double x;
    private double y;
    private double k;

    public Analytical (double a, double b, double c, double d, double e){
        check_func(a,b); //Проверка на выпуклость/вогнутость
        find_XY(a,b,c,d,e);
        System.out.println("\nПроверка: подставим значение х в у");
        System.out.format("y = %4.2f\n",checkY(b,c,e));
        kernel(a,b,c,e,d);
    }

    private void kernel(double a, double b, double c, double e, double d){
        k = (a*Math.pow(this.x,2)) + (b*Math.pow(this.y,2)) + (c*this.x*this.y) + (d*this.x) + (e*this.y);
    }

    public double saddle_point(){ return (this.k); }

    private void find_XY(double a, double b, double c, double d, double e){
        System.out.println("\nНайдем производные:");
        System.out.format("Hxx = 2ax+cy+d = %4.2fx + %4.2fy + %4.2f",2*a,c,d);
        System.out.format("\nHxy = 2by+cx+e = %4.2fy + %4.2fx + %4.2f",2*b,c,e);
        System.out.format("\n\nx = -(%4.2fy + %4.2f)/%4.2f",c,d,2*a);
        System.out.format("\ny = -(%4.2fx + %4.2f)/%4.2f",c,e,2*b);

        y = (c*d-2*a*e)*4*a*b/(4*a*b*(4*a*b - c*c));
        x = -(c*y + d)/(2*a);
        System.out.format("\nx = %4.2f y = %4.2f",x,y);
    }

    private double checkY(double b, double c, double e){return (-(c*this.x + e)/(2*b));}

    private void check_func(double a, double b){
        System.out.println("Проверка на выпуклость/вогнутость:");
        System.out.format("2*a = %4.2f 2*b = %4.2f\n",2*a,2*b);
        if ((2*a) < 0)
            System.out.print("выпукло-");
        else System.out.print("вогнуто-");

        if ((2*b) < 0)
            System.out.print("выпуклая игра \n");
        else System.out.print("вогнутая игра \n");
    }
}
