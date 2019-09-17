This directory contains alpha version visualizations in kibana. 

## How to navigate the visualizations
Each visualization directory contains the following:
- **`README`** describing the visualization and the data generation tool, if there is one
- **`{VIS_NAME}-mapping.jso`** describing the index mapping for the data to go in
- **`{VIS_NAME}-vega.json`** if the vis is Vega, for you to plug into the Vega dev console
- **`postNew{VIS_NAME}.sh`** to be run from your *local machine*. Adds the data in the data dir
- **`add{VIS_NAME)ToEs.sh`** to be run from *the EC2.*  the `post` script runs this for you.
- **`{VIS_NAME}Data` dir OR `{VIS_NAME}-generator` dir**. One of these will contain the data. If it has a generator, it's contained in `generator/src/main/resources`



###Utils 
**utils** contains (gasp!) utility scripts for these visualizations/Kibana. Specifically, import-export for kibana and JSON data templates for PGMM data.

###Visualization Descriptions
Most of these you can see for yourself active on the TO14 Kibana dashboard.

**geopoint:** Queries the given index (specified in vega JSON file) and visualizes points with lats and lons on a map.

**simple-polygon:** Queries the given index for documents with "geojson" field and displays the given GeoShape. Not a ton of flexibility ATM for various other GeoJSON types, filtering, etc. 

**boresight:** Uses GeoJSON to query for `boresight_ellipse` and `boresight_centroid` fields, and displays the polygons/points contained in those fields.


**Visualization-Making Note:** Each visualization may or may not have different formats for how to structure the GeoJSON data. Refer to the data directories for each visualization. We are hoping to unify the data format once we have JSON templates. To add more data by-hand from a file to an ES index, run:
`curl -XPOST -H 'Content-Type: application/json' localhost:9200/{INDEX_NAME}/_doc -d @{FULL_PATH_TO_FILE}`


Most of the visualizations have helper scripts (like `addPolysToEs.sh` in simple-polygon) that allow you to copy data from your local machine to EC2, and then post all of those files to ElasticSearch. *Be careful with this, because it will overwrite files on the EC2 that shares the same name.*
