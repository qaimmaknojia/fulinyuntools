#include <unistd.h>
#include <wait.h>
#include <libxml/encoding.h>
#include <libxml/tree.h>
#include <libxml/parser.h>
#include <libdap/DataDDS.h>
#include <libdap/Array.h>
#include <libdap/Float32.h>
#include <libdap/Float64.h>

#include <cerrno>
#include <iostream>
#include <string>
#include <cstdlib>
#include <vector>
#include <cstring>
#include <map>

using namespace std;

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
			string err = (string)"Failed to execute the ferret request: " ;
			if (err_info) err += err_info;
			else err += "unknown reason";
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

xmlNode * find_first_child(xmlNode *parent) {
	xmlNode *child = parent->children;
	while (child) {
		if (child->type == XML_ELEMENT_NODE)
			return child;
		else child = child->next;
	}
	return child;
}

xmlNode * find_first_child(xmlNode *parent, const char* name) {
	xmlNode *child = parent->children;
	while (child) {
		if (child->type == XML_ELEMENT_NODE && strcmp((const char *)(child->name), name) == 0)
			return child;
		else child = child->next;
	}
	return child;
}

xmlNode * find_next_child(xmlNode *cur) {
	xmlNode *next = cur->next;
	while (next) {
		if (next->type == XML_ELEMENT_NODE)
			return next;
		else next = next->next;
	}
	return next;
}

xmlNode * find_next_child(xmlNode *cur, const char* name) {
	xmlNode *next = cur->next;
	while (next) {
		if (next->type == XML_ELEMENT_NODE && strcmp((const char *)(next->name), name) == 0)
			return next;
		else next = next->next;
	}
	return next;
}

const char * find_prop_value(xmlNode *node, const char* pname) {
	xmlAttr *propIter = node->properties;
	for (; propIter; propIter = propIter->next) {
		if (strcmp((const char *)(propIter->name), pname) == 0)
			return (const char *)(propIter->children->content);
	}
	return 0;
}

char * find_att_value(xmlNode* node, const char *name) {
	xmlNode *attIter = find_first_child(node, "attribute");
	for (; attIter; attIter = find_next_child(attIter, "attribute")) {
		if (strcmp(find_prop_value(attIter, "name"), name) == 0)
				return find_prop_value(attIter, "value");
	}
	return null;
}

void parse_header(const char *header_filename) {
	xmlDoc *doc = xmlReadFile(header_filename, NULL, 0);
	xmlNode *root_element = xmlDocGetRootElement(doc); // <data>

	vector<DataDDS *> ddses;
	// First build a hash that maps the axes names to the axes.
	map<string, Array*> axes;
	xmlNode *axisIter = find_first_child(
			find_first_child(root_element, "axes"),
			"axis"); // <data> -> <axes> -> <axis>
	for (; axisIter; axisIter = find_next_child(axisIter, "axis")) {
		char *name = find_prop_value(axisIter, "name");
		char *datatype = find_att_value(axisIter, "infile_datatype");
		int length = atoi(find_att_value(axisIter, "length"));

		if (strcmp(datatype, "DOUBLE") == 0) {
			Float64 bt(name);
			Array a(name, &bt);
			a.append_dim(length, name);
			axes[name]=&a;
		} else if (strcmp(datatype, "FLOAT") == 0) {
			Float32 bt(name);
			Array a(name, &bt);
			a.append_dim(length, name);
			axes[name]=&a;
		}
	}

	// Find Grid, Array and Dimensions
	xmlNode *datasetIter = find_first_child(
			find_first_child(root_element, "datasets"),
			"dataset"); // <data> -> <datasets> -> <dataset>
	for (; datasetIter; datasetIter = find_next_child(datasetIter, "dataset")) {
		char * name = find_prop_value(datasetIter, "name");
		DataDDS *dds = new DataDDS( NULL, name ) ;

		xmlNode *varIter = find_first_child(datasetIter, "var");
		for (; varIter; varIter = find_next_child(varIter, "var")) {
			char * name = find_prop_value(varIter, "name");
			Grid var(name);
			xmlNode *axisIter = find_first_child(
					find_first_child(
							find_first_child(varIter, "grid"),
							"axes")); // <var> -> <grid> -> <axes> -> <[x|y|z|t]axis>
			for (; axisIter; axisIter = find_next_child(axisIter)) {
				axis_datasets[(const char *)(axisIter->children->content)]
				              = find_prop_value(datasetIter, "name");
			}
		}
		ddses.push_back(dds);
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

void print_element(xmlNode *anode, int level) {
	for (xmlNode *node = anode; node; node = node->next) {
//		cout << "node: " << node << endl;
//		cout << "children: " << node->children << endl;
		for (int i = 0; i < level; i++) cout << "  ";
		cout << "name: " << node->name << endl;
		for (int i = 0; i < level; i++) cout << "  ";
		cout << "properties: " << endl;
		for (xmlAttr *prop = node->properties; prop; prop = prop->next) {
			for (int i = 0; i < level; i++) cout << "  ";
			cout << "  " << prop->name << " : " << prop->children->content << endl;
		}
//		cout << "children: " << node->children << endl;

		if (node->content) {
			for (int i = 0; i < level; i++) cout << "  ";
			cout << "content: " << node->content << endl;
		} else {
			for (int i = 0; i < level; i++) cout << "  ";
			cout << "no content" << endl;
		}

		print_element(node->children, level+1);
	}
}

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

int main() {
//	string type = "nc";
//	run_header("dods_demo.jnl", "header.xml", type);
//	march_through_xml("header.xml");
	parse_header("header.xml");
}
