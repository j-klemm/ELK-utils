import groovy.io.FileType
import groovy.json.*
import groovy.cli.commons.CliBuilder

//AUTHOR C.Cohee
//Edited by J.Klemm for lowside repo


List<String> objectTypes = ["visualization", "search", "index-pattern", "dashboard"]
String host = "{CHANGE_ME}" //CHANGE ME to host IP

//CLI options
def cli = new CliBuilder(usage:'main [options] -import | -export', header:'Options:')

cli.import('Flag to import visualizations')
cli.export('Flag to export visualizations')
def options = cli.parse(args)


if(options && (options.import || options.export)) {
    if(options.import)
        importVisualizations(host, objectTypes)

    if(options.export)
        exportVisualizations(host, objectTypes)
}
else {
    println cli.usage()
}


//Import all given object types in objectTypes to cluster at host IP
void importVisualizations(String host, List<String> objectTypes)
{
    objectTypes.each { type ->
        def savedObjectDir = new File("output/${type}")
        savedObjectDir.eachFile(FileType.FILES) { file ->
            println "Importing output/${type}/${file.getName().minus(".json")}"
            def curl = ['bash', '-c', "curl -XPOST -H \"Content-Type: application/json\" -H \"kbn-xsrf: true\" ${host}:5601/api/saved_objects/${type}/${file.getName().minus(".json")} -d @output/${type}/${file.getName()}"].execute()
            curl.waitFor()
        }
    }
}

//Export all object types in objectTypes to the given host
void exportVisualizations(String host, List<String> objectTypes) {
    objectTypes.each { type ->
        createOutputDir(type)

        List<String> idList = collectIds(host, type)

        idList.each { id ->
            String name = id.minus("${type}:")
            def curl = "curl -XGET ${host}:5601/api/saved_objects/${type}/${name}".execute()
            curl.waitFor()

            def jsonResults = new JsonSlurper().parseText(curl.text)   // Convert to map to get 'attributes' payload (needed for importing)

            // Remove all but 'attributes' from results
            def jsonAttributes = new JsonBuilder()
            jsonAttributes {
                attributes jsonResults.attributes
            }

            println "Exporting output/${type}/${name}.json"
            new File("output/${type}/${name}.json").write(JsonOutput.prettyPrint(jsonAttributes.toString()))
        }
    }
}

//Get IDs of saved objects as list
private List<String> collectIds(String host, String type) {
    println "Collecting IDs for ${type}."

    def curl = "curl -XGET ${host}:9200/.kibana/_search?q=type:${type}&size=100&_source=_id&pretty=true".execute()
    curl.waitFor()

    def json = new JsonSlurper().parseText(curl.text)
    List<String> idList = []

    json.hits.hits.each { results ->
        idList.add(results._id)
    }

    idList
}

private void createOutputDir(String type)
{
    def exportDir = new File("output")
    if(!exportDir.exists() ) {
        println "Output directory does not exist. Creating directory."
        exportDir.mkdir()
    }

    def savedObjectDir = new File("output/${type}")
    if(!savedObjectDir.exists() ) {
        println "${type} directory does not exist. Creating directory."
        savedObjectDir.mkdir()
    }
    else {
        println "Removing all files from ${type} directory."
        savedObjectDir.eachFile(FileType.FILES){ file ->
            file.delete()
        }
    }
}

