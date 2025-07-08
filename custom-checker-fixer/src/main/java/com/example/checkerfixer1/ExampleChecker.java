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

import java.util.List;

import chemaxon.checkers.ExternalStructureChecker;
import chemaxon.checkers.result.DefaultExternalStructureCheckerResult;
import chemaxon.checkers.result.StructureCheckerResult;
import chemaxon.struc.MolAtom;
import chemaxon.struc.Molecule;
import chemaxon.struc.PeriodicSystem;

/**
 * Simple example structure checker without parameters. The related fixer is {@link ExampleFixer}.
 * <p>
 * For a parameterized variant, see {@link com.example.checkerfixer2.ParameterizedExampleChecker}.
 */
public class ExampleChecker extends ExternalStructureChecker {

    public ExampleChecker() {
        super("ExampleCheckerError");
    }

    @Override
    protected StructureCheckerResult check1(Molecule molecule) {
        List<MolAtom> atomsWithMoreProtonThanOxygen = molecule.atoms().stream()
                .filter(atom -> atom.getAtno() > PeriodicSystem.O)
                .toList();

        // If there is no error, null should be returned
        if (atomsWithMoreProtonThanOxygen.isEmpty()) {
            return null;
        }

        // Otherwise, return the appropriate result to describe the problem
        return new DefaultExternalStructureCheckerResult(
                this,
                atomsWithMoreProtonThanOxygen,
                List.of(), // list of erroneous bonds, none in our case
                molecule,
                "These atoms have more protons than Oxygen"
        );
    }

}
