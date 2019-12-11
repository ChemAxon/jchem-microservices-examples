/*   Copyright 2019 ChemAxon Ltd.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.chemaxon.exampe.jms.ccf;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import chemaxon.checkers.result.StructureCheckerResult;
import chemaxon.formats.MolImporter;
import chemaxon.struc.Molecule;

public class MyCustomCheckerTest {

    @Test
    @DisplayName("Without atoms heavier than oxygen our checker finds no atoms")
    public void noAtomsFound() throws Exception {
        Molecule m = MolImporter.importMol("c1ccccc1CC(N)C");
        StructureCheckerResult result = new MyCustomChecker().check(m);
        assertTrue(result.getAtoms().isEmpty());
    }
    
    @Test
    @DisplayName("All atoms that are heavier than oxygen is found")
    public void allFound() throws Exception {
        Molecule m = MolImporter.importMol("Brc1ccccc1Cl");
        StructureCheckerResult result = new MyCustomChecker().check(m);
        assertAll(() -> assertFalse(result.getAtoms().isEmpty()), () -> assertEquals(2, result.getAtoms().size()));
    }
    
}
