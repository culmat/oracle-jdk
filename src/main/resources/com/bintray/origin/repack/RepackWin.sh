7z e $1.exe 
7z -otools x tools.zip
rm tools.zip
find tools -name "*.pack" -exec sh -c 'unpack200 -r "{}" "$(dirname "{}")/$(basename "{}" .pack).jar"' \;
cd tools
7z -r a ../$1.zip *
cd ..
rm -rf tools
