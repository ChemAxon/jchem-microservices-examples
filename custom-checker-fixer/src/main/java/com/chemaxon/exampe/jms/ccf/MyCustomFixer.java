/*   Copyright 2019 ChemAxon Ltd.
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.chemaxon.exampe.jms.ccf;

import chemaxon.checkers.result.StructureCheckerResult;
import chemaxon.fixers.AbstractStructureFixer;
import chemaxon.struc.PeriodicSystem;

public class MyCustomFixer extends AbstractStructureFixer {

    @Override
    public boolean fix(StructureCheckerResult checkerResult) {
        checkerResult.getAtoms().forEach(atom -> {
            atom.setAtno(PeriodicSystem.O);
        });
        return true;
    }

}
