# How to write custom Structure Checker / Structure Fixer for JChem Microservices

This repository contains an example project, on how to create your own Structure Checkers and Structure Fixers. In the
end, you can build the application and use the jar in JChem Microservices.

## How to build the project

First of all, you have to obtain your access keys as described in the
gradle-panel [here](https://chemaxon.com/products/jchem-engines/download#gradle-panel). You can write these
information to the `gradle.properties` file.

Execute the build with: `./gradlew build` command (it requires that you have the appropriate license).

The built jar can be found in `build/libs` folder.

## How to use the built jar in JChem Microservices

Copy the jar file to the `extra-libs` folder in your JChem Microservices installation.

Send a request with content:

```json
{
  "checkerSettings": [
    {
      "checkerClass": "com.example.checkerfixer1.ExampleChecker",
      "displayName": "custom checker"
    }
  ],
  "filter": {
    "untriggered": false
  },
  "structure": {
    "source": "C1C=CC=CC=1CC(N)CCl"
  }
}
```

to `/rest-v1/checker/` endpoint.

### What you should expect

Our dummy checker checks for atoms with higher atom number than Oxygen. In the input we have
a Chlorine atom, that should be found by the checker

### How to use our fixer with our checker

Send the following example input to the  `/rest-v1/checker-fixer/`  endpoint.

Example input:

```json
{
  "exportFormat": "smiles",
  "filter": {
    "fixed": false,
    "untriggered": false
  },
  "settings": [
    {
      "checkerSettings": {
        "checkerClass": "com.example.checkerfixer1.ExampleChecker",
        "displayName": "custom checker"
      },
      "fixerSettings": {
        "displayName": "custom fixer",
        "exportFormat": "smiles",
        "fixerClass": "com.example.checkerfixer1.ExampleFixer"
      }
    }
  ],
  "structure": {
    "source": "C1C=CC=CC=1CC(N)CCl"
  }
}
```

#### What you should expect

Our dummy fixer replaces all atoms found by the checker with Oxygen. In the final result molecule you should not see any
Chlorine atoms, but Oxygens in their places.

## How to pass parameters to your checkers

See the classes in the `com.example.checkerfixer2` package.

To use the checker, send a request like below to `/rest-v1/checker/`.

```json
{
  "checkerSettings": [
    {
      "checkerClass": "com.example.checkerfixer2.ParameterizedExampleChecker",
      "displayName": "parameterized custom checker",
      "atomicNumber": 6
    }
  ],
  "filter": {
    "untriggered": false
  },
  "structure": {
    "source": "C1C=CC=CC=1CC(N)CCl"
  }
}
```

To fix the structure as well, send a request like below to `/rest-v1/checker-fixer/`. Notice that we only added to
parameter to the checker as the fixer cannot have a parameter on its own.

```json
{
  "exportFormat": "smiles",
  "filter": {
    "fixed": false,
    "untriggered": false
  },
  "settings": [
    {
      "checkerSettings": {
        "checkerClass": "com.example.checkerfixer2.ParameterizedExampleChecker",
        "displayName": "parameterized custom checker",
        "atomicNumber": 6
      },
      "fixerSettings": {
        "displayName": "parameterized custom fixer",
        "exportFormat": "smiles",
        "fixerClass": "com.example.checkerfixer2.ParameterizedExampleFixer"
      }
    }
  ],
  "structure": {
    "source": "C1C=CC=CC=1CC(N)CCl"
  }
}
```

### What you should expect

Our dummy checker and fixer check for atoms with higher atom number than the provided.
