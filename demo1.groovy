def alert = com.eviware.soapui.support.UISupport;
//Define a file pointer for groovy to handle the file operations.
def inputFile = new File("C:\\SOAPUI\\DATASHEET.xml")
if(!inputFile.exists())
{
    //Display an alert if the file is not found.
    alert.showInfoMessage("Input File 'pom.xml' not found!");   
}
else
{
    //Read and parse XML file and store it into a variable
    def InputXML = new XmlParser().parseText(inputFile.text)
    //Find/ Filter XML nodes based on a condition
    def InputRow = InputXML.license.findAll{
        it.text();   
        
    }
    InputRow.each{
         log.info(it.text());
        }
} 
