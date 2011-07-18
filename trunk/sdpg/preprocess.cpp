#include <cstdio>
#include <cstring>
#include <cctype>

const char * xml = "<?xml version=\"1.0\" encoding=\"utf-8\">\n";
const char * appPrefix = "<s:Application xmlns:fx=\"http://ns.adobe.com/mxml/2009\"\n\t\
xmlns:s=\"library://ns.adobe.com/flex/spark\"\n\t\
xmlns:mx=\"library://ns.adobe.com/flex/mx\"\n\t";
const char * appSuffix = "creationComplete=\"init()\">";

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

