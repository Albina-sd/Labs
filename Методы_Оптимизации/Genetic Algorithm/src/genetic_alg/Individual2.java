package genetic_alg;

import org.apache.commons.math3.util.Precision;

class Individual2 implements Comparable<Individual2>   //особь
{
    double x1;
    double x2;
    private String bin_x1;
    private String bin_x2;


    String ToBin(double n) { return Long.toBinaryString(Double.doubleToLongBits(n)); }

    @Override
    public String toString() {
        String str1 = "", str2 = "";
        str1 += x1;
        str2 += x2;
        str1 = str1.substring(0,9);
        str2 = str2.substring(0,9);
        return "x = " + str1 +
                ", y = " + str2 +
                //", bin_x='" + bin_x1 + '\'' +
                //", bin_y='" + bin_x2 + '\'' +
                ", fitness_function = " + Precision.round(fitness_function,7);
    }

    double fitness_function; //целевая функция

    public Individual2(double x1,double x2) {
        this.x1=x1;
        this.x2=x2;
        this.bin_x1=ToBin(this.x1);
        this.bin_x2=ToBin(this.x2);
        this.fitness_function = Math.sin(this.x1) * Math.exp(-(this.x2*this.x2)) / (1 + this.x1*this.x1 + this.x2*this.x2);
    }

    public int compareTo(Individual2 o) {
        return  (int)((o.fitness_function - fitness_function)*10000);
    }

}
