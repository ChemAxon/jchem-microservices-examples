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

package com.example.checkerfixer2;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import chemaxon.checkers.result.StructureCheckerResult;
import chemaxon.formats.MolImporter;
import chemaxon.struc.Molecule;
import chemaxon.struc.PeriodicSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ParameterizedExampleCheckerTest {

    private static final String MOLECULE = "c1ccccc1CC(N)C";

    @Test
    @DisplayName("Without atoms heavier than oxygen our checker finds no atoms")
    void noAtomsFound() throws Exception {
        var checker = new ParameterizedExampleChecker();
        Molecule molecule = MolImporter.importMol(MOLECULE);

        StructureCheckerResult result = new ParameterizedExampleChecker().check(molecule);

        assertNull(result);
    }

    @Test
    @DisplayName("Passing the atomic number in the appropriate constructor changes the checker")
    void useConstructor() throws Exception {
        var checker = new ParameterizedExampleChecker(Map.of("atomicNumber", "6"));
        Molecule molecule = MolImporter.importMol(MOLECULE);

        StructureCheckerResult result = checker.check(molecule);

        assertNotNull(result);
        assertEquals(1, result.getAtoms().size());
    }

    @Test
    @DisplayName("Passing the atomic number in the setter changes the checker")
    void useSetter() throws Exception {
        var checker = new ParameterizedExampleChecker();
        checker.setAtomicNumber(PeriodicSystem.C);
        Molecule molecule = MolImporter.importMol(MOLECULE);

        StructureCheckerResult result = checker.check(molecule);

        assertNotNull(result);
        assertEquals(1, result.getAtoms().size());
    }

}
