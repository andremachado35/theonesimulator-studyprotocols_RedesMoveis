#! /bin/sh
java -Xmx8G -cp target:lib/ECLA.jar:lib/DTNConsoleConnection.jar core.DTNSim $*
