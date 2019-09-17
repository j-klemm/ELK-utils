# ELK-utils
Various files that are helpful for deploying ElasticStack, ingesting data into ElasticSearch or creating Kibana visualizations. Most of this is a reduced form of a Maxar Corp. project, edited to be open-source.

**NOTE:** Most of the "real" stuff can't be shared on this public repo. If you're developing for Maxar, refer to the company's private repo which is more up-to-date. This repo is here mostly to help with common problems involving multi-node ElasticSearch clusters on Docker and to provide an example for the complicated GeoJSON visualizations using Vega in Kibana.

*ELK-resources* contains files for Docker Compose (run `docker stack deploy --compose-file docker-compose.yml dev`) and a logstash config file.

*kibana-visuals* has some utility files for posting data to ES and a Vega JSON file to make the visualization from posted data. Most of the "real" data, data generation, etc. has been removed for lowside.
