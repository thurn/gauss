#!/bin/bash

# modified version of process_assets script by user DDRBoxman on Github.
# https://github.com/DDRBoxman/Android-SVG-Asset-Generator/
# Requires Inkscape to be installed.
# This script scales and creates images at the correct dpi level for Android.
# When creating svg files set the image size to the size that you want your mdpi images to be.

case $OSTYPE in
darwin*)
  inkscape="/Applications/Inkscape.app/Contents/Resources/bin/inkscape"
  ;;
*)
  inkscape="inkscape"
  ;;
esac

function processImageAndroid {
  file=$(basename $1)
  $inkscape -d 480 -e ./noughts-android/res/drawable-xxhdpi/${file/.svg}.png $1 >& /dev/null
  $inkscape -d 320 -e ./noughts-android/res/drawable-xhdpi/${file/.svg}.png $1 >& /dev/null
  $inkscape -d 240 -e ./noughts-android/res/drawable-hdpi/${file/.svg}.png $1 >& /dev/null
  $inkscape -d 160 -e ./noughts-android/res/drawable-mdpi/${file/.svg}.png $1 >& /dev/null
}

function processImageIOS {
  file=$(basename $1)
  $inkscape -d 326 -e ./noughts-ios/images/retina/${file/.svg}@2x.png $1 >& /dev/null
  $inkscape -d 163 -e ./noughts-ios/images/regular/${file/.svg}.png $1 >& /dev/null
}

for f in $(find ./assets/ios -name *.svg -type f) ;
do
  echo "Processing $f"
  processImageIOS $f
done
