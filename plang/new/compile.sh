#!/bin/bash

PREFIX=.compile
PRE_OLD=$PREFIX/.old
[ -d $PRE_OLD ] || mkdir $PRE_OLD -p

pre_comp_old_file=
function pre_comp() {
    local file=$1
    if [ ! -f $PRE_OLD/$file ] || ! diff $file $PRE_OLD/$file; then
	sed $file -ne 's/\s\([A-Z][a-z]*\)\W/\1/gp' | \
	    sort | \
	    uniq | \
	    xargs pre_comp
    fi
}

case "x$1" in 
    xclean) 
	rm *.class
	rm $PRE_OLD/*
	;;
    xbuild)
	for java in *.java; do
	    if [ ! -f $PRE_OLD/$java ] || ! diff $java $PRE_OLD/$java; then
		if javac $java; then
		    cp $java $PRE_OLD/$java
		fi
	    fi
	done
	;;
    xtest|xrun)
	if ! java CompileTest $2 2>/tmp/compile.sh.error >Test.j; then
	    echo "Run failed"
	    cat /tmp/compile.sh.error
	else
	    if [ -z "$(oolong Test.j 2>&1 | tee /tmp/compile.sh.error)" ]; then
		result=$(java RunTest)
		if [ $1 = run ]; then
		    echo "$result"
		else
		    if [ -n "$2" ]; then
			wanted=$(echo "scale=16; .5-.5+$2" | bc | perl -pne '1 while s/(?<!\.)0$//; $_ = 0 . $_ if /^\./')
			if [ "${wanted:0:15}" = "${result:0:15}" ]; then
			    echo "Passed: $wanted"
			else
			    echo "Failed: wanted $wanted, got $result"
			    cat Test.j
			fi
		    fi
		fi
	    else
		echo "Oolong failed"
		xargs echo '> ' </tmp/compile.sh.error
		cat Test.j
	    fi
	fi
	rm /tmp/compile.sh.error
	;;
    *)
	echo "Usage: $(basename $0) [build|clean|test|run]"
	exit 1
	;;
esac
