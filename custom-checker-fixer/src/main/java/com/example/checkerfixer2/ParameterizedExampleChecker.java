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

import java.util.List;
import java.util.Map;

import chemaxon.checkers.ExternalStructureChecker;
import chemaxon.checkers.result.StructureCheckerResult;
import chemaxon.struc.MolAtom;
import chemaxon.struc.Molecule;
import chemaxon.struc.PeriodicSystem;

import static java.util.stream.Collectors.toList;

/**
 * Example structure checker with parameters. The related fixer is {@link ParameterizedExampleFixer}.
 * <p>
 * For a simple variant, see {@link com.example.checkerfixer1.ExampleChecker}.
 */
public class ParameterizedExampleChecker extends ExternalStructureChecker {

    private static final String ERROR_CODE = "MyCheckerError";

    private int atno = PeriodicSystem.O;

    public ParameterizedExampleChecker() {
        super(ERROR_CODE);
    }

    // When using the XML-based endpoint (/rest-v1/checker-fixer/xml-based), custom parameters are passed to this
    // constructor. This constructor can be omitted if the checker has no parameters or the XML-based endpoint is not
    // used.
    public ParameterizedExampleChecker(Map<String, String> properties) {
        super(ERROR_CODE);
        atno = Integer.parseInt(properties.get("atomicNumber"));
    }

    // When using the JSON-based endpoints (all endpoints except /rest-v1/checker-fixer/xml-based), custom parameters
    // are passed using setters.
    public void setAtomicNumber(int atomicNumber) {
        this.atno = atomicNumber;
    }

    @Override
    protected StructureCheckerResult check1(Molecule molecule) {
        List<MolAtom> atomsWithMoreProtons = molecule.atoms().stream()
                .filter(atom -> atom.getAtno() > atno)
                .collect(toList());

        // If there is no error, null should be returned
        if (atomsWithMoreProtons.isEmpty()) {
            return null;
        }

        // Otherwise, return the appropriate result to describe the problem.
        // We return an instance of a custom class here to pass parameters to the fixer.
        return new ExampleCheckerResult(
                this,
                atomsWithMoreProtons,
                List.of(), // list of erroneous bonds, none in our case
                molecule,
                "These atoms have more than " + atno + " protons",
                atno
        );
    }

}
