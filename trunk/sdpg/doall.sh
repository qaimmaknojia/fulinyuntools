#!/bin/bash

echo writing showdata.jnl...
echo "set data \""$1"\"" > showdata.jnl
echo "show data" >> showdata.jnl
echo "showdata.jnl:"
cat showdata.jnl

echo extracting metadata...
echo "ferret -memsize 16 -gif -server -script header.jnl showdata.jnl header.xml"
ferret -memsize 16 -gif -server -script header.jnl showdata.jnl header.xml

#manual remove of illegal characters may be needed here

echo generating mxml source code...
echo "./preprocess header.xml"
./preprocess header.xml

echo compiling mxml source code...
echo "mxmlc header.mxml"
mxmlc header.mxml

echo deploying swf file...
echo "cp header.swf /home/$USER/public_html/sdpg/"
cp header.swf /home/$USER/public_html/sdpg/
echo "cp index.html /home/$USER/public_html/sdpg/"
cp index.html /home/$USER/public_html/sdpg/

echo opening the webpage in firefox...
echo "firefox http://localhost/sdpg/"
firefox http://localhost/sdpg/

echo all finished!

