#!/bin/sh
set -e

inkscape="/Applications/Inkscape.app/Contents/Resources/bin/inkscape"

$inkscape -d 72 -e 480p/blue.png blue.svg
$inkscape -d 90 -e 600p/blue.png blue.svg
$inkscape -d 96 -e 640p/blue.png blue.svg
$inkscape -d 140.25 -e 935p/blue.png blue.svg
$inkscape -d 150.75 -e 1005p/blue.png blue.svg
$inkscape -d 159.45 -e 1063p/blue.png blue.svg
$inkscape -d 162 -e 1080p/blue.png blue.svg
$inkscape -d 188.25 -e 1255p/blue.png blue.svg
$inkscape -d 324 -e 2160p/blue.png blue.svg

$inkscape -d 72 -e 480p/red.png red.svg
$inkscape -d 90 -e 600p/red.png red.svg
$inkscape -d 96 -e 640p/red.png red.svg
$inkscape -d 140.25 -e 935p/red.png red.svg
$inkscape -d 150.75 -e 1005p/red.png red.svg
$inkscape -d 159.45 -e 1063p/red.png red.svg
$inkscape -d 162 -e 1080p/red.png red.svg
$inkscape -d 188.25 -e 1255p/red.png red.svg
$inkscape -d 324 -e 2160p/red.png red.svg

$inkscape -d 72 -e 480p/background.png background.svg
$inkscape -d 90 -e 600p/background.png background.svg
$inkscape -d 96 -e 640p/background.png background.svg
$inkscape -d 140.25 -e 935p/background.png background.svg
$inkscape -d 150.75 -e 1005p/background.png background.svg
$inkscape -d 159.45 -e 1063p/background.png background.svg
$inkscape -d 162 -e 1080p/background.png background.svg
$inkscape -d 188.25 -e 1255p/background.png background.svg
$inkscape -d 324 -e 2160p/background.png background.svg
