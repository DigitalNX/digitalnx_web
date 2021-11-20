# DigitalNX Web app

*DigitalNX Web* is a web app consisting of a back-end API and a front-end (SPA) for running a basic IoT setup. The back-end in this project is a RESTFul API server written in Java using Spring, and the front-end is written in Javascript and uses Vue.js to build a web user interface. This separation makes it easy to integrate the back-end with other platforms such as mobile apps and other controllers.

![Alt text](https://raw.githubusercontent.com/DigitalNX/digitalnx.github.io/main/demo/screenshot.png)

## Installation
First make sure you have [Node](https://nodejs.org) and OpenJDK 11+ installed, then run the following command:

```
make install
```

And to start the project use:
```
make run
```
Now you should be able to view and login to the front-end at *http://127.0.0.1:8080* with the username **admin** and password **password**.

(Optional) To enable weather forecast, sign up on [openweathermap.org](http://openweathermap.org) for free and get a token and replace it with *\<TOKEN\>* in *backend/application.yml*.

## Widgets
The front-end consists of widgets such as relay control widget, note-taking widget, ... which are derived from base widgets MediumBoxWidget, SmallBoxWidget, etc. These base widgets take advantage of Bootstrap styling system. As a result the deriving widgets get appropriate sizing and positioning on the page regardless of screen size and device.

### Current widgets

#### Weather widget
Shows the current weather and forecast of the next 7 days. This widget retrieves the weather data from the back-end and the back-end itself uses an online API service ([openweathermap.org](http://openweathermap.org)) for this job. Besides retrieving them, the back-end also simplifies them to an easily-processable JSON object.

#### Relay control widget

This widget can be used to turn on/off relays that are registered in the back-end. These relays can be attached to any appliance such as a standard wall lamp, irrigation system for gardens or any other device.

#### Sensor widget

This widget can be used to view sensor values that are registered in the back-end. These sensors can be different types such as *boolean* and *floating point* sensor.

#### Note widget
A simple note-taking widget that supports create, read, update and delete (CRUD) via a database in the back-end.

#### Info widget
Shows some information that the back-end info route provides. This information can be any text such as the outputs of connected sensors, information about a social media account or any other information that the user decides to add to this widget.
 
## Backend Routes
Interface routes:
- `[GET] /api/widgets/` - returns registered widgets in the database
- `[GET] /api/layout/` - returns home layout widgets
- `[POST] /api/layout/new` - accepts a post request consisting of a widget `name` and `id` and adds it to the home layout 
- `[DELETE] /api/layout/<int:layout_id>` - removes widget from home layout with id `layout_id`

Relay routes:
- `[GET] /api/relays/` - returns registered relays in the database
- `[GET] /api/relays/<int:group_id>/` - returns relays with group id `group_id`
- `[GET] /api/relay/<int:relay_id>` - returns relay info (address, device name, group id and the status) of relays with id `relay_id` 
- `[GET] /api/relay/<int:relay_id>/<string:action>` - turns relay with id `relay_id` *on* or *off* depending on the `action` value

Sensor routes:
- `[GET] /api/sensors/` - returns registered sensors in the database
- `[GET] /api/sensors/<int:group_id>/` - returns sensors with group id `group_id`
- `[GET] /api/sensor/<int:sensor_id>` - returns sensor info (address, device name, group id, sensor_type and value) with id `sensor_id` 
- `[GET] /api/sensor/<int:sensor_id>/data` - returns output of sensor with id `sensor_id`

Note routes:
- `[GET] /api/notes/` - returns notes stored in the database
- `[GET] /api/note/<int:note_id>` - returns title and body of the note with id `note_id`
- `[POST] /api/note/new [POST]` - accepts a post request consisting of a note title and body and adds it to the database 
- `[POST] /api/note/<int:note_id>/update` - accepts a post request consisting of a note tile and body and update the note with id `note_id` with it
- `[DELETE] /api/note/<int:note_id>/` - removes the note with id `note_id`

Weather routes:
- `[GET] /api/weatherForecast` - returns the current weather info and forecast of the next 7 days

Reley scheduling routes:
- `[GET] /schedules/relay/all` - returns all registered schedules for relays
- `[GET] /schedules/relay/<int:relay_id>` - returns schedule for the relay with id `relay_id` (if exists)
- `[POST] /schedules/relay/<int:relay_id>/add` - accepts a post request containing period, execution duration and description to add for scheduling `relay_id`
- `[DELETE] /schedules/relay/<int:relay_id>/` - removes the relay schedule for relay with id `relay_id`

Info routes:
- `[GET] /api/infoData` - returns info widgets data (each info widget data as an entry in an array)

## License
This project is licenced under **GNU Public License 3**. Visit the [LICENSE](https://github.com/DigitalNX/digitalnx_web/blob/main/LICENSE) file for more info.

