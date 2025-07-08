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
package com.example.checkerfixer1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import chemaxon.formats.MolImporter;
import chemaxon.struc.Molecule;
import chemaxon.struc.PeriodicSystem;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExampleFixerTest {

    @Test
    @DisplayName("Fixer replaces heavy atoms with Oxygen")
    void testFix() throws Exception {
        var checker = new ExampleChecker();
        var fixer = new ExampleFixer();
        Molecule molecule = MolImporter.importMol("CNCl");

        fixer.fix(checker.check(molecule));

        assertAll(
                () -> assertEquals(3, molecule.atoms().size()),
                () -> assertEquals(PeriodicSystem.C, molecule.getAtom(0).getAtno()),
                () -> assertEquals(PeriodicSystem.N, molecule.getAtom(1).getAtno()),
                () -> assertEquals(PeriodicSystem.O, molecule.getAtom(2).getAtno())
        );
    }

}
