package multisize;

import main.Game;
import main.PrimaMain;
import main.State;
import main.Value;
import prima.PRIMA_State;
import prima.PRIMA_Value;

public class MULTISIZE_Game extends Game{

    public State[] agentState;

    @Override
    public void init() {
        Centralized = false;
        myState = new MULTISIZE_State(PrimaMain.testCase, 1);
        MULTISIZE_State ms = (MULTISIZE_State) myState;
        Game.endTime = 3 * (ms.width + ms.height) / 2;
        PrimaMain.timer = new long[ms.playerNumber + 1];
        for (int i = 1; i <= ms.playerNumber; ++i)
            PrimaMain.timer[i] = 0;
        //Game.endTime = 10;
        agentState = new State[ms.playerNumber + 1];
        agentState[1] = myState;
        for (int i = 2; i <= ms.playerNumber; ++i) {
            agentState[i] = new MULTISIZE_State(PrimaMain.testCase, i);
        }
    }

    @Override
    public Value CreateZeroValue() {
        return new MULTISIZE_Value(0, 0);
    }
}
