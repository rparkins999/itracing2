# itracing2 

This version is forked from https://github.com/sylvek/itracing2

Changes from sylvek/itracing2 are:

* Updated to build and work with latest Android
* Dismissing ringtone notification does the same thing as touching it (usually stop ringtone)
* Added action on phone screen turned on (enables quick stop ringtone by pressing power-on button)
* Added preference option to restart itracing2 when phone is rebooted

## History 📓
itracing2 is a free and open source application allowing to manage "iTag" and "iBeacon" devices. It probably doesn't work with Apple™ AirTag™ devices, but I haven't tried.

 Around 2015, [Bluetooth LE](https://www.link-labs.com/blog/bluetooth-vs-bluetooth-low-energy) _(or Bluetooth 4)_ became popular on smartphones and a lot of gadget-keyring-bluetooth-brands rised from nowhere _(think about smart things)_.
It was the begining of "IoT" for everyone and low cost devices from China invaded the market 😾 .

Because "no name company" are only interested by selling devices, software was closed source and incredibly unusable.

Customers were so disappointated that Sylvain decided to create his own application.

In 2021, [Apple released their AirTags](https://en.wikipedia.org/wiki/AirTag) 😮 . That's pretty smart because those devices are pretty cheap and by using iPhone/iPad as sensors, AirTags can be found anywhere in the Earth. Yes, every AirTag has an unique device id and Apple probably knows who is the owner of a dedicated AirTag.

## Do you BLE? ⚙️

If you want to learn more about BLE, there a lot of articles on the internet.
- [A Practical Guide to BLE Throughput](https://interrupt.memfault.com/blog/ble-throughput-primer)
- [List of Bluetooth profiles](https://en.wikipedia.org/wiki/List_of_Bluetooth_profiles)
- [List of Service Class IDs](https://docs.microsoft.com/en-us/windows/uwp/devices-sensors/aep-service-class-ids)

iTracing2 implements ["Proximity Profile"](https://en.wikipedia.org/wiki/List_of_Bluetooth_profiles#Proximity_Profile_(PXP)) and should be compatible with a lot of devices.. **BUT** because [chinese iTag are so badly built](https://github.com/sylvek/itracing2/wiki/MLE-15), Sylvain hardcoded some stuff. _(prefer [Quintic PROXPR](https://github.com/sylvek/itracing2/wiki/Quintic-PROXR) chip if you can)_

## How that works?

You can configure itracing2 to take any of the following actions on any of the following events. It should work with all iTag or iBeacon devices, but some of them don't implement all functions.

###Actions

* Capture your current position _(you need a Map application like OSMAnd or RMap or Organic Maps to display it)_
* Start playing a ringtone on your phone
* Stop playing the ringtone on your phone
* Toggle the playing / not playing state of a ringtone on your phone
* Start vibrating your phone
* Stop vibrating your phone
* Toggle the vibrating / not vibrating state of your phone
* Capture current GPS position of your phone (_not_ the iTag)
* Play/Pause audio playlist
* call a custom Intent action (no extras possible - yet!)
* Call a custom URL (`GET` action)
* Call someone

###Events

* Single click on the iTag's button
* Double click on the iTag's button
* iTag gets connected
* iTag gets disconnected (powered off or out of range)
* Phone screen on

The ringtone can be specified for each combination of iTag and event. If you have a sound recorder, you can record a message for the phone to speak instead of a ringtone (might be useful if you tag your dog to make the phone call it to come back).

Capturing the GPS position requires Location permission.
Calling someone requires phone permission.

Starting a ringtone creates a notification, and touching or dismissing the notification stops it again. Capturing the GPS position also creates a notification and touching the notification will bring up your mapping app (if you have one installed) with the captured GPS position as the target location. Touching a position event in the iTag's event history will also bring up your mapping app with the captured GPS position as the target location.

If the iTag supports enabling and disabling the iTag beep when it loses the connection, you can change this setting from itracing2, but only while the iTag is connected. If you try to change it while the iTag is not connected you get an error message. If the iTag doesn't support changing it, it just doesn't work (no error). If you don't need the beep, turning it off will improve iTag battery life.

The range is about 100 metres outdoors and indoors reduces by about 20 meteres per intervening solid wall. If you put an iTag in your car and want to capture the GPS position where you parked it as soon as you get out of the car, put the iTag somewhere like under the spare wheel where there is a lot of metal around it to attenuate the signal and shorten the range.

I don't speak all of the languages that Sylvain's version supported and I don't trust Google Translate, so new messages that I have added are only in English. Pull requests with tanslations gratefully received.

##Building it

Source code is available at (https://github.com/rparkins999/itracing2). I recommend using git clone as the build script expects to be able to use [git](https://git-scm.com/). If you don't use git, see the comment at line 4 of app/build.gradle. It should build with current Android Studio versions, but you will need to set up a signing key since current Android versions won't let you load unsigned applications and I'm not giving you my own key. It expects to find a GeneratedKey.jks file in the directory ../keys from the project root. Android Studio can create a key for you if you don't already have one.

There should normally be a recent build with my signing key at (https://github.com/rparkins999/itracing2/releases). If you want to report an issue with my built version, please provide the build details from the version info page accessible from the top level menu.

