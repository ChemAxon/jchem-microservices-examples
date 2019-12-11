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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import chemaxon.checkers.ExternalStructureChecker;
import chemaxon.checkers.result.DefaultExternalStructureCheckerResult;
import chemaxon.checkers.result.StructureCheckerResult;
import chemaxon.struc.MolAtom;
import chemaxon.struc.Molecule;
import chemaxon.struc.PeriodicSystem;

public class MyCustomChecker extends ExternalStructureChecker {

    public MyCustomChecker() {
        super("MyCheckerError");
    }

    @Override
    protected StructureCheckerResult check1(Molecule molecule) {
        List<MolAtom> atomsWithMoreProtonThanOxygen = molecule.atoms().stream()
                .filter(atom -> atom.getAtno() > PeriodicSystem.O).collect(Collectors.toList());
        return new DefaultExternalStructureCheckerResult(this, atomsWithMoreProtonThanOxygen, new ArrayList<>(),
                molecule, "These atoms have more protons than Oxygen");
    }

}
