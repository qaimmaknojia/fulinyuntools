#include <cstdio>
#include <cstring>
#include <cctype>
#include <cstdlib>

#include <string>
#include <vector>
#include <map>
#include <iostream>
#include <sstream>

#include <libxml/encoding.h>
#include <libxml/tree.h>
#include <libxml/parser.h>

using namespace std;

string xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
string appPrefix = "<s:Application xmlns:fx=\"http://ns.adobe.com/mxml/2009\"\n\t\
xmlns:s=\"library://ns.adobe.com/flex/spark\"\n\t\
xmlns:mx=\"library://ns.adobe.com/flex/mx\"\n\t";
string appSuffix = "creationComplete=\"init()\">";
string helpPage = "http://www.opendap.org/online_help_files/opendap_form_help.html";


/**
 * find the first child node of a node
 */
xmlNode * find_first_child(xmlNode *parent) {
	xmlNode *child = parent->children;
	while (child) {
		if (child->type == XML_ELEMENT_NODE)
			return child;
		else
			child = child->next;
	}
	return child;
}

/**
 * find the first child node of a node with a particular name
 */
xmlNode * find_first_child(xmlNode *parent, string name) {
	xmlNode *child = parent->children;
	while (child) {
		if (child->type == XML_ELEMENT_NODE && (string)(const char *)child->name == name)
			return child;
		else
			child = child->next;
	}
	return child;
}

/**
 * find the next node at the same level
 */
xmlNode * find_next_child(xmlNode *cur) {
	xmlNode *next = cur->next;
	while (next) {
		if (next->type == XML_ELEMENT_NODE)
			return next;
		else
			next = next->next;
	}
	return next;
}

/**
 * find the next node with a particular name at the same level
 */
xmlNode * find_next_child(xmlNode *cur, string name) {
	xmlNode *next = cur->next;
	while (next) {
		if (next->type == XML_ELEMENT_NODE && (string)(const char *)(next->name) == name)
			return next;
		else
			next = next->next;
	}
	return next;
}

/**
 * find the value of a property of a node given the name of the property
 * e.g., given the following node:
 * <node name="sst">
 *   ...
 * </node>
 * find_prop_value(node, "name") will return "sst"
 */
string find_prop_value(xmlNode *node, string pname) {
	xmlAttr *propIter = node->properties;
	for (; propIter; propIter = propIter->next) {
		if ((string)(const char *)(propIter->name) == pname)
			return (string)(const char *)(propIter->children->content);
	}
	return "";
}

/**
 * find the "value" value corresponding to a particular "name" value in an attribute tag in a node
 * e.g., given the following node:
 * <node>
 *   <attribute name="text" value="5" />
 * </node>
 * find_att_value(node, "text") will return "5"
 */
string find_att_value(xmlNode* node, string name) {
	xmlNode *attIter = find_first_child(node, "attribute");
	for (; attIter; attIter = find_next_child(attIter, "attribute")) {
		if (find_prop_value(attIter, "name") == name)
			return find_prop_value(attIter, "value");
	}
	return "";
}

typedef struct {
	string datatype;
	string name;
	int length;
	map<string, string> attTable;
	int id;
} Axis;

typedef struct {
	string datatype;
	string name;
	map<string, string> attTable;
	vector<Axis*> axes;
	int id;
} Variable;

typedef struct {
	string name;
	vector<Variable *> vars;
	vector<Axis *> axes;
} Dataset;

/**
 * parse the header file (header.xml) to get information about datasets
 */
void parse_header(const char *header_filename, vector<Dataset*> &datasets) {
	xmlDoc *doc = xmlReadFile(header_filename, NULL, 0);
	xmlNode *root_element = xmlDocGetRootElement(doc); // <data>
	int id = 0;

	// First build a hash that maps the axes names to the axes,
	// and a hash that maps the names to the lengths
	map<string, Axis*> name_axis;
	vector<Axis*> axes;
	xmlNode *axisIter = find_first_child(
			find_first_child(root_element, "axes"), "axis"); // <data> -> <axes> -> <axis>
	for (; axisIter; axisIter = find_next_child(axisIter, "axis")) {
		Axis *axis = new Axis;
		axis->id = id++;
		axis->name = find_prop_value(axisIter, "name");
		axis->datatype = find_att_value(axisIter, "infile_datatype");
		axis->length = atoi(find_att_value(axisIter, "length").c_str());

		// fill out the attribute table
		xmlNode *attIter = find_first_child(axisIter, "attribute");
		for (; attIter; attIter = find_next_child(attIter, "attribute")) {
			axis->attTable[find_prop_value(attIter, "name")] = find_prop_value(attIter, "value");
		}
		name_axis[axis->name] = axis;
		axes.push_back(axis);
	}

	// Find non-coordinate data
	xmlNode *datasetIter = find_first_child(
			find_first_child(root_element, "datasets"), "dataset"); // <data> -> <datasets> -> <dataset>
	for (; datasetIter; datasetIter = find_next_child(datasetIter, "dataset")) {
		string dsname = find_prop_value(datasetIter, "name");
		Dataset *dds = new Dataset;
		dds->name = dsname;
		dds->axes = axes;
		xmlNode *varIter = find_first_child(datasetIter, "var");
		for (; varIter; varIter = find_next_child(varIter, "var")) {
			Variable *var = new Variable;
			var->id = id++;
			var->name = find_prop_value(varIter, "name");
			var->datatype = find_att_value(varIter, "infile_datatype");

			// fill out the attribute table
			xmlNode *attIter = find_first_child(varIter, "attribute");
			for (; attIter; attIter = find_next_child(attIter, "attribute")) {
				var->attTable[find_prop_value(attIter, "name")] = find_prop_value(attIter, "value");
			}
			string direction = "";
			xmlNode *axisIter =
					find_first_child(
							find_first_child(find_first_child(varIter, "grid"),
									"axes")); // <var> -> <grid> -> <axes> -> <[x|y|z|t]axis>
			for (; axisIter; axisIter = find_next_child(axisIter)) {
				if (strcmp((char *)axisIter->name, "xaxis")==0) direction += "I";
				else if (strcmp((char *)axisIter->name, "yaxis")==0) direction += "J";
				else if (strcmp((char *)axisIter->name, "zaxis")==0) direction += "K";
				else if (strcmp((char *)axisIter->name, "taxis")==0) direction += "L";
				char * axisName = (char *) axisIter->children->content;
				var->axes.push_back(name_axis[axisName]);
			}
			var->attTable["direction"] = direction;
			var->attTable["dataset"] = dsname;
			dds->vars.push_back(var);
		}
		datasets.push_back(dds);
	}

	// Find global data
	xmlNode *global = find_first_child(root_element, "global"); // <data> -> <global>
	if (global) {
		Dataset *dds = new Dataset;
		dds->name = "global"; // I name this dataset "global"

		xmlNode *varIter = find_first_child(global, "var");
		for (; varIter; varIter = find_next_child(varIter, "var")) {
			const char * name = find_prop_value(varIter, "name").c_str();
			char *t = new char[strlen(name)];
			strcpy(t, name);
			char *c = t;
			for (; *c != 0 && *c != ']'; c++);
			*c = 0;
			c = t;
			for (; *c != 0 && *c != ','; c++);
			*c = 0;
			c = t;
			char * dsname = 0;
			for (; *c != 0 && *(c+1) != 0 && *(c+2) != 0; c++) {
				if (strncmp(c, "[D=", 3) == 0 || strncmp(c, "[d=", 3) == 0) {
					dsname = new char[strlen(c+3)];
					strcpy(dsname, c+3);
					break;
				}
			}
			if (dsname == 0) {
				dsname = new char[2];
				*dsname = '1';
				dsname[1] = 0;
			}

			// get rid of [d="dataset"] in the variable name
			strcpy(t, name);
			for (c = t; *c != 0 && *(c+1) != 0 && *(c+2) != 0; c++) {
				if (strncmp(c, "[D=", 3) == 0 || strncmp(c, "[d=", 3) == 0) {
					*c = 0;
					break;
				}
			}
			Variable* var = new Variable;
			var->id = id++;
			var->name = t;
			var->datatype = find_att_value(varIter, "infile_datatype");

			// fill out the attribute table
			var->attTable["dataset"] = dsname;
			xmlNode *attIter = find_first_child(varIter, "attribute");
			for (; attIter; attIter = find_next_child(attIter, "attribute")) {
				var->attTable[find_prop_value(attIter, "name")] = find_prop_value(attIter, "value");
			}
			string direction = "";
			xmlNode *axisIter =
					find_first_child(
							find_first_child(find_first_child(varIter, "grid"),
									"axes")); // <var> -> <grid> -> <axes> -> <[x|y|z|t]axis>
			for (; axisIter; axisIter = find_next_child(axisIter)) {
				if (strcmp((char *)axisIter->name, "xaxis")==0) direction += "I";
				else if (strcmp((char *)axisIter->name, "yaxis")==0) direction += "J";
				else if (strcmp((char *)axisIter->name, "zaxis")==0) direction += "K";
				else if (strcmp((char *)axisIter->name, "taxis")==0) direction += "L";
				char * axisName = (char *) axisIter->children->content;
				var->axes.push_back(name_axis[axisName]);
			}
			var->attTable["direction"] = direction;
			dds->vars.push_back(var);
		}
		datasets.push_back(dds);
	}

	/*free the document */
	xmlFreeDoc(doc);

	/*Free the global variables that may have been allocated by the parser. */
	xmlCleanupParser();
}
/*
string toLowercase(string s) {
	char * ret = new char[s.length()+1];
	for (int i = 0; i < s.length(); i++) ret[i] = tolower(s[i]);
	ret[s.length()] = 0;
	return (string)ret;
}
*/
int main(int argc, char * args[]) { // args[0] is ./preprocess

	// check argument
	if (argc < 2) {
		cout << "Missing input description filename\nUsage: ./preprocess <def-file>" << endl;
		return 1;
	}
	int fnlen;
	if ((fnlen = strlen(args[1])) <= 4) {
		cout << "\"" << args[1] << "\" is not a valid XML description file" << endl;
		return 1;
	}

	char *fn = new char[fnlen+2];
	for (int i = 0; i < fnlen+1; i++) fn[i] = tolower(args[1][i]);
	if (strcmp(fn + (fnlen-4), ".xml") != 0) {
		cout << "\"" << args[1] << "\" is not a valid XML description file" << endl;
		return 1;		
	}

	// get info from the description file
	vector<Dataset*> datasets;
	parse_header(args[1], datasets);

	// write <s:Application> opening tag
	// use description file name (without extension) as the page title
	*(fn+(fnlen-3)) = 0;
	strcat(fn, "mxml");
	freopen(fn, "w", stdout);
	*(fn+(fnlen-4)) = 0;
	cout << xml << appPrefix << "pageTitle=\"" << fn << "\"" << endl
			<< "\t" << appSuffix << endl;
	
	// write <fx:Declarations> opening tag
	cout << "<fx:Declarations>" << endl;

	// TODO: fill in declarations according to info fetched from the description file

	//write <fx:Declarations> closing tag
	cout << "</fx:Declarations>" << endl;

	// write <fx:Script> opening tag
	cout << "<fx:Script><![CDATA[" << endl;

/*
private var baseurl:String = "http://test.opendap.org/opendap/data/nc/fnoc1.nc";
private var exturl:String = "";

[Bindable]
private var dataurl:String = baseurl+exturl;

private function init(): void {}
*/
	cout << "\tprivate var baseurl:String = \"" << datasets[0]->name << "\";" << endl;
	cout << "\tprivate var exturl:String = \"\";" << endl;
	cout << "\t[Bindable]" << endl;
	cout << "\tprivate var dataurl:String = baseurl+exturl;" << endl;
	cout << "\tprivate function init(): void {}" << endl;
/*
private function updateDataurl(): void {
	exturl = "";
	var first:Boolean = true;
	if (id0.selected) {
		if (first) {
			exturl += "?";
			first = false;
		} else exturl += ",";
		exturl += "u"+"["+id0_2.text+"]"+"["+id0_1.text+"]"+"["+id0_0.text+"]";
	}
	dataurl = baseurl+exturl;
}
*/
	cout << "\tprivate function updateDataurl(): void {" << endl;
	cout << "\t\texturl = \"\";" << endl;
	cout << "\t\tvar first:Boolean = true;" << endl;
	vector<Variable*>::iterator it = datasets[0]->vars.begin();
	for (; it != datasets[0]->vars.end(); it++) {
		cout << "\t\tif (id" << (*it)->id << ".selected) {" << endl;
		cout << "\t\t\tif (first) {" << endl;
		cout << "\t\t\t\texturl += \"?\";" << endl;
		cout << "\t\t\t\tfirst = false;" << endl;
		cout << "\t\t\t} else exturl += \",\";" << endl;
		cout << "\t\t\texturl += \"" << (*it)->name << "\"";
		for (int i = (*it)->axes.size()-1; i >= 0; i--)
			cout << "+\"[\"+id" << (*it)->id << "_" << i << ".text+\"]\"";
		cout << ";" << endl;
		cout << "\t\t}" << endl;
	}
	vector<Axis*>::iterator ita = datasets[0]->axes.begin();
	for (; ita != datasets[0]->axes.end(); ita++) {
		cout << "\t\tif (id" << (*ita)->id << ".selected) {" << endl;
		cout << "\t\t\t if (first) {" << endl;
		cout << "\t\t\t\texturl += \"?\";" << endl;
		cout << "\t\t\t\tfirst = false;" << endl;
		cout << "\t\t\t} else exturl += \",\";" << endl;
		cout << "\t\t\t\texturl += \"" << (*ita)->name << "\"";
		cout << "+\"[\"+id" << (*ita)->id << "_0.text+\"]\";" << endl;
		cout << "\t\t}" << endl;
	}
	cout << "\t\tdataurl = baseurl+exturl;" << endl;
	cout << "\t}" << endl;

/*
private function getAscii(): void {
	var url:String = baseurl+".ascii"+exturl;
	navigateToURL(new URLRequest(url));
}
*/
	cout << "\tprivate function getAscii(): void {" << endl;
	cout << "\t\tvar url:String = baseurl+\".ascii\"+exturl;" << endl;
	cout << "\t\tnavigateToURL(new URLRequest(url));" << endl;
	cout << "\t}" << endl;

/*
private function getNc(): void {
	var url:String = baseurl+exturl;
	navigateToURL(new URLRequest(url));
}
*/
	cout << "\tprivate function getNc(): void {" << endl;
	cout << "\t\tvar url:String = baseurl+exturl;" << endl;
	cout << "\t\tnavigateToURL(new URLRequest(url));" << endl;
	cout << "\t}" << endl;

/*
private function getDap(): void {
	var url:String = baseurl+".dods"+exturl;
	navigateToURL(new URLRequest(url));
}
*/
	cout << "\tprivate function getDap(): void {" << endl;
	cout << "\t\tvar url:String = baseurl+\".dods\"+exturl;" << endl;
	cout << "\t\tnavigateToURL(new URLRequest(url));" << endl;
	cout << "\t}" << endl;

	// write <fx:Script> closing tag
	cout << "]]></fx:Script>" << endl;

	// write <fx:Style> element
	// provide a default main.css style sheet, TODO: or use the user-designated style sheet according to an argument
	cout << "<fx:Style source=\"main.css\" />" << endl;

	// use <mx:Grid>, <mx:GridRow> and <mx:GridItem> to organize Actions, Data URL, Variables and Axes sections
	cout << "<s:Scroller height=\"100%\">" << endl;
	cout << "<s:VGroup paddingLeft=\"100\" paddingRight=\"100\" paddingTop=\"20\">" << endl;
	cout << "<mx:Grid>" << endl;

	// actions
	cout << "\t<mx:GridRow>" << endl;
	cout << "\t\t<mx:GridItem horizontalAlign=\"right\" >" << endl;
/*
			<mx:Text fontSize="20" color="blue" fontWeight="bold">
				<mx:htmlText>
					<![CDATA[<a href="http://www.opendap.org/online_help_files/opendap_form_help.html">Action:</a>]]>
				</mx:htmlText>
			</mx:Text>
*/
	cout << "\t\t\t<mx:Text fontSize=\"20\" color=\"blue\" fontWeight=\"bold\">" << endl;
	cout << "\t\t\t\t<mx:htmlText>" << endl;
	cout << "\t\t\t\t\t<![CDATA[<a href=\"" << helpPage << "\">Action:</a>]]>" << endl;
	cout << "\t\t\t\t</mx:htmlText>" << endl;
	cout << "\t\t\t</mx:Text>" << endl;
	cout << "\t\t</mx:GridItem>" << endl;

	cout << "\t\t<mx:GridItem>" << endl;
	cout << "\t\t\t<s:HGroup>" << endl;
//	<s:Button label="Get ASCII" click="getAscii();" />
	cout << "\t\t\t\t<s:Button label=\"Get ASCII\" "
			"click=\"getAscii();\" />" << endl;
//	<s:Button label="Get as NetCDF" click="getNc();" />
	cout << "\t\t\t\t<s:Button label=\"Get as NetCDF\" "
			"click=\"getNc();\" />" << endl;
//	<s:Button label="Binary (DAP) Object" click="getDap();" />
	cout << "\t\t\t\t<s:Button label=\"Binary (DAP) Object\" "
			"click=\"getDap();\" />" << endl;
//	<s:Button label="Show Help" click="navigateToURL(new URLRequest('http://www.opendap.org/online_help_files/opendap_form_help.html'));" />
	cout << "\t\t\t\t<s:Button label=\"Show Help\" "
			"click=\"navigateToURL(new URLRequest('" << helpPage << "'));\" />" << endl;
	cout << "\t\t\t</s:HGroup>" << endl;
	cout << "\t\t</mx:GridItem>" << endl;
	cout << "\t</mx:GridRow>" << endl;

	cout << "\t<mx:GridRow>" << endl;
	cout << "\t\t<mx:GridItem horizontalAlign=\"right\">" << endl;
	cout << "\t\t\t<mx:Text fontSize=\"20\" color=\"blue\" fontWeight=\"bold\">" << endl;
	cout << "\t\t\t\t<mx:htmlText>" << endl;
	cout << "\t\t\t\t\t<![CDATA[<a href=\"" << helpPage << "\">Data URL:</a>]]>" << endl;
	cout << "\t\t\t\t</mx:htmlText>" << endl;
	cout << "\t\t\t</mx:Text>" << endl;
	cout << "\t\t</mx:GridItem>" << endl;
	cout << "\t\t<mx:GridItem>" << endl;
	cout << "\t\t\t<s:TextInput text=\"{dataurl}\" width=\"100\%\"/>" << endl;
	cout << "\t\t</mx:GridItem>" << endl;
	cout << "\t</mx:GridRow>" << endl;

	// global attributes
	cout << "\t<mx:GridRow>" << endl;
	cout << "\t\t<mx:GridItem horizontalAlign=\"right\">" << endl;
	cout << "\t\t\t<mx:Text fontSize=\"20\" color=\"blue\" fontWeight=\"bold\">" << endl;
	cout << "\t\t\t\t<mx:htmlText>" << endl;
	cout << "\t\t\t\t\t<![CDATA[<a href=\"" << helpPage << "\">Global Attribute(s):</a>]]>" << endl;
	cout << "\t\t\t\t</mx:htmlText>" << endl;
	cout << "\t\t\t</mx:Text>" << endl;
	cout << "\t\t</mx:GridItem>" << endl;

	cout << "\t\t<mx:GridItem>" << endl;
	cout << "\t\t\t<mx:Text width=\"100\%\" text=\"no global attributes\"/>" << endl; // TODO: what are global attributes?
	cout << "\t\t</mx:GridItem>" << endl;
	cout << "\t</mx:GridRow>" << endl;

	// variables
	cout << "\t<mx:GridRow>" << endl;
	cout << "\t\t<mx:GridItem horizontalAlign=\"right\">" << endl;
	cout << "\t\t\t<mx:Text fontSize=\"20\" color=\"blue\" fontWeight=\"bold\">" << endl;
	cout << "\t\t\t\t<mx:htmlText>" << endl;
	cout << "\t\t\t\t\t<![CDATA[<a href=\"" << helpPage << "\">Variable(s):</a>]]>" << endl;
	cout << "\t\t\t\t</mx:htmlText>" << endl;
	cout << "\t\t\t</mx:Text>" << endl;
	cout << "\t\t</mx:GridItem>" << endl;

	cout << "\t\t<mx:GridItem>" << endl;
	cout << "\t\t\t<s:VGroup width=\"100\%\">" << endl;
	for (it = datasets[0]->vars.begin(); it != datasets[0]->vars.end(); it++) {
		// <s:CheckBox click="updateDataurl();" id="idX" label="val: Array of Bytes [lat = 0..511] [lon = 0..511]" />
		cout << "\t\t\t\t<s:CheckBox click=\"updateDataurl();\" id=\"id" << (*it)->id
				<< "\" label=\"" << ((*it)->name) << ": Array of " << ((*it)->datatype);
		vector<Axis *>::iterator ita;
		for (ita = (*it)->axes.begin(); ita != (*it)->axes.end(); ita++) {
			// [lat = 0..511]
			cout << " [" << ((*ita)->name) << " = 0.." << ((*ita)->length-1) << "]";
		}
		cout << "\" />" << endl;
		cout << "\t\t\t\t<s:HGroup width=\"100\%\">" << endl;
		int count = 0;
		for (ita = (*it)->axes.begin(); ita != (*it)->axes.end(); ita++) {
			// <s:Label text="lat:" />
			// <s:TextInput width="50%" text="{id0.selected ? '0:1:20' : ''}" change="updateDataurl();" id="id0_1"/>
			cout << "\t\t\t\t\t<s:Label text=\"" << ((*ita)->name) << ":\" />" << endl;
			cout << "\t\t\t\t\t<s:TextInput width=\"" << (100/((*it)->axes.size()))
					<< "\%\" text=\"{id" << (*it)->id << ".selected ? '0:1:" << ((*ita)->length-1)
					<< "' : ''}\" change=\"updateDataurl();\" id=\"id" << (*it)->id << "_" << (count++) << "\" />" << endl;
		}
		cout << "\t\t\t\t</s:HGroup>" << endl;
		string attString = "";
		map<string, string>::iterator itm;
		for (itm = (*it)->attTable.begin(); itm != (*it)->attTable.end(); itm++) {
			string name = itm->first;
			string value = itm->second;
			for (int i = 0; i < name.length(); i++) attString += (name[i] == '\"' ? '\0' : name[i]);
			attString += ": ";
			for (int i = 0; i < value.length(); i++) attString += (value[i] == '\"' ? '\0' : value[i]);
			attString += "{'\\n'}";
		}
		cout << "\t\t\t\t<mx:Text width=\"100\%\" text=\"" << attString << "\" />" << endl;
	}
	cout << "\t\t\t</s:VGroup>" << endl;
	cout << "\t\t</mx:GridItem>" << endl;
	cout << "\t</mx:GridRow>" << endl;

	// axes
	cout << "\t<mx:GridRow>" << endl;
	cout << "\t\t<mx:GridItem horizontalAlign=\"right\">" << endl;
	cout << "\t\t\t<mx:Text fontSize=\"20\" color=\"blue\" fontWeight=\"bold\">" << endl;
	cout << "\t\t\t\t<mx:htmlText>" << endl;
	cout << "\t\t\t\t\t<![CDATA[<a href=\"" << helpPage << "\">Axes:</a>]]>" << endl;
	cout << "\t\t\t\t</mx:htmlText>" << endl;
	cout << "\t\t\t</mx:Text>" << endl;
	cout << "\t\t</mx:GridItem>" << endl;

	cout << "\t\t<mx:GridItem>" << endl;
	cout << "\t\t\t<s:VGroup width=\"100\%\">" << endl;
	for (ita = datasets[0]->axes.begin(); ita != datasets[0]->axes.end(); ita++) {
		// <s:CheckBox click="updateDataurl();" id="id0" label="val: Array of Bytes [lat = 0..511] [lon = 0..511]" />
		cout << "\t\t\t\t<s:CheckBox click=\"updateDataurl();\" id=\"id" << (*ita)->id << "\" label=\""
				<< ((*ita)->name) << ": Array of " << ((*ita)->datatype);
		// [lat = 0..511]
		cout << " [" << ((*ita)->name) << " = 0.." << ((*ita)->length-1) << "]";
		cout << "\" />" << endl;
		cout << "\t\t\t\t<s:HGroup width=\"100\%\">" << endl;
		// <s:Label text="lat:" />
		// <s:TextInput id="id0_0" width="100%" text="{id0.selected ? '0:1:10' : ''}" change="updateDataurl();" />
		cout << "\t\t\t\t\t<s:Label text=\"" << ((*ita)->name) << ":\" />" << endl;
		cout << "\t\t\t\t\t<s:TextInput id=\"id" << (*ita)->id << "_0\" width=\"100\%\" text=\"{id" << (*ita)->id << ".selected ? '0:1:"
				<< ((*ita)->length-1) << "' : ''}\" change=\"updateDataurl();\" />" << endl;
		cout << "\t\t\t\t</s:HGroup>" << endl;

		string attString = "";
		map<string, string>::iterator itm;
		for (itm = (*ita)->attTable.begin(); itm != (*ita)->attTable.end(); itm++) {
			string name = itm->first;
			string value = itm->second;
			for (int i = 0; i < name.length(); i++) attString += (name[i] == '\"' ? '\0' : name[i]);
			attString += ": ";
			for (int i = 0; i < value.length(); i++) attString += (value[i] == '\"' ? '\0' : value[i]);
			attString += "{'\\n'}";
		}
		cout << "\t\t\t\t<mx:Text width=\"100\%\" text=\"" << attString << "\" />" << endl;
	}
	cout << "\t\t\t</s:VGroup>" << endl;
	cout << "\t\t</mx:GridItem>" << endl;
	cout << "\t</mx:GridRow>" << endl;

	cout << "</mx:Grid>" << endl;
	cout << "</s:VGroup>" << endl;
	cout << "</s:Scroller>" << endl;
	// write <s:Application> closing tag
	cout << "</s:Application>" << endl;
	fclose(stdout);
	return 0;
}

