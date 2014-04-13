#!/bin/bash
data="$0/../../test/resources/histogram-sample.txt"
histogram.kt -input "$data" -output "$HOME/histogram-sample.png" -label 'Response (s)'
