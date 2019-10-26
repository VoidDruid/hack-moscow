import json

from fastapi import FastAPI
from redis import Redis
from starlette.responses import JSONResponse

from common.here_api import here
from common.models import ProvidedLocation
from common.redis_sync import BaseRedisSyncStorage

redis_db = BaseRedisSyncStorage(Redis(host='localhost', port=6379, db='0'), '')
app = FastAPI()


@app.post("/api/location/{uid}")
async def create_item(uid: str, item: ProvidedLocation):
    here_place = here.search({'long': item.long, 'lat': item.lat})
    if here_place is None:
        return JSONResponse(content={
            "ok": False,
            "error": "Wrong parameters"
        })
    place = {
        'long': item.long, 'lat': item.lat,
        'duration': item.duration, 'city': item.city,
        'title': here_place['title'],
        'category': {
            'title': here_place['category']['title'],
            'id': here_place['category']['id']
        }
    }

    try:
        redis_db.lpush(uid, json.dumps(place))
    except Exception as e:
        return JSONResponse(content={
            "ok": False,
            "error": "Couldn't save user location",
            'error_detail': str(e)
        })
    return JSONResponse(content={"ok": True})
