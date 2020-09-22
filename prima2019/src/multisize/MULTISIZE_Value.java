package multisize;

import main.State;
import main.Value;

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
                        * (1 / st.playerNumber)
                        : 0
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
//        value = (value + getMatchingValue(state))/2;
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

    public double getMatchingValue(State state){
        int counter = 0;
        MULTISIZE_State st = (MULTISIZE_State) state;
        for (int i = 0; i < st.assignment.length; ++i){
            if(st.table[st.assignment[i][0]][st.assignment[i][1] ]== -(i+1)-1){
                counter++;
            }
        }
        return (double) (counter/st.playerNumber);
    }
}
