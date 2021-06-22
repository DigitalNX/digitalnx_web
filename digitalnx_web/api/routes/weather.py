from . import *
from datetime import datetime

@api.route('/weather_report')
def weather_report():
    number_of_days = 7
    location = WeatherWidget.query.first().location
    url = 'http://api.openweathermap.org/data/2.5/forecast?q={0}&cnt={1}&appid={2}&units=metric'.format(
                   'location', number_of_days, current_app.config['WEATHER_TOKEN'])
    try:
        weather_data = add_api_cache(url)
        if weather_data == None or len(weather_data) == 0:
            return {}
    except:
        return {}

    from datetime import date
    import calendar

    forcast_days = make_forcast_days(calendar.day_name[date.today().weekday()][:3])
    output = []

    months = [ "January",
               "Febuary",
               "March",
               "April",
               "May",
               "June",
               "July",
               "August",
               "September",
               "October",
               "November",
               "December" ]
    today = datetime.today()
    info_obj = {}
    info_obj['location'] = location
    info_obj['date'] = str(today.day) + ' ' + str(months[today.month]) + ' ' + str(today.year)
    output.append(info_obj)

    temperatue_data = []
    for d in range(len(forcast_days)):
        res = {}
        res['temperature'] = int(weather_data['list'][d]['main']['temp'])
        res['day'] = forcast_days[d]
        temperatue_data.append(dict(res))
    output.append(temperatue_data)

    response = jsonify(output)
    response.headers.add('Access-Control-Allow-Origin', '*')
    return response 

def make_forcast_days(today):
    days_of_week = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'] 
    if today not in days_of_week:
        raise ValueError("Error: Invalid day for make_forcast_list function.") 

    day = []
    todays_index = days_of_week.index(today)
    for _ in range(7):
        day.append(days_of_week[todays_index % 7])
        todays_index = todays_index + 1

    return day