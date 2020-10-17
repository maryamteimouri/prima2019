package multisize;

import main.State;
import main.Value;

import java.util.ArrayList;
import java.util.List;

public class MULTISIZE_Value extends Value {

    public MULTISIZE_Value(int num, double value) {
        super(num, value);
    }

    public MULTISIZE_Value(int i, double d, boolean[] m) {
        this.num = i;
        this.value = d;
        bestValue = value;
        this.mark = m;
    }

    @Override
    public MULTISIZE_Value update(State state, Value simulationResult) {
        MULTISIZE_State st = (MULTISIZE_State) state;
        MULTISIZE_Value simulation_result = (MULTISIZE_Value) simulationResult;
        ++num;
        // TODO change ?
        bestValue = Math.max(bestValue,
                simulation_result.value
                        - (st.lastColor != -1
                        ? simulation_result.mark[st.lastColor]
                        ? (double) (1.5 - (double) (((modelNumber - 1) % 3) + 1) / 2)
                        * (1 / (double) st.playerNumber)
                        : (double) (1.5 - (double) (((modelNumber - 1) % 3) + 1) / 2)
                        * -(1 / ( getNearestTargetDist(st)))//st.playerNumber *
                        : 0));
        switch (modelNumber) {
            case 1:
            case 2:
            case 3:
                value = (value * (num - 1) + simulation_result.value
                        - (st.lastColor != -1
                        ? simulation_result.mark[st.lastColor]
                        ? (double) (1.5 - (double) modelNumber / 2) * (1 / st.playerNumber)
                        : 0
                        : 0))
                        / num;
                break;
            case 4:
            case 5:
            case 6:
                value = bestValue;
                break;
            default:
                break;
        }
        return this;
    }

    @Override
    public String toString() {
        return "MULTISIZE_Value [num=" + num + ", value=" + value + ", bestValue=" + bestValue + "]";
    }

    @Override
    public int compareTo_UCT(Value vv, int total_number) {
        double u1 = value + Math.sqrt(2 * Math.log(total_number) / num);
        double u2 = vv.value + Math.sqrt(2 * Math.log(total_number) / vv.num);
        if (u1 < u2)
            return -1;
        else
            return 1;
    }

    private double getNearestTargetDist(MULTISIZE_State st) {

        int[][] cost_table;
        List<MULTISIZE_State.PII> targets;
        int[][] assignment = new int[st.playerNumber][2];

        targets = new ArrayList<>(st.playerNumber);

        cost_table = new int[st.playerNumber][st.playerNumber];


        for (int i = 0; i < st.width; ++i) {
            for (int j = 0; j < st.height; ++j) {
                if (st.table[i][j] == -1) {
                    targets.add(new MULTISIZE_State.PII(i, j));
                }
            }
        }

        int cost;
        for (int i = 1; i <= st.playerNumber; ++i) {
            for (int j = 0; j < targets.size(); ++j) {
                cost = 1000;
                if (st.table[st.lastMove[i].first][st.lastMove[i].second] > 0)
                    cost = (int) ((int) Math.pow((st.lastMove[i].first - targets.get(j).first), 2) + Math.pow((st.lastMove[i].second - targets.get(j).second), 2));
                cost_table[i - 1][j] = cost;
            }
        }


        HungarianAlgorithm ha = new HungarianAlgorithm(cost_table);
        assignment = ha.findOptimalAssignment();
//        hungarian check
//        String s = "{";
//        for (int i = 0; i < assignment.length; ++i) {
//                if (assignment[i][1] >= 0 && assignment[i][1] < targets.size())
//                    s += targets.get(assignment[i][1]) + ", ";
//            s += "\n";
//        }
//        s += "}";
//        System.out.println(s);


        double dist = Math.sqrt(Math.pow(st.height, 2) + Math.pow(st.width, 2));
        double max_dist = Math.sqrt(Math.pow(st.height, 2) + Math.pow(st.width, 2));
        if (assignment[st.lastColor - 1][1] >= 0 && assignment[st.lastColor - 1][1] < targets.size()) {
            dist = (Math.pow(targets.get(assignment[st.lastColor - 1][1]).first - st.lastMove[st.lastColor ].first, 2)
                    + Math.pow(targets.get(assignment[st.lastColor - 1][1]).second - st.lastMove[st.lastColor ].second, 2));
//            System.out.println(st.lastColor + ", " + targets.get(assignment[st.lastColor - 1][1]).first + ", "
//                    + targets.get(assignment[st.lastColor - 1][1]).second + ", " + dist);
        }

        return dist;
    }


}
