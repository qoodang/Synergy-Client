#!/bin/bash

for file in $(find . -name "*.java"); do
    cp $file $file.old
    cat header "$file" $file.old > "$file"
done
