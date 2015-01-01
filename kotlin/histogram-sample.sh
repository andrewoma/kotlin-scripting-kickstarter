#!/bin/bash
currentDir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
output="${HOME}/histogram-sample.png"

data="${currentDir}/../test/resources/histogram-sample.txt"
histogram.kt -input "$data" -output "${output}" -label 'Response (s)'

echo "Histogram saved to '${output}'"
