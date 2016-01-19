brew update
brew outdated p7zip || brew upgrade p7zip
mkdir macwork
mv $1.dmg macwork
cd macwork
7z -y e $1.dmg 
mkdir Foo
cd Foo
xar -xf ../*.pkg
cd jdk*.pkg
cat Payload | gunzip -dc |cpio -i
7z -r a ../../../$1.zip Contents
cd ../../..
rm -rf macwork