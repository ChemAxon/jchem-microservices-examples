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

package com.chemaxon.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;

import chemaxon.formats.MolExporter;
import chemaxon.formats.MolImporter;
import chemaxon.struc.Molecule;

import com.chemaxon.example.exception.NoIdException;

public class TransferData {

    private static final String CD_ID = "CD_ID";

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Please provide inputfiles to work with");
            throw new IllegalArgumentException("No inputfiles were provided");
        }
        for (String arg : args) {
            ObjectMapper objectMapper = new ObjectMapper();
            try (SequenceWriter writer = objectMapper.writer().writeValues(getOutputFile(arg)).init(true)) {
                System.out.println("\nProcessing: " + arg);
                try {
                    createJsonFromMolecules(writer, arg);
                    System.out.println("Successfully processed.");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } catch (IOException e) {
                throw new IllegalStateException("Could not write json from molecule file: " + arg, e);
            }
        }
    }

    private static void createJsonFromMolecules(SequenceWriter writer, String fileName) throws IOException {
        try (MolImporter mi = new MolImporter(fileName)) {
            mi.getMolStream().forEach(molecule -> {
                try {
                    writer.write(moleculeToJson(molecule, fileName));
                } catch (NoIdException | IOException e) {
                    throw new IllegalArgumentException("Could not process file: " + fileName, e);
                }
            });
        }
    }

    private static File getOutputFile(String fileName) throws IOException {
        Path outputPath = Path.of("./output");
        Files.createDirectories(outputPath);
        return outputPath.resolve(Path.of(fileName).getFileName() + ".json").toFile();
    }

    private static Map<String, Object> moleculeToJson(Molecule molecule, String fileName)
            throws NoIdException, IOException {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("id", getId(molecule, fileName));
        result.put("molecule", MolExporter.exportToFormat(molecule, "mrv"));
        result.put("additionalData", getAdditionalData(molecule));
        return result;
    }

    private static Map<String, Object> getAdditionalData(Molecule molecule) {
        Map<String, Object> additionalData = new HashMap<>();
        for (int i = 0; i < molecule.getPropertyCount(); ++i) {
            String propertyKey = molecule.getPropertyKey(i);
            if (!propertyKey.equals(CD_ID)) {
                additionalData.put(propertyKey, molecule.getPropertyObject(propertyKey));
            }
        }
        return additionalData;
    }

    private static long getId(Molecule mol, String fileName) throws NoIdException {
        String id;

        var idObject = mol.getPropertyObject(CD_ID);
        if (idObject != null) {
            id = (String) idObject;
        } else if (mol.getName() != null) {
            id = mol.getName();
        } else {
            throw new NoIdException(fileName);
        }

        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new NoIdException(fileName, e);
        }
    }

}
