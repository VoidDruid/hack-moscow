import traceback

from northstar.celery import app
from northstar.models import PlacesStatistics
from collector.loop import collect, redis_cities, CITY_DATA_SEP


@app.task(serializer='json')
def export_stats():
    print('Start exporting stats')
    data = {}
    for city in redis_cities:
        print(f'PROCESSING CITY: {city}')
        city_data = []
        for key in redis_cities.hkeys(city):
            key = key.decode()
            if CITY_DATA_SEP in key:
                continue
            try:
                city_data.append({
                    key: {
                        'count': int(redis_cities.hget(city, key)),
                        'location': {
                            'lat': float(redis_cities.hget(city, f'{key}{CITY_DATA_SEP}lat')),
                            'long': float(redis_cities.hget(city, f'{key}{CITY_DATA_SEP}long')),
                        },
                        'category': redis_cities.hget(city, f'{key}{CITY_DATA_SEP}cat').decode(),
                    },
                })
            except Exception as e:
                print(f"Error in export_stats for key '{key}': '{e}'")
        city_data.sort(key=lambda val: list(val.values())[0]['count'], reverse=True)
        data[city] = city_data[:250]
    PlacesStatistics(data=data).save()
    redis_cities.flush()
    print('Finished exporting stats')
