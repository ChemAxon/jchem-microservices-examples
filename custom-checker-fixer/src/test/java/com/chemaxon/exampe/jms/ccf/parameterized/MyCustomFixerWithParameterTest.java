/*
 * Copyright 2019-2025 Chemaxon Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.chemaxon.exampe.jms.ccf.parameterized;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import chemaxon.formats.MolImporter;
import chemaxon.struc.MolAtom;
import chemaxon.struc.PeriodicSystem;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MyCustomFixerWithParameterTest {

    @Test
    @DisplayName("Fixer replaces heavy atoms with specified atom")
    void testFix() throws Exception {
        var mol = MolImporter.importMol("CNCl");

        var checker = new MyCustomCheckerWithParameter();
        var fixer = new MyCustomFixerWithParameter();
        fixer.fix(checker.check(mol));

        assertEquals(
                List.of(PeriodicSystem.C, PeriodicSystem.N, PeriodicSystem.O),
                mol.atoms().stream().map(MolAtom::getAtno).toList()
        );

        checker.setAtomicNumber(PeriodicSystem.N);
        fixer.fix(checker.check(mol));

        assertEquals(
                List.of(PeriodicSystem.C, PeriodicSystem.N, PeriodicSystem.N),
                mol.atoms().stream().map(MolAtom::getAtno).toList()
        );

        checker.setAtomicNumber(PeriodicSystem.C);
        fixer.fix(checker.check(mol));

        assertEquals(
                List.of(PeriodicSystem.C, PeriodicSystem.C, PeriodicSystem.C),
                mol.atoms().stream().map(MolAtom::getAtno).toList()
        );
    }

}
