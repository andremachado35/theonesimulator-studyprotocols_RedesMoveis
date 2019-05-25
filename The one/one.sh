#! /bin/sh
java -Xmx3G -cp target:lib/ECLA.jar:lib/DTNConsoleConnection.jar core.DTNSim $*
