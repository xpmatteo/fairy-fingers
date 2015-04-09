# Imports the monkeyrunner modules used by this program
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice
import time
import random


#touch num_seg times the screen and return the middle point between the last twos
def drawLine(x,y,num_seg):
    device.touch(x,y, MonkeyDevice.DOWN)
    for i in xrange(2,num_seg):
        ox = x
        oy = y
        x += random.randrange(-50,50)
        y += random.randrange(-50,50)
        time.sleep(.05)
        device.touch(x,y, MonkeyDevice.MOVE)
    device.touch(x,y,MonkeyDevice.UP)
    return ((x+ox)/2,(y+oy)/2)


# check for minimuum and maximum constraints on the color in point x y of the image
def checkColor(shot,x,y,mr=0,mg=0,mb=0,MR=255,MG=255,MB=255):
    (al, red, gr, bl) = shot.getRawPixel (x,y)
    print  "COLOR : " + str(red) + " " + str(gr) + " " + str(bl)
    return (red >= mr and red <= MR and
            gr  >= mg and gr  <= MG and
            bl  >= mb and bl  <= MB)


# Connects to the current device, returning a MonkeyDevice object
device = MonkeyRunner.waitForConnection()

# Installs the Android package. Notice that this method returns a boolean, so you can test
# to see if the installation worked..
#device.installPackage('AndroidDependent/build/outputs/apk/AndroidDependent-debug.apk')

#run activity on component
package = "com.tdd4android.fairyfingers"
activity = "com.tdd4android.fairyfingers.DrawActivity"
runComponent = package + "/" + activity

device.startActivity(component=runComponent)

time.sleep(.5)

#######################################
###  Single Finger
###       touch and drag finger

(x,y) = drawLine(200,200,10)


###
###       =>  one line appears with starting color  rgb(204,51,53)
###       =>  check last point
####              assert >200, <100,<100 due to possible started decay

shot = device.takeSnapshot()
# Writes the screenshot to a file
#shot.writeToFile('shot1.png','png')
assert (checkColor(shot, x,y, mr=200,MG=100,MB=100)), "ERROR: line was not draw"
print "Test Line Drawing OK"

###
###       =>  the line disappears within half second
###

time.sleep(.5)
shot = device.takeSnapshot()
#shot.writeToFile('shot2.png','png')
assert (checkColor(shot, x,y, mr=235,mg=235,mb=235)), "ERROR: line did not disappear"
print "Test Line Decaying OK"

#
######################################



######################################
###    Multiple Colors
###
###     Draw other three lines

(x,y) = drawLine(100,100,5)
(x,y) = drawLine(150,150,5)
(x,y) = drawLine(200,200,8)

###
###           =>  the colour of the last line should be rgb(0, 160, 176)
###

shot = device.takeSnapshot()
assert (checkColor(shot, x,y, MR=50,mg=160,MG=190,mb=176,MB=200)), "ERROR: wrong change of colour"
print "Test Line Color Change OK"

#
######################################


#Quit the application
device.shell('am force-stop ' + package)
