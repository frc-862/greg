from picamera import PiCamera
import time

camera = PiCamera()

for i in range(6):
    #camera.annotate_text = ("image%s" % i)  
    #camera.annotate_text_size = 50
    camera.brightness = 20
    camera.contrast = 25 #50
    camera.exposure_mode = 'off'
    camera.awb_mode = 'off'
    #print(camera.awb_gains)
    camera.awb_gains = [1.1, 1.4]
    #time.sleep(0.3)
    camera.capture('/media/pi/log-862/images/1-30-20/ring2/45ft/image%s.jpg' % i)

