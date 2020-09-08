package multisize;

import main.Action;
import main.Simulator;
import main.State;

public class MULTISIZE_Simulator extends Simulator {

    @Override
    public State simulate(State state, Action action) {
        MULTISIZE_Action act = (MULTISIZE_Action) action;
        MULTISIZE_State st = (MULTISIZE_State) state;
        MULTISIZE_State res = null;
        if (st.table[act.y][act.x] == 0)
            res = new MULTISIZE_State(st, act);
        return res;
    }

    public static State simulateX(State state, Action action) {
        MULTISIZE_Action act = (MULTISIZE_Action) action;
        MULTISIZE_State st = (MULTISIZE_State) state;
        MULTISIZE_State res = null;
        if (st.table[act.y][act.x] == 0 || st.table[act.y][act.x] == -1 || act.stay)
            res = new MULTISIZE_State(st, act);
        return res;
    }

}
