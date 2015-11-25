#!/bin/bash
##############################################################################
#
##############################################################################

DEBUG=0
RUNNER="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
SCRIPT=$1
HOME=`cd $RUNNER/../; pwd -P`
CLASSES="$HOME/build/kotlin-classes/main"
PACKAGE=`grep '^package ' "$1" | awk -F'[\t ;]' '{print $2}'`
MAIN=`echo "$PACKAGE" | awk -F'.' '{print $NF}'`
MAIN="$(tr '[:lower:]' '[:upper:]' <<< ${MAIN:0:1})${MAIN:1}"
MAIN="$PACKAGE.${MAIN}Kt"
COMPILED=`echo "$MAIN" | sed 's/\./\//g'`
COMPILED="$CLASSES/$COMPILED.class"

if [ $DEBUG -gt 0 ]; then
    echo "RUNNER=$RUNNER"
    echo "SCRIPT=$SCRIPT"
    echo "HOME=$HOME"
    echo "CLASSES=$CLASSES"
    echo "PACKAGE=$PACKAGE"
    echo "MAIN=$MAIN"
    echo "COMPILED=$COMPILED"
fi

if [[ (! -f "$COMPILED") || ("$SCRIPT" -nt "$COMPILED") ]]; then
    OUTPUT=`"$HOME/gradlew" --build-file "$HOME/build.gradle" --project-dir "$HOME" clean classes copyToLib`
    STATUS=$?
    if [ $STATUS -gt 0 ]; then
        echo "$OUTPUT" 1>&2
        exit $STATUS
    fi
fi

[ -n "$JAVA_OPTS" ] || JAVA_OPTS="-Xmx512M -Xms32M"

if [ -z "$JAVACMD" -a -n "$JAVA_HOME" -a -x "$JAVA_HOME/bin/java" ]; then
    JAVACMD="$JAVA_HOME/bin/java"
fi

if [ $DEBUG -gt 0 ]; then
    echo "${JAVACMD:=java}" $JAVA_OPTS "-Dkotlin.script.file=$SCRIPT" -cp "$HOME/build/kotlin-classes/main:$HOME/lib/*" $MAIN "$@"
fi

shift
"${JAVACMD:=java}" $JAVA_OPTS "-Dkotlin.script.file=$SCRIPT" -cp "$HOME/build/kotlin-classes/main:$HOME/lib/*" $MAIN "$@"
exit $?
