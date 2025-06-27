# itracing2 

This version is forked from https://github.com/sylvek/itracing2

Changes from sylvek/itracing2 are:

* Updated to build and work with latest Android
* Dismissing ringtone notification does the same thing as touching it (usually stop ringtone)
* Added action on phone screen turned on (enables quick stop ringtone by pressing power-on button)
* Added preference option to restart itracing2 when phone is rebooted

## History 📓
itracing2 is a free and open source application allowing to manage "iTag" devices.

Around 2015, [Bluetooth LE](https://www.link-labs.com/blog/bluetooth-vs-bluetooth-low-energy) _(or Bluetooth 4)_ became popular on smartphones and a lot of gadget-keyring-bluetooth-brands rised from nowhere _(think about smart things)_.
It was the begining of "IoT" for everyone and low cost devices from China invaded the market 😾 .

Because "no name company" are only interested by selling devices, software was closed source and incredibly unusable.

Customers were so disappointated that Sylvain decided to create his own application.

In 2021, [Apple released his AirTags](https://en.wikipedia.org/wiki/AirTag) 😮 . That's pretty smart because those devices are pretty cheap and by using iPhone/iPad as sensors, AirTags can be found anywhere in the Earth. Yes, every AirTag has an unique device id and Apple probably knows who is the owner of a dedicated AirTag.

## Do you BLE? ⚙️

If you want to learn more about BLE, there a lot of articles on the internet.
- [A Practical Guide to BLE Throughput](https://interrupt.memfault.com/blog/ble-throughput-primer)
- [List of Bluetooth profiles](https://en.wikipedia.org/wiki/List_of_Bluetooth_profiles)
- [List of Service Class IDs](https://docs.microsoft.com/en-us/windows/uwp/devices-sensors/aep-service-class-ids)

iTracing2 implementes ["Proximity Profile"](https://en.wikipedia.org/wiki/List_of_Bluetooth_profiles#Proximity_Profile_(PXP)) and should be compatible with a lot of devices.. **BUT** because [chinese iTag are so badly built](https://github.com/sylvek/itracing2/wiki/MLE-15), Sylvain hardcoded some stuff. _(prefer [Quintic PROXPR](https://github.com/sylvek/itracing2/wiki/Quintic-PROXR) chip if you can)_

## How that works?

[Read me on WIKI](https://github.com/sylvek/itracing2/wiki)

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
Calling someone needs phone permission.

Starting a ringtone creates a notification, and touching or dismissing the notification stops it again. Capturing the GPS position also creates a notification and touching the notification will bring up your mapping app (if you have one installed) with the captured GPS position as the target location. Touching a position event in the iTag's event history will also bring up your mapping app with the captured GPS position as the target location.

If the iTag supports enabling and disabling the iTag beep when it loses the connection, you can change this setting from itracing2, but only while the iTag is connected. If you try to change it while the iTag is not connected you get an error message. If the iTag doesn't support changing it, it just doesn't work (no error). If you don't need the beep, turning it off will improve iTag battery life.

The range is about 100 metres outdoors and indoors reduces by about 20 meteres per intervening solid wall. If you put an iTag in your car and want to capture the GPS position where you parked it as soon as you get out of the car, put the iTag somewhere like under the spare wheel where there is a lot of metal around it to attenuate the signal and shorten the range.
