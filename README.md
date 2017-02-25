# Distributed-Key-Value-Pair-BigData-
A distributed data store is a computer network where information is stored on more than one node, often in a replicated fashion. It is usually specifically used to refer to either a distributed database where users store information on a number of nodes, or a computer network in which users store information on a number of peer network nodes.

Pre-requisite:
1.Zookeeper
2.Java
------------------------------------------------------------------------------
Put the log4j.properties file in Zookeeper folder.
To run the code excecute the shell script(proj.sh).
It checks if the master server is already exists, if it does not exists then it creates a master server and the other two servers.
If the master server is already exists then it creates the other two servers.
