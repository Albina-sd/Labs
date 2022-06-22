package social_net;

public class Lab_5 {

    public static void main(String[] args) {
        Matrix matrix = new Matrix();
        int size = 10;
        double [][] trustM = matrix.generate_matrix(size);
        double [] vectorMind = matrix.agent_opinion(size);

        matrix.final_opinion(trustM,vectorMind,false);

        System.out.println("\nWith influence:");
        Influence influence = new Influence();
        influence.gen_agent(size);
        int agents1 [] = influence.agent1;
        int agents2 [] = influence.agent2;
        double opinion1 = 57, opinion2 = -67;
        System.out.format("opinion 1 player: %3.0f; opinion 2 player: %2.0f.\n\n",opinion1,opinion2);
        vectorMind = influence.init(size,agents1,agents2,opinion1,opinion2);

        matrix.final_opinion(trustM,vectorMind,false);
    }
}
