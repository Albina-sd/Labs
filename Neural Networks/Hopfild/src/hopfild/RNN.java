package hopfild;

public class RNN {
    int weights [][];
    int y [];
    int n_img [][];

    public RNN (int [][] image, int [][] test){
        this.weights = new int[image[0].length][image[0].length];
        this.n_img = new int[image.length][image[0].length];

        // инициализация
        initialization(image);
        print(this.weights);

        // предсказание
        for (int i = 0; i < image.length; i++) {
            this.y = new int[test[0].length];
            asynchronous_algorithm(test[i]);
            this.n_img[i] = this.y;
        }
    }

    public int [][] getY () {
        return this.n_img;
    }

    private void asynchronous_algorithm (int [] image){
        double net;
        int out = 0;
        int y0[] = image, era = 0;

        do {
            //System.out.print(era + " ");

            for (int j = 0; j < image.length; j++) {

                net = net_sync(j, y0);

                if (j != 0) this.y[j] = f_activation(net, y0[j-1]);
                else this.y[j] = f_activation(net, y0[y0.length-1]);

                out = this.y[j];
                //System.out.print(this.y[j] + " "+ net +" ");
                //net += net_async(i,j, 2);
                //this.y[j][i] = f_activation(net);
            }

            y0 = y;
            era++;
        }while (era < 5); //while (y0.equals(this.y));

        //System.out.println(era);
    }

    private void initialization (int x [][]){
        for (int k = 0; k < x[0].length; k++){
            for (int j = 0; j < x[0].length; j++){

                if (k == j) this.weights[k][j] = 0;
                else {
                    for (int i = 0; i < x.length; i++)
                        this.weights[k][j] += x[i][j] * x[i][k];
                }
            }
        }
    }

    /*private double net_async (int k, int j, int mode){
        double net = 0;

        if (mode == 1) {
            for (int i = ++k; i < this.y.length; i++)
                net += this.weights[i][j] * this.y[j];
        }

        if (mode == 2){
            if (k >= 1) {
                for (int i = 0; i < --k; i++)
                    net += this.weights[i][j] * this.y[j];
            }
        }
        return net;
    }*/

    private double net_sync (int k, int [] y0){
        double net = 0;

        for (int j = 0; j < this.y.length; j++)
            net += this.weights[k][j] * y0[j];

        return net;
    }

    private int f_activation (double net, int f0){
        if (net > 0)
            return 1;
        if (net < 0)
            return -1;
        return f0;
    }

    private void print (int mass[][]){
        for (int i = 0; i < mass.length; i++){
            for (int j = 0; j < mass[1].length; j++){
                System.out.format("%3d",mass[i][j]);
            }
            System.out.println();
        }
    }
}
