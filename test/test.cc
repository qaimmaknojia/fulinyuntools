#include <unistd.h>
#include <wait.h>
#include <libxml/encoding.h>
#include <libxml/tree.h>
#include <libxml/parser.h>
#include <libdap/DataDDS.h>
#include <libdap/Array.h>
#include <libdap/BaseType.h>
#include <libdap/Float32.h>
#include <libdap/Float64.h>
#include <libdap/Grid.h>
//#include <bes/BESResponseObject.h>
//#include <bes/BESDataHandlerInterface.h>

#include <cerrno>
#include <iostream>
#include <fstream>
#include <string>
#include <cstdlib>
#include <vector>
#include <cstring>
#include <map>

using namespace libdap;
using std::cout;
using std::vector;
using std::string;
using std::endl;

// ferret -memsize 16 -gif -server -script header.jnl /media/Acer/eclipseWorkspace/ferret721/resources/iosp/temp/C1A77896AA1BE2A945A01665C47FE2A1/ferret_operation_1308332358527.jnl resources/iosp/temp/C1A77896AA1BE2A945A01665C47FE2A1/header.xml
void build_header_args(const string &tempjnl_filename,
		const string &output_filename, vector<string> &args) {
	string FerretCommand = "ferret";
	string FerretMemsize = "16";
	args.push_back(FerretCommand);
	args.push_back("-memsize");
	args.push_back(FerretMemsize);
	args.push_back("-server");
	args.push_back("-gif");
	args.push_back("-script");
	args.push_back("header.jnl"); // may change to something like resources/iosp/scripts/header.jnl
	args.push_back(tempjnl_filename);
	args.push_back(output_filename);
}

/**
 * execute a journal file (XXX.jnl) to get a header file (header.xml)
 */
void run_header(const string &jnl_filename, const string &resp_filename,
		string &type) {
	type = "nc";

	// - want to redirect stderr so that we can get any error messages
	int pip_stderr[2];
	// - response variable
	int r;
	// - fork response
	int pid;

	r = pipe(pip_stderr);
	if (r == -1) {
		cerr << "Failed to create pipe for ferret execution" << endl;
		//		string err = (string)"Failed to create pipe for ferret execution" ;
		//		throw BESInternalError( err, __FILE__, __LINE__ ) ;
	}

	pid = fork();
	if (pid == 0) { // child process
		r = close(pip_stderr[0]);
		if (r == -1) {
			exit(-1);
		}
		r = dup2(pip_stderr[1], STDERR_FILENO);
		if (r == -1) {
			exit(-1);
		}
		close(pip_stderr[1]);
		vector<string> args;
		build_header_args(jnl_filename, resp_filename, args);

		char **cargs = new char*[args.size() + 1];
		int i = 0;
		for (i = 0; i < args.size(); i++) {
			cargs[i] = (char *) args[i].c_str();
		}
		cargs[i] = 0;
		execvp(args[0].c_str(), cargs);

	} else if (pid != -1) { // parent process
		r = close(pip_stderr[1]);
		char readbuffer[1024];
		string ferret_err;
		int nbytes = 0;
		while ((nbytes = read(pip_stderr[0], readbuffer, sizeof(readbuffer)))) {
			ferret_err += readbuffer;
		}

		r = waitpid(pid, 0, 0);
		if (errno != 0) {
			// handle error
			char *err_info = strerror(errno);
			string err = (string) "Failed to execute the ferret request: ";
			if (err_info)
				err += err_info;
			else
				err += "unknown reason";
		}
		if (!ferret_err.empty()) {
			// handle ferret error
			cerr << "ferret error string: " << ferret_err << endl;
			//			FerretUtils::handle_ferret_error( ferret_err ) ;
		}
	} else { // fork failed
		cerr << "fork failed" << endl;
		//		string err = (string)"Failed to execute ferret" ;
		//		throw BESInternalError( err, __FILE__, __LINE__ ) ;
	}

}

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

//void getDatatype(const char *name, const char *datatype, BaseType * ret) {
//	if (strcmp(datatype, "DOUBLE") == 0) {
//		ret = new Float64(name);
//	} else if (strcmp(datatype, "FLOAT") == 0) {
//		ret = new Float32(name);
//	}
//}

/**
 * parse the header file (header.xml) to get DataDDS structures and print them with DataDDS::dump()
 */
void parse_header(const char *header_filename) {
	xmlDoc *doc = xmlReadFile(header_filename, NULL, 0);
	xmlNode *root_element = xmlDocGetRootElement(doc); // <data>

	vector<DataDDS *> ddses;
	// First build a hash that maps the axes names to the axes,
	// and a hash that maps the names to the lengths
	map<string, Array*> name_axis;
	map<string, int> axis_length;
	xmlNode *axisIter = find_first_child(
			find_first_child(root_element, "axes"), "axis"); // <data> -> <axes> -> <axis>
	for (; axisIter; axisIter = find_next_child(axisIter, "axis")) {
		const char *name = find_prop_value(axisIter, "name");
		const char *datatype = find_att_value(axisIter, "infile_datatype");
		int length = atoi(find_att_value(axisIter, "length"));

		BaseType* bt;
		if (strcmp(datatype, "DOUBLE") == 0) {
			bt = new Float64(name);
		} else if (strcmp(datatype, "FLOAT") == 0) {
			bt = new Float32(name);
		} // TODO correct the code and add more datatypes

		// fill out the attribute table
		AttrTable &at = bt->get_attr_table();
		xmlNode *attIter = find_first_child(axisIter, "attribute");
		for (; attIter; attIter = find_next_child(attIter, "attribute")) {
			at.append_attr(find_prop_value(attIter, "name"), "String",
					find_prop_value(attIter, "value"));
		}
		Array* a = new Array(name, bt);
		a->append_dim(length, name);
		name_axis[name] = a;
		axis_length[name] = length;
	}

	// Find non-coordinate data
	xmlNode *datasetIter = find_first_child(
			find_first_child(root_element, "datasets"), "dataset"); // <data> -> <datasets> -> <dataset>
	for (; datasetIter; datasetIter = find_next_child(datasetIter, "dataset")) {
		const char * dsname = find_prop_value(datasetIter, "name");
		DataDDS *dds = new DataDDS(NULL, dsname);

		xmlNode *varIter = find_first_child(datasetIter, "var");
		for (; varIter; varIter = find_next_child(varIter, "var")) {
			const char * name = find_prop_value(varIter, "name");
			Grid *var = new Grid(name);
			const char * datatype = find_att_value(varIter, "infile_datatype");
			BaseType *bt;
			if (strcmp(datatype, "DOUBLE") == 0) {
				bt = new Float64(name);
			} else if (strcmp(datatype, "FLOAT") == 0) {
				bt = new Float32(name);
			} // TODO correct the code and add more datatypes

			// fill out the attribute table
			AttrTable &at = bt->get_attr_table();
			xmlNode *attIter = find_first_child(varIter, "attribute");
			for (; attIter; attIter = find_next_child(attIter, "attribute")) {
				at.append_attr(find_prop_value(attIter, "name"), "String",
						find_prop_value(attIter, "value"));
			}
			Array *a = new Array(name, bt);
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
				var->add_var(name_axis[axisName], maps);
				a->append_dim(axis_length[axisName], axisName);
			}
			at.append_attr("direction", "String", direction);
			at.append_attr("dataset", "String", dsname);
			var->add_var(a, array);
			var->set_read_p(true);
			dds->add_var(var);
		}
		ddses.push_back(dds);
//		freopen("dump.txt", "a", stdout);
//		cout << "******" << endl;
//		dds->dump(cout);
//		fclose(stdout);
	}

	// Find global data
	xmlNode *global = find_first_child(root_element, "global"); // <data> -> <global>
	if (global) {
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
			Grid *var = new Grid((string)t);
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
			Array *a = new Array(name, bt);
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
				var->add_var(name_axis[axisName], maps);
				a->append_dim(axis_length[axisName], axisName);
			}
			at.append_attr("direction", "String", direction);
			var->add_var(a, array);
			var->set_read_p(true);
			dds->add_var(var);
		}
		ddses.push_back(dds);
	}
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

/**
 * called by march_through_xml()
 */
void print_element(xmlNode *anode, int level) {
	for (xmlNode *node = anode; node; node = node->next) {
		//		cout << "node: " << node << endl;
		//		cout << "children: " << node->children << endl;
		for (int i = 0; i < level; i++)
			cout << "  ";
		cout << "name: " << node->name << endl;
		for (int i = 0; i < level; i++)
			cout << "  ";
		cout << "properties: " << endl;
		for (xmlAttr *prop = node->properties; prop; prop = prop->next) {
			for (int i = 0; i < level; i++)
				cout << "  ";
			cout << "  " << prop->name << " : " << prop->children->content
					<< endl;
		}
		//		cout << "children: " << node->children << endl;

		if (node->content) {
			for (int i = 0; i < level; i++)
				cout << "  ";
			cout << "content: " << node->content << endl;
		} else {
			for (int i = 0; i < level; i++)
				cout << "  ";
			cout << "no content" << endl;
		}

		print_element(node->children, level + 1);
	}
}

/**
 * print everything in an xml file in a hierarchical manner, used to help get familiar with the libxml2
 * library
 */
void march_through_xml(const char *xmlfilename) {
	xmlDoc *doc = xmlReadFile(xmlfilename, NULL, 0);
	xmlNode *root_element = xmlDocGetRootElement(doc);
	print_element(root_element, 0);

	/*free the document */
	xmlFreeDoc(doc);

	/*
	 *Free the global variables that may
	 *have been allocated by the parser.
	 */
	xmlCleanupParser();

}

/**
 * driver function, the code cannot run now due to a bunch of link error such as
 * undefined reference to `libdap::Grid::Grid(std::basic_string<char, std::char_traits<char>, std::allocator<char> > const&)'
 */
int main() {
	string type = "nc";
	run_header("dods_demo.jnl", "header.xml", type);
	//	march_through_xml("header.xml");
	parse_header("header.xml");
}
