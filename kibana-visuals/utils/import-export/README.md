# Import/Export Visualizations

This directory contains Groovy scripts for importing and exporting saved objects from Kibana. 

The script can be executed with the desired option appended: 

`groovy src/main/groovy/main.groovy [-import | -export]`

At least one tag must be specified: 

- _-export_: All saved objects from Kibana are exported as code and saved locally in the _output/_ directory
- _-import_: All saved objects saved to the local _output/_ directory are uploaded to Kibana

If it does not already exist, the _output/_ directory is created dynamically whenever exporting saved objects. Each saved object type (dashboard, visualization, search, index-pattern...) is saved under its own directory inside _output/_. 


AUTHOR C.Cohee, edited by J.Klemm for lowside repo
