
RELATIVE_PATH="`dirname \"$0\"`"
SCRIPT_PATH=$(realpath $RELATIVE_PATH)
PROJECT_PATH=$(realpath $SCRIPT_PATH/..)

cd $PROJECT_PATH

reset

scalac main.scala
scala main

rm -rf main.class
rm -rf main$.class