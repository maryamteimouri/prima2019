package multisize;

import main.*;
import prima.PRIMA_State;

import java.util.ArrayList;

public class MULTISIZE_MCTS extends MonteCarloTreeSearch {
    public MULTISIZE_MCTS(Game game, Simulator simulator) {
        super(game, simulator);
    }

    @Override
    public State getBestNextStateMulti(State root) {
        MULTISIZE_State st = (MULTISIZE_State) root;
        State[] gg = new State[st.playerNumber + 1];
        State[] ng = new State[st.playerNumber + 1];
        MULTISIZE_Game ga = (MULTISIZE_Game) game;
        for (int i = 1; i <= st.playerNumber; ++i) {// O(pI^2 + pIT + pIn^2)
            long stt = System.currentTimeMillis();
            ((MULTISIZE_State) ga.agentState[i]).nextColor = i;
            gg[i] = getBestNextStateSingle(ga.agentState[i]);// O(I^2 + IT + In^2)
            PrimaMain.timer[i] += System.currentTimeMillis() - stt;
        }
        for (int i = 1; i <= st.playerNumber; ++i) {// O(p*n^2)
            ng[i] = new MULTISIZE_State(ga.agentState, gg, i);
        }
        ga.agentState = ng;
        return ng[1];
    }

    private State selection(State state) {// Done
        State st = state;
        while (st.isInTree && st.isNotTerminal())
            st = best_uct(st);
        return st;
    }

    private State expansion(State state) {
        // expand just random child here ?
        // expand all child
        // expand some child
        State st = state;
        if (!st.isInTree) {
            st.isInTree = true;
            st.value = game.CreateZeroValue();
        } else if (state.isNotTerminal()) {
            System.out.println("---WTF---");
            // state = simulator.randomChild(state);
            // states.put(state, new Value(0, 0));
        }
        return st;
    }

    private State best_uct(State state) { // DONE
        // Value vx = states.get(state);
        Value vx = state.value;
        ArrayList<State> childs = state.getChilds();

        State ans = null;
        Value vbest = null;
        for (State st : childs) {
            if (!st.isInTree)
                return st;
            Value vv = st.value;
            if (vbest == null || vbest.compareTo_UCT(vv, vx.num) < 0) {
                vbest = vv;
                ans = st;
            }
        }
        return ans;
    }

    private Value rollout(State state) {
        if (PrimaMain.fastRollout)
            return fastRollout(state);
        while (state.isNotTerminal())
            state = state.getRandomChild();
        return state.getValue();
    }

    private Value fastRollout(State state) {
        // oop??
        MULTISIZE_State st = new MULTISIZE_State((MULTISIZE_State) state);
        while (st.isNotTerminal())
            st.rollDown();
        return st.getValue();
    }

    private void backpropagation(Value simulation_result, State state) {
        while (state != null) {
            if (state.isInTree)
                state.value = state.value.update(state, simulation_result);
            state = state.parent;
        }
    }

    private State bestChild(State state) {
        ArrayList<State> childs = state.getChilds();
        State ans = null;
        Value vbest = null;
        for (State ch : childs) {
            Value vv = ch.value;
            if (PrimaMain.debugMode)
                System.out.println("CH:\n" + ch + vv);
            if (vbest == null || vbest.compareTo(vv) < 0) {
                vbest = vv;
                ans = ch;
            }
        }
        return ans;
    }

}
