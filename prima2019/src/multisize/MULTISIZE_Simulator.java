package multisize;

import main.Action;
import main.Simulator;
import main.State;
import prima.PRIMA_Action;

import java.util.ArrayList;

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

    public static ArrayList<Action> getActionsxt(State state) {
        MULTISIZE_State st = (MULTISIZE_State) state;
        ArrayList<Action> acs = new ArrayList<Action>();
        if (st.table[st.lastMove[st.nextColor].first][st.lastMove[st.nextColor].second] < 0)
            acs.add(new MULTISIZE_Action(st.lastMove[st.nextColor].second, st.lastMove[st.nextColor].first, st.nextColor,
                    true));
        else
            for (int i = -1; i < 2; ++i)
                for (int j = (i == 0 ? -1 : 0); j < (i == 0 ? 2 : 1); ++j)
                    if (st.lastMove[st.nextColor].first + i >= 0 && st.lastMove[st.nextColor].first + i < st.width
                            && st.lastMove[st.nextColor].second + j >= 0
                            && st.lastMove[st.nextColor].second + j < st.height
                            && (st.table[st.lastMove[st.nextColor].first + i][st.lastMove[st.nextColor].second + j] == 0
                            || st.table[st.lastMove[st.nextColor].first + i][st.lastMove[st.nextColor].second
                            + j] == -1))
                        acs.add(new MULTISIZE_Action(st.lastMove[st.nextColor].second + j,
                                st.lastMove[st.nextColor].first + i, st.nextColor));
        return acs;
    }

    public ArrayList<Action> getActionst(State state) {
        MULTISIZE_State st = (MULTISIZE_State) state;
        ArrayList<Action> acs = new ArrayList<Action>();
        if (st.table[st.lastMove[st.nextColor].first][st.lastMove[st.nextColor].second] < 0)
            acs.add(new MULTISIZE_Action(st.lastMove[st.nextColor].second, st.lastMove[st.nextColor].first, st.nextColor,
                    true));
        else
            for (int i = -1; i < 2; ++i)
                for (int j = (i == 0 ? -1 : 0); j < (i == 0 ? 2 : 1); ++j)
                    if (st.lastMove[st.nextColor].first + i >= 0 && st.lastMove[st.nextColor].first + i < st.width
                            && st.lastMove[st.nextColor].second + j >= 0
                            && st.lastMove[st.nextColor].second + j < st.height
                            && (st.table[st.lastMove[st.nextColor].first + i][st.lastMove[st.nextColor].second + j] == 0
                            || st.table[st.lastMove[st.nextColor].first + i][st.lastMove[st.nextColor].second
                            + j] == -1))
                        acs.add(new MULTISIZE_Action(st.lastMove[st.nextColor].second + j,
                                st.lastMove[st.nextColor].first + i, st.nextColor));
        return acs;
    }

}
