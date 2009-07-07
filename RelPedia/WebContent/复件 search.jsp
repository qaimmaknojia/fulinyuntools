<%@ page import="org.apexlab.sw.relationbrowser.relationbrowser.RelationInfoRetriever" %>
<%@ page import="org.apexlab.sw.relationbrowser.relationbrowser.StringDoublePair" %>
<%@ page import="org.apexlab.sw.relationbrowser.relationbrowser.RelationLinkFinder" %>
<%@ page import="org.apexlab.sw.relationbrowser.relationbrowser.RelationInstanceFinder" %>
<%@ page import="org.apexlab.sw.relationbrowser.relationbrowser.Translator" %>
<%@ page import="org.apexlab.sw.relationbrowser.relationbrowser.SubObjNum" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DBpedia Relation Browser</title>
<style type="text/css">
	a:link { text-decoration: none; color: #00f; }
	a:visited { text-decoration: none; color: #639; }
	a:hover { text-decoration: underline; }
	a:active { text-decoration: underline; }
	.mouseOver {
		background: #708090;
		color: #FFFAFA;
	}
	.mouseOut {
		background: #FFFAFA;
		color: #000000;
	}
</style>

<script type="text/javascript">

	var relNameSuggestHttp;
	var searchHttp;
	
	var completeDiv;
	var queryField;
	var suggestTable;
	var suggestTableBody;
	var currentSelectedSuggestion;
	var hasSuggested=false;
	var suggestionSize=0;
	var the_names;

	function createXMLHttpRequest() {
		if(window.ActiveXObject) {
			relNameSuggestHttp = new ActiveXObject("Microsoft.XMLHTTP");
		} else if(window.XMLHttpRequest) {
				relNameSuggestHttp = new XMLHttpRequest(); 
		}
	}

	function initVars() {
		queryField = document.getElementById("searchBox");
		suggestTable = document.getElementById("suggest_table"); 
		completeDiv = document.getElementById("popup");
		suggestTableBody = document.getElementById("suggest_table_body");
	}

	function doSuggest() {
		initVars(); 
		if (queryField.value.length >= 3) { 
			createXMLHttpRequest();
			var url = "./relationbrowser?query=" + escape(queryField.value);
			relNameSuggestHttp.open("GET", url, true);
			relNameSuggestHttp.onreadystatechange = displaySuggest;
			relNameSuggestHttp.send(null);
		} else { 
			clearSuggests();
		}
	}

	function displaySuggest() {
		if (relNameSuggestHttp.readyState == 4) {
			if (relNameSuggestHttp.status == 200) {
				clearSuggests();
				if(relNameSuggestHttp.responseXML.getElementsByTagName("Suggestion").length!=0){
					var name = relNameSuggestHttp.responseXML.getElementsByTagName("Suggestion")[0].firstChild.data;
					suggestionSize = relNameSuggestHttp.responseXML.getElementsByTagName("Suggestion").length;
					the_names = relNameSuggestHttp.responseXML.getElementsByTagName("Suggestion");
					setNames(the_names);
					hasSuggested = true;
				} else {
					hasSuggested = false;
				}
			}
			else if (relNameSuggestHttp.status == 204) {
				clearSuggests();
				hasSuggested = false;
			}
		} else {
			hasSuggested = false;
		}
	}
	
	function setNames(the_names) {
		clearSuggests();
    	var size = the_names.length;
    	setOffsets();

      	var row, cell, txtNode;
      	for (var i = 0; i < size; i++) {
          	var nextNode = the_names[i].firstChild.data;
          	row = document.createElement("tr");
          	cell = document.createElement("td");
          
          	row.onmouseover = function() {selectCurrentSuggestion(this.id)};
          	//row.onmouseover = function() {this.className='mouseOver';};
			row.id = i;
          	row.onclick = function(){ queryField.value=the_names[this.id].firstChild.data; clearSuggests(); };
          	row.setAttribute("bgcolor", "#FFFAFA");
          	row.setAttribute("border", "0");
          	//cell.onclick = function() { populateName(this); } ;                             

          	txtNode = document.createTextNode(nextNode);
          	cell.appendChild(txtNode);
          	row.appendChild(cell);
          	suggestTableBody.appendChild(row);
      	}
		currentSelectedSuggestion = -1;
    }

    function setOffsets() {
        var end = queryField.offsetWidth;
        var left = calculateOffsetLeft(queryField);
        var top = calculateOffsetTop(queryField) + queryField.offsetHeight;

        completeDiv.style.border = "black 1px solid";
        completeDiv.style.left = left + "px";
        completeDiv.style.top = top + "px";
        suggestTable.style.width = end + "px";
    }
        
    function calculateOffsetLeft(field) {
        return calculateOffset(field, "offsetLeft");
    }

    function calculateOffsetTop(field) {
        return calculateOffset(field, "offsetTop");
    }

    function calculateOffset(field, attr) {
        var offset = 0;
        while(field) {
            offset += field[attr]; 
            field = field.offsetParent;
        }
        return offset;
    }

    function populateSuggest(cell) {
        queryField.value = cell.firstChild.nodeValue;
        clearSuggests();
    }

    function clearSuggests() {
        var ind = suggestTableBody.childNodes.length;
        for (var i = ind - 1; i >= 0 ; i--) {
            suggestTableBody.removeChild(suggestTableBody.childNodes[i]);
        }
        completeDiv.style.border = "none";
        hasSuggested = false;
    }

	function selectCurrentSuggestion(nextToSelectSuggestion) {
		if (currentSelectedSuggestion != -1) 
			document.getElementById(currentSelectedSuggestion).className='mouseOut';
		currentSelectedSuggestion = nextToSelectSuggestion;
		if (suggestionSize != 0) 
			currentSelectedSuggestion = (currentSelectedSuggestion*1+suggestionSize*1)%suggestionSize;
		document.getElementById(currentSelectedSuggestion).className='mouseOver';
	}

	function onSearchBoxKeyDown(keyCode) {
		if (keyCode==38) { //up
			if (!hasSuggested) return;
			if (currentSelectedSuggestion == -1) selectCurrentSuggestion(suggestionSize-1);
			else selectCurrentSuggestion(currentSelectedSuggestion-1);
		}
		if (keyCode==40) {	//down
			if (!hasSuggested) return;
			selectCurrentSuggestion(currentSelectedSuggestion+1);
		}
	}
	
	function onSearchBoxKeyUp(keyCode) {
		if (keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40) return;//left, up, right down
		if (keyCode == 13) {	//enter
			if (document.getElementById("searchBox").value == "") return;
			if (!hasSuggested || currentSelectedSuggestion == -1) doSearch();
			else {
				document.getElementById("searchBox").value 
					= the_names[currentSelectedSuggestion].firstChild.data;
				doSearch();
			}
		} else {
			doSuggest();
		}
	}
	
	function doSearch() {
		if (document.getElementById("searchBox").value == "") return;
		window.location = "./search.jsp?query="+document.getElementById("searchBox").value+"&level=0";
	}
	
</script>
</head>
<body>
	<h1 align="center"><a href="./"><img src="3.jpg" width="320" height="116" border="0"></a></h1>
	<p align="center" />
    <%
    	String query = request.getParameter("query");
    	int queryID = Translator.getRelationID(query);
    	int level = Integer.parseInt(request.getParameter("level"));
    	
    	//Similar Relaiton Via Category
//    	ArrayList<StringDoublePair> simRelationCatC = RelationLinkFinder.getCatSimC(queryID, level);
//    	ArrayList<StringDoublePair> simRelationCatW = RelationLinkFinder.getCatSimW(queryID, level);
    	ArrayList simRelationCatC = RelationLinkFinder.getCatSimC(queryID, level);
    	ArrayList simRelationCatW = RelationLinkFinder.getCatSimW(queryID, level);
	    	
		//Similar Relaiton Via Prop
		ArrayList<StringDoublePair> simRelationPropC = RelationLinkFinder.getPropSimC(queryID);
    	ArrayList<StringDoublePair> simRelationPropW = RelationLinkFinder.getPropSimW(queryID);	    		    				
	    	
		//Similar Relaiton Via PropEx
		ArrayList<StringDoublePair> simRelationPropExC = new ArrayList<StringDoublePair>();
		ArrayList<StringDoublePair> simRelationPropExW = new ArrayList<StringDoublePair>();	    	
//	   	RelationLinkFinder.getPropExSim(query, simRelationPropExC, simRelationPropExW);//////
	    	
    	//Similar Relation Via Ins
    	ArrayList<StringDoublePair> simRelationInsC = RelationLinkFinder.getInsSim(queryID);

    	ArrayList<SubObjNum> relationInfo = RelationInfoRetriever.getCategoryPair(level, queryID);
    	//System.err.println("query:"+(query));
    	ArrayList<SubObjNum> instances = RelationInstanceFinder.findInstance(queryID, 0, 20);
    	//System.err.println("Is instances null?"+(instances==null));
    %>

<table align="center">
	<tr align="center">
  	<td>
  		<input type="text" id="searchBox" onkeyup="onSearchBoxKeyUp(event.keyCode);" 
  		onkeydown="onSearchBoxKeyDown(event.keyCode)" style="width:300px" value = "<%=query%>">
  		<input type="button" id="searchButton" value="Search" onclick="doSearch();">
  		<div style="position:absolute;z-index:11;" id="popup">
  		
			<table id="suggest_table" bgcolor="#FFFAFA" border="0" cellspacing="0" cellpadding="0" 
				style="width:300px" align="left"/>
				<tbody id="suggest_table_body" align="left">
				</tbody>
			</table>
		</div>
  	</td>
	</tr>
</table>

<div align="left">
<a><B>Level: <%=level%> </B></a>
<a href = "search.jsp?query=<%=query%>&level=<%=(level<6)?level+1:6%>"><B>[+]</B></a>
<a href = "search.jsp?query=<%=query%>&level=<%=(level>0)?level-1:0%>"><B>[-]</B></a>
<br />
</div>

<%

if ( instances.size() == 0 ) {
	out.println("<p /><p align=\"center\" /><font size=6><strong><em>Sorry, no such relation."
			+ "</em></strong></font>");
} else {

%>
<table width="913" border="2" align = "center" bordercolor="#6666FF">
	<tr>
		<td width="297"><div align="center"><strong>Similar Relations</strong></div></td>
		<td width="297"><div align="center"><strong>Relation Information</strong></div></td>
		<td width="297"><div align="center"><strong>Sample Instances</strong></div></td>
	</tr>
	<tr>
		<td valign="top">
      	<div align="left">
        <% 
		out.println("<strong> SimCat_Counting: </strong> <br>");
        int count = 0;
		for (Iterator i = simRelationCatC.iterator(); ) {
			out.println("<a href = \"./search.jsp?query=" + sdp.s + "&level=" + level
					+ "\">" + sdp.s + "</a>\t\t" + sdp.d + "<br />" );
			count++;
			if (count >= 10) break;
		}
		
		out.println("<br /><strong> SimCat_Weighted: </strong> <br />");
		count = 0;
		for (StringDoublePair sdp : simRelationCatW) {
			out.println("<a href = \"./search.jsp?query=" + sdp.s + "&level=" + level
					+ "\">" + sdp.s + "</a>\t\t" + sdp.d + "<br />" );
			count++;
			if (count >= 10) break;
		}
		
		out.println("<br /><strong> SimProp_Counting: </strong> <br />");
		count = 0;
		for (StringDoublePair sdp : simRelationPropC) {
			out.println("<a href = \"./search.jsp?query=" + sdp.s + "&level=" + level
					+ "\">" + sdp.s + "</a>\t\t" + sdp.d + "<br />" );
			count++;
			if (count >= 10) break;
			
		}
		
		out.println("<br /><strong> SimProp_Weighted: </strong> <br />");
		count = 0;
		for (StringDoublePair sdp : simRelationPropW) {
			out.println("<a href = \"./search.jsp?query=" + sdp.s + "&level=" + level
					+ "\">" + sdp.s + "</a>\t\t" + sdp.d + "<br />" );
			count++;
			if (count >= 10) break;
		}
		
		out.println("<br /><strong> SimPropEx_Counting: </strong> <br />");
		count = 0;
		for (StringDoublePair sdp : simRelationPropExC) {
			out.println("<a href = \"./search.jsp?query=" + sdp.s + "&level=" + level
					+ "\">" + sdp.s + "</a>\t\t" + sdp.d + "<br />" );
			count++;
			if (count >= 10) break;
		}
		
		out.println("<br /><strong> SimPropEx_Weighted: </strong> <br />");
		count = 0;
		for (StringDoublePair sdp : simRelationPropExW) {
			out.println("<a href = \"./search.jsp?query=" + sdp.s + "&level=" + level
					+ "\">" + sdp.s + "</a>\t\t" + sdp.d + "<br />" );
			count++;
			if (count >= 10) break;
		}
		
		out.println("<br /><strong> SimInstance: </strong> <br />");
		count = 0;
		for (StringDoublePair sdp : simRelationInsC) {
			out.println("<a href = \"./search.jsp?query=" + sdp.s + "&level=" + level
					+ "\">" + sdp.s + "</a>\t\t" + sdp.d + "<br />" );
			count++;
			if (count >= 10) break;
		}

		%>
		</div>
  		</td>
		<td valign="top">
			<div align="left">
        		<%
				out.println("<strong>Domain\tRange\tSize</strong> <p>");
				int k = 0;
				for (SubObjNum son : relationInfo) {
					if (k >= 20) break;
					out.println("<em>" + Translator.getCatName(son.sub) + "\t" 
						+ Translator.getCatName(son.obj) + "</em>\t" + son.num + "<p>");
					k++;
				}
				%>
        	</div>
        </td>
		<td valign="top">
			<div align="left">
				<%
				for (SubObjNum son : instances)
					out.println(Translator.getResName(son.sub) + "\t,\t" 
						+ Translator.getResName(son.obj) + " <p />");
				%>
			</div>
		</td>
	</tr>
</table>
<%
}
%>
</body>
</html>