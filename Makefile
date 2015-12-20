


agents: harvester
	java -cp libs/jade.jar:classes/InformationHarvester.class jade.Boot -agents pedro:InformationHarvester.HarvesterAgent

harvester:
	javac -classpath libs/jade.jar -d libs src/InformationHarvester/*.java