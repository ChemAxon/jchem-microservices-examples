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

package com.chemaxon.exampe.jms.ccf;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import chemaxon.formats.MolImporter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

 class MyCustomCheckerTest {

    @Test
    @DisplayName("Without atoms heavier than oxygen our checker finds no atoms")
     void noAtomsFound() throws Exception {
        var m = MolImporter.importMol("c1ccccc1CC(N)C");
        var result = new MyCustomChecker().check(m);
        assertTrue(result.getAtoms().isEmpty());
    }

    @Test
    @DisplayName("All atoms that are heavier than oxygen is found")
     void allFound() throws Exception {
        var m = MolImporter.importMol("Brc1ccccc1Cl");
        var result = new MyCustomChecker().check(m);
        assertEquals(2, result.getAtoms().size());
    }

}
