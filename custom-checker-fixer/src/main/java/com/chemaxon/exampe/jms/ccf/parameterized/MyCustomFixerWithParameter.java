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

import chemaxon.checkers.result.StructureCheckerResult;
import chemaxon.fixers.AbstractStructureFixer;

public class MyCustomFixerWithParameter extends AbstractStructureFixer {

    @Override
    public boolean fix(StructureCheckerResult checkerResult) {
        // Fixers cannot have parameters on their own, but they always receive the result object that was returned by
        // the connected checker, so they can use that to pass information.
        var atno = ((MyStructureCheckerResult) checkerResult).atno;
        checkerResult.getAtoms().forEach(atom -> atom.setAtno(atno));
        return true;
    }

}
