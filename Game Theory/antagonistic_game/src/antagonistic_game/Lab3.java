package antagonistic_game;


public class Lab3 {

    public static void main(String[] args) {
        double a = -5;
        double b = 5.0/6.0;
        double c = 10.0/3.0;
        double d = -2.0/3.0;
        double e = -2;

        System.out.println("Аналитический метод:");
        Analytical an = new Analytical(a,b,c,d,e);
        double H = an.saddle_point();
        System.out.format("Седловая точка: %5.3f\n",H);

        System.out.println("\nЧисленный метод:");
        NumericalSolution ns = new NumericalSolution(a,b,c,d,e);
    }
}
