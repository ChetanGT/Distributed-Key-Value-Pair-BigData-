cd /home/hduser/hadoop/zookeeper-3.4.9/zookeeper-3.4.9
javac ZKExists.java #checking whether Zookeeper Master node exists or not 
java ZKExists >exists.txt #returns value 0(not exits) or 1(exits) 
javac server.java
var=$(tail -n1 exists.txt)
if [ "$var" = 0 ]; then #checking for master existence using return value from ZKExists.java
	echo "INITIALIZING ...."
	javac ZKCreate.java #Compiling the ZKCreate.java
	java ZKCreate #Run the file
	var=$(tail -n1 META_ip.txt) #Get the server IP address
	echo $var>master_ip.txt
	(gnome-terminal --command "java server $var true" &) && sleep 0.8 && xdotool windowminimize $(xdotool search --class 'gnome-terminal' |sort|tail -1)#create server as a terminal and then minimize
	javac ZKCreate1.java
	java ZKCreate1
	var=$(tail -n2 META_ip.txt | head -n1)#Get the server1 IP address
	(gnome-terminal --command "java server $var false" &) && sleep 0.8 && xdotool windowminimize $(xdotool search --class 'gnome-terminal' |sort|tail -1)
	var=$(tail -n1 META_ip.txt) #Get the Server1 replica IP address 
	(gnome-terminal --command "java server $var false" &) && sleep 0.8 && xdotool windowminimize $(xdotool search --class 'gnome-terminal' |sort|tail -1)#creating the server replica as a terminal and then minimize
	javac ZKCreate2.java
	java ZKCreate2
	var=$(tail -n2 META_ip.txt | head -n1)
	(gnome-terminal --command "java server $var false" &) && sleep 0.8 && xdotool windowminimize $(xdotool search --class 'gnome-terminal' |sort|tail -1)
	var=$(tail -n1 META_ip.txt)
	(gnome-terminal --command "java server $var false" &) && sleep 0.8 && xdotool windowminimize $(xdotool search --class 'gnome-terminal' |sort|tail -1)
	echo "MASTER CREATED"
else
	echo "MASTER EXISTS"  #if master exits then excecute the following commands
	head -n1 META.txt > temp.txt 
	rm META.txt
	mv temp.txt META.txt
	var=$(tail -n1 master_ip.txt)
		(gnome-terminal --command "java server $var true" &) && sleep 0.8 && xdotool windowminimize $(xdotool search --class 'gnome-terminal' |sort|tail -1)
	#javac ZKCreate1.java
	java ZKCreate1
	var=$(tail -n2 META_ip.txt | head -n1)
		(gnome-terminal --command "java server $var false" &) && sleep 0.8 && xdotool windowminimize $(xdotool search --class 'gnome-terminal' |sort|tail -1)
	var=$(tail -n1 META_ip.txt)
		(gnome-terminal --command "java server $var false" &) && sleep 0.8 && xdotool windowminimize $(xdotool search --class 'gnome-terminal' |sort|tail -1)
	#javac ZKCreate2.java
	java ZKCreate2
	var=$(tail -n2 META_ip.txt | head -n1)
		(gnome-terminal --command "java server $var false" &) && sleep 0.8 && xdotool windowminimize $(xdotool search --class 'gnome-terminal' |sort|tail -1)
		
	var=$(tail -n1 META_ip.txt)
		(gnome-terminal --command "java server $var false" &) && sleep 0.8 && xdotool windowminimize $(xdotool search --class 'gnome-terminal' |sort|tail -1)
		
	
fi
var=$(tail -n1 master_ip.txt) #getting the master IP  address from the file.
javac client.java
java client $var 6000  #running the using IP address





