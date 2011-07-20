#include <cstdio>
#include <cstring>
#include <cctype>

#include <string>
#include <vector>

#include <libxml/encoding.h>
#include <libxml/tree.h>
#include <libxml/parser.h>

const char * xml = "<?xml version=\"1.0\" encoding=\"utf-8\">\n";
const char * appPrefix = "<s:Application xmlns:fx=\"http://ns.adobe.com/mxml/2009\"\n\t\
xmlns:s=\"library://ns.adobe.com/flex/spark\"\n\t\
xmlns:mx=\"library://ns.adobe.com/flex/mx\"\n\t";
const char * appSuffix = "creationComplete=\"init()\">";

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
xmlNode * find_first_child(xmlNode *parent, const char* name) {
	xmlNode *child = parent->children;
	while (child) {
		if (child->type == XML_ELEMENT_NODE && strcmp(
				(const char *) (child->name), name) == 0)
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
xmlNode * find_next_child(xmlNode *cur, const char* name) {
	xmlNode *next = cur->next;
	while (next) {
		if (next->type == XML_ELEMENT_NODE && strcmp(
				(const char *) (next->name), name) == 0)
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
const char * find_prop_value(xmlNode *node, const char* pname) {
	xmlAttr *propIter = node->properties;
	for (; propIter; propIter = propIter->next) {
		if (strcmp((const char *) (propIter->name), pname) == 0)
			return (const char *) (propIter->children->content);
	}
	return 0;
}

/**
 * find the "value" value corresponding to a particular "name" value in an attribute tag in a node
 * e.g., given the following node:
 * <node>
 *   <attribute name="text" value="5" />
 * </node>
 * find_att_value(node, "text") will return "5"
 */
const char * find_att_value(xmlNode* node, const char *name) {
	xmlNode *attIter = find_first_child(node, "attribute");
	for (; attIter; attIter = find_next_child(attIter, "attribute")) {
		if (strcmp(find_prop_value(attIter, "name"), name) == 0)
			return find_prop_value(attIter, "value");
	}
	return 0;
}

typedef struct {
	char * name;
	char * value;
} Attribute;

typedef struct {
	vector<Attribute *> entries;
} AttTable;

typedef struct {
	char * datatype;
	char * name;
	int length;
	AttTable * attTable;
} Axis;

typedef struct {
	char * datatype;
	char * name;
	AttTable * attTable;
} Variable;

typedef struct {
	char *name;
	vector<Variable *> vars;
	vector<Axis *> axes;
} Dataset;

/**
 * parse the header file (header.xml) to get information about datasets
 */
void parse_header(const char *header_filename, vector<Dataset*> &datasets) {
	xmlDoc *doc = xmlReadFile(header_filename, NULL, 0);
	xmlNode *root_element = xmlDocGetRootElement(doc); // <data>

	// First build a hash that maps the axes names to the axes,
	// and a hash that maps the names to the lengths
	map<char *, Axis*> name_axis;
	xmlNode *axisIter = find_first_child(
			find_first_child(root_element, "axes"), "axis"); // <data> -> <axes> -> <axis>
	for (; axisIter; axisIter = find_next_child(axisIter, "axis")) {
		Axis *axis = new Axis;
		axis->name = find_prop_value(axisIter, "name");
		axis->datatype = find_att_value(axisIter, "infile_datatype");
		axis->length = atoi(find_att_value(axisIter, "length"));

		// fill out the attribute table
		axis->attTable = new AttTable;
		table->entries = new vector<Attribute*>;
		xmlNode *attIter = find_first_child(axisIter, "attribute");
		for (; attIter; attIter = find_next_child(attIter, "attribute")) {
			Attribute *att = new Attribute;
			att->name = find_prop_value(attIter, "name");
			att->value = find_prop_value(attIter, "value");
			table->entries.push_back(att);
		}
		name_axis[axis->name] = axis;
	}

	// Find non-coordinate data
	xmlNode *datasetIter = find_first_child(
			find_first_child(root_element, "datasets"), "dataset"); // <data> -> <datasets> -> <dataset>
	for (; datasetIter; datasetIter = find_next_child(datasetIter, "dataset")) {
		const char * dsname = find_prop_value(datasetIter, "name");
		Dataset *dds = new Dataset;
		dds->name = dsname;

		xmlNode *varIter = find_first_child(datasetIter, "var");
		for (; varIter; varIter = find_next_child(varIter, "var")) {
			Variable *var = new Variable;
			var->name = find_prop_value(varIter, "name");
			var->datatype = find_att_value(varIter, "infile_datatype");

			// fill out the attribute table
			var->attTable = new AttTable;
			var->attTable.entries = new vector<Attribute*>;
			xmlNode *attIter = find_first_child(varIter, "attribute");
			for (; attIter; attIter = find_next_child(attIter, "attribute")) {
				Attribute *att = new Attribute;
				att->name = find_prop_value(attIter, "name");
				att->value = find_prop_value(attIter, "value");
			}
			Array a(name, bt);
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
				var.add_var(name_axis[axisName], maps);
				a.append_dim(axis_length[axisName], axisName);
			}
			at.append_attr("direction", "String", direction);
			at.append_attr("dataset", "String", dsname);
			var.add_var(&a, array);
			var.set_read_p(true);
			dds->add_var(&var);
		}
		ddses.push_back(dds);
	}

	// Find global data
	xmlNode *global = find_first_child(root_element, "global"); // <data> -> <global>
	DataDDS *dds = new DataDDS(NULL, "global"); // I name this dataset "global"
	xmlNode *varIter = find_first_child(global, "var");
	for (; varIter; varIter = find_next_child(varIter, "var")) {
		const char * name = find_prop_value(varIter, "name");
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
		Grid var((string)t);
		const char * datatype = find_att_value(varIter, "infile_datatype");
		BaseType *bt;
		if (strcmp(datatype, "DOUBLE") == 0) {
			bt = new Float64(name);
		} else if (strcmp(datatype, "FLOAT") == 0) {
			bt = new Float32(name);
		} // TODO correct the code and add more datatypes

		// fill out the attribute table
		AttrTable &at = bt->get_attr_table();
		at.append_attr("dataset", "String", dsname);
		xmlNode *attIter = find_first_child(varIter, "attribute");
		for (; attIter; attIter = find_next_child(attIter, "attribute")) {
			at.append_attr(find_prop_value(attIter, "name"), "String",
					find_prop_value(attIter, "value"));
		}
		Array a(name, bt);
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
			var.add_var(name_axis[axisName], maps);
			a.append_dim(axis_length[axisName], axisName);
		}
		at.append_attr("direction", "String", direction);
		var.add_var(&a, array);
		var.set_read_p(true);
		dds->add_var(&var);
	}
	ddses.push_back(dds);

	// transform the DataDDS into a netcdf file. The dhi only needs the
	// output stream and the post constraint. Test no constraints and
	// then some different constraints (1 var, 2 var)

	// The resulting netcdf file is streamed back. Write this file to a
	// test file locally
	for (int i = 0; i < ddses.size(); i++) {
		DataDDS dds = *ddses[i];
		ofstream fstrm("./header.nc");
		dds.dump(fstrm);
		//		BESResponseObject *obj = new BESDataDDSResponse( dds ) ;
		//		::BESDataHandlerInterface dhi ;
		//		dhi.set_output_stream( &fstrm ) ;
		//		dhi.data[POST_CONSTRAINT] = "" ;
		//		FONcTransmitter ft ;
		//		FONcTransmitter::send_data( obj, dhi ) ;
		//		fstrm.close() ;
		//
		//		// deleting the response object deletes the DataDDS
		//		delete obj ;
	}

	//	for (map<string, string>::iterator it = axis_datasets.begin(); it != axis_datasets.end(); it++) {
	//		cout << it->first << " : " << it->second << endl;
	//	}

	/*free the document */
	xmlFreeDoc(doc);

	/*
	 *Free the global variables that may
	 *have been allocated by the parser.
	 */
	xmlCleanupParser();
}

int main(int argc, char * args[]) { // args[0] is ./preprocess

	// check argument
	if (argc < 2) {
		printf("Missing input description filename\nUsage: ./preprocess <def-file>\n");
		return 1;
	}
	int fnlen;
	if ((fnlen = strlen(args[1])) <= 4) {
		printf("\"%s\" is not a valid XML description file", args[1]);
		return 1;
	}

	char *fn = new char[fnlen+2];
	for (int i = 0; i < fnlen+1; i++) fn[i] = tolower(args[1][i]);
	if (strcmp(fn + (fnlen-4), ".xml") != 0) {
		printf("\"%s\" is not a valid XML description file", args[1]);
		return 1;		
	}

	// TODO: get info from the description file

	// write <s:Application> opening tag
	// use description file name (without extension) as the page title
	*(fn+(fnlen-3)) = 0;
	strcat(fn, "mxml");
	freopen(fn, "w", stdout);
	*(fn+(fnlen-4)) = 0;
	printf("%s%spageTitle=\"%s\"\n\t%s", xml, appPrefix, fn, appSuffix);
	
	// write <fx:Declarations> opening tag
	printf("<fx:Declarations>\n");

	// TODO: fill in declarations according to info fetched from the description file

	//write <fx:Declarations> closing tag
	printf("</fx:Declarations>\n");

	// write <fx:Script> opening tag
	printf("<fx:Script>\n");

	// TODO: fill in scripts according to info fetched from the description file

	// write <fx:Script> closing tag
	printf("</fx:Script>\n");

	// write <fx:Style> element
	// provide a default main.css style sheet, TODO: or use the user-designated style sheet according to an argument
	printf("<fx:Style source=\"main.css\" />");

	// TODO: use <mx:Grid>, <mx:GridRow> and <mx:GridItem> to organize Actions, Data URL, Variables and Axes sections
	
	// write <s:Application> closing tag
	printf("</s:Application>\n");
	fclose(stdout);
	return 0;
}

