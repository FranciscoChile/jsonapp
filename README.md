# Sonatafi JSON Parser v2

Thinking process

1. Main class will take a file (properties.txt) with diverse JSON examples.
2. Get ensure all jsons syntax are correct (Developing JSON schema validator)
3. Identifing pattern using requeriments and JSON examples.
4. Defining classes needed (tools and POJOs)
5. Working in requeriments from 1 to 4 (it use Jackson to map JSON)
6. Developing DiffTool as is defined in document, two arguments and a list as return.
7. Developing unit test including all possible cases.
8. Unit test give us some errors that let us refine the code.
9. SonarLint applied to fix some issues
    - Refactor this method to reduce its Cognitive Complexity
    - Standard outputs should not be used directly to log anything.
    - Generic exceptions should never be thrown.
    - Boolean expressions should not be gratuitous.

As for this one
    - Unused “private” fields should be removed, six issues were found.
It seems refers to the fields in model classes, those are actually used in the code.

Summary

Main class take one file with JSON examples, process everything using two classes and show a message in the case of property changes. Took me 2 hours.