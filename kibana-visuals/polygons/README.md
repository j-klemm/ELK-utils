Visualize GeoJSON shapes and points in Kibana with Vega


*To get data into ES:*

Assuming you have data `./localdata`, to post data to a remote cluster, modify the IP address in `./addDataToEs` and run it. Or, if you want to add data to an ElasticSearch cluster running on your local machine, run `./postNewData.sh`

*To visualize data:*

It's easiest to copy the Vega JSON file into the visualization in the browser. Go to your Kibana page in the browser -> Visualize tab  -> "+" symbol -> "Vega" -> Paste `polygon-with-centroid-vega.json` file into text box.
