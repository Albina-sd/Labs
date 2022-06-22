package optimization;

public class Lab5 {

    public static void main(String[] args) {
        System.out.println("\nПрограмма решения задачи многокритериальной оптимизации.\n");

        double [] vect = {7,4,9,5}; //задаем вектор весов критериев
        System.out.println("Составляем вектор весов критериев, используя шкалу 1÷10:");
        for (int i = 0; i < vect.length; i++) System.out.print((int) vect[i]+"  ");

        System.out.println("\n\nНормализованный вектор:");
        Optimize Opt = new Optimize();
        double [] norm_vect = Opt.normalize_vector(vect);
        for (int i = 0; i < norm_vect.length; i++) System.out.print(norm_vect[i]+" ");

        System.out.println("\n\nСоставим матрицу оценок для альтернатив:");
        double A [][] = {{7,7,2,3},{5,4,4,7},{2,3,7,5},{4,2,8,8}}; //задаем матрицу критериев
        double B [][] = {{7,7,2,3},{5,4,4,7},{2,3,7,5},{4,2,8,8}};

        System.out.println("  1  2  3  4");
        for (int i = 0; i < vect.length; i++){
            System.out.print(Opt.criteria(i)+" ");
            for (int j = 0; j < 4; j++){
                System.out.print((int) A [i][j] +"  ");
            }
            System.out.println();
        }

        System.out.println("\n\nМетод замены критериев ограничениями\n");
        System.out.println("Выберем в качестве главного критерия цену за 1 м3 (критерий 1)\n");
        int krit = 0; //задаем главный критерий

        //установим минимально допустимые уровни для остальных критериев
        double lim1  = 0.03, lim2 = 0.04, lim3 = 0.05;

        //вызываем Метод замены критериев ограничениями
        Opt.limit_method(A,krit,lim1,lim2,lim3);

        System.out.println("\n\nФормирование и сужение множества Парето\n");
        System.out.println("Выберем в качестве главных критериев цену за 1 м3 и долговечность.");
        System.out.println("Долговечность - по оси x, цена за 1 м3 - по оси y.\n");
        int x = 0, y = 2; //выбор главных критериев
        double new_A [][] = new double[4][2]; //матрица заначениЙ альтернатив по двум критериям

        //образуем матрицу значений альтернатив по двум критериям
        int l = 0, m = 0;
        for (int i = 0; i < vect.length; i++){
            for (int j = 0; j < vect.length; j++) {
                if ((j == x)||(j == y)) {
                    new_A[i][l] = B[i][j];
                    l++;
                }
            }
            l=0;
        }

        System.out.println("x  y");
        for (int i = 0; i < vect.length; i++){
            for (int j = 0; j < 2; j++) {
                System.out.print((int) new_A [i][j] +"  ");
            }
            System.out.println();
        }
        //вызываем метод нахождения лучшего критерия по Парето
        Opt.Pareto(new_A);

        System.out.println("\n\nВзвешивание и объединение критериев\n");
        //вызываем метод Взвешивания и объединения критериев
        Opt.WeightingCombining_criteria(B,norm_vect);

        System.out.println("\n\nМетод анализа иерархий\n");
        //вызываем метод Метод анализа иерархий
        Opt.analysis_hierarchy(B,vect);
    }
}