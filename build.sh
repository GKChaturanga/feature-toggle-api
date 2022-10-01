echo "Start compiling swiscom DetureToggle  " >&2
if ./gradlew build; then
     echo "Gradle task succeeded" >&2
  	sudo docker-compose build 
  	echo "Docker build is done " >&2
  	echo "Booting up docker  " >&2
	sudo docker-compose up --remove-orphans
  
else
  echo "Gradle task failed" >&2
fi
