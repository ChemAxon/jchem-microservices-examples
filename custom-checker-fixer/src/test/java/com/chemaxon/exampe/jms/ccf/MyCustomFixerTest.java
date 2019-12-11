package com.chemaxon.exampe.jms.ccf;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import chemaxon.checkers.result.StructureCheckerResult;
import chemaxon.formats.MolImporter;
import chemaxon.struc.Molecule;
import chemaxon.struc.PeriodicSystem;

public class MyCustomFixerTest {

    @Test
    @DisplayName("fixer replaces heavy atoms with Oxygen")
    public void testFix() throws Exception {
        Molecule mol = MolImporter.importMol("CNCl");

        MyCustomChecker checker = new MyCustomChecker();
        StructureCheckerResult result = checker.check(mol);

        MyCustomFixer fixer = new MyCustomFixer();
        fixer.fix(result);

        assertAll(() -> assertEquals(3, mol.atoms().size()),
                () -> assertEquals(PeriodicSystem.C, mol.getAtom(0).getAtno()),
                () -> assertEquals(PeriodicSystem.N, mol.getAtom(1).getAtno()),
                () -> assertEquals(PeriodicSystem.O, mol.getAtom(2).getAtno()));
    }

}
