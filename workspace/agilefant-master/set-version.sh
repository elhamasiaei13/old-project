#!/bin/sh

if [ -z "$1" ]; then
  echo "Usage: $0 <version>"
  exit 1
fi

VERSION=$1

mvn -Pversion versions:set -DnewVersion=$VERSION
