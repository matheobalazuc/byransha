#!/bin/bash
cd $HOME/frontend/Project_DS4H/ && git pull
/home/byransha/jre/bin/java -cp $(find /home/byransha/backend/bin -maxdepth 1 | tr '\n' ':') byransha.web.WebServer | tee /home/byransha/stdout.txt
