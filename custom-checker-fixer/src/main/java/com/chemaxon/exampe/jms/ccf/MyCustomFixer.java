package com.chemaxon.exampe.jms.ccf;

import chemaxon.checkers.result.StructureCheckerResult;
import chemaxon.fixers.AbstractStructureFixer;
import chemaxon.struc.PeriodicSystem;

public class MyCustomFixer extends AbstractStructureFixer {

    @Override
    public boolean fix(StructureCheckerResult checkerResult) {
        checkerResult.getAtoms().forEach(atom -> {
            atom.setAtno(PeriodicSystem.O);
        });
        return true;
    }

}
