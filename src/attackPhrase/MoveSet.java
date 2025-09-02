package attackPhrase;

import java.util.ArrayList;
import java.util.List;


public class MoveSet {
    private List<Move> moves;


    public MoveSet() {
        moves = new ArrayList<>();
    }


    public void addMove(Move move) {
        if (moves.size() < 4) {
            moves.add(move);
        }
    }


    public Move getMove(int index) {
        return moves.get(index);
    }


    public List<Move> getMoves() {
        return moves;
    }
}
