package com.company.robot;

import java.util.ArrayList;
import java.util.List;

import static com.company.robot.Instruction.TURNLEFT;
import static com.company.robot.Instruction.TURNRIGHT;

public class InstructionListTool {

    static List<Instruction> compacte(List<Instruction> instructions) {
        List<Instruction> copieCompacte = new ArrayList<Instruction>();
        List<Instruction> instructionsEnAttente = new ArrayList<Instruction>();
        for (int i = 0; i < instructions.size(); i++) {
            if (instructions.get(i) == TURNRIGHT && instructionsEnAttente.size() == 2) {
                instructionsEnAttente.clear();
                copieCompacte.add(TURNLEFT);
            } else if (instructions.get(i) == TURNRIGHT)
                instructionsEnAttente.add(TURNRIGHT);
            else {
                copieCompacte.addAll(instructionsEnAttente);
                instructionsEnAttente.clear();
                copieCompacte.add(instructions.get(i));
            }
        }
        copieCompacte.addAll(instructionsEnAttente);
        return copieCompacte;
    }

    static <T> List<T> concatene(List<T> trace, T coordinates) {
        ArrayList<T> coordonnees = new ArrayList<T>(trace);
        coordonnees.add(coordinates);
        return coordonnees;
    }
}
