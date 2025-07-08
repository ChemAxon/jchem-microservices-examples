package com.example.checkerfixer2;

import java.util.List;

import chemaxon.checkers.StructureChecker;
import chemaxon.checkers.result.DefaultExternalStructureCheckerResult;
import chemaxon.struc.MolAtom;
import chemaxon.struc.MolBond;
import chemaxon.struc.Molecule;

/**
 * Custom checker result type to pass parameters from {@link ParameterizedExampleChecker} to
 * {@link ParameterizedExampleFixer}. If the fixer has no parameters, the {@link DefaultExternalStructureCheckerResult}
 * can be used instead.
 */
public class ExampleCheckerResult extends DefaultExternalStructureCheckerResult {

    final int atno;

    public ExampleCheckerResult(StructureChecker source, List<MolAtom> atoms,
            List<MolBond> bonds, Molecule molecule, String errorCode, int atno) {
        super(source, atoms, bonds, molecule, errorCode);
        this.atno = atno;
    }

}
