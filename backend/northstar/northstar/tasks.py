from northstar.celery import app
from collector.loop import redis_cities, collect


@app.task(serializer='json')
def export_tasks():
    pass
