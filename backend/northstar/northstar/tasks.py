from northstar.celery import app


@app.task(serializer='json')
def export_tasks():
    pass
