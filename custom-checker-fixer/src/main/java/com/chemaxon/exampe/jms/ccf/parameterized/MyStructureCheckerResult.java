package com.chemaxon.exampe.jms.ccf.parameterized;

import java.util.List;

import chemaxon.checkers.StructureChecker;
import chemaxon.checkers.result.DefaultExternalStructureCheckerResult;
import chemaxon.struc.MolAtom;
import chemaxon.struc.MolBond;
import chemaxon.struc.Molecule;

public class MyStructureCheckerResult extends DefaultExternalStructureCheckerResult {

    final int atno;

    public MyStructureCheckerResult(StructureChecker source, List<MolAtom> atoms,
            List<MolBond> bonds, Molecule molecule, String errorCode, int atno) {
        super(source, atoms, bonds, molecule, errorCode);
        this.atno = atno;
    }

}
