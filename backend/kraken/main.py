import json

from fastapi import FastAPI
from redis import Redis
from starlette.responses import JSONResponse

from common.redis_sync import BaseRedisSyncStorage
from common.models import ProvidedLocation

redis_db = BaseRedisSyncStorage(Redis(host='localhost', port=6379, db='0'), '')
app = FastAPI()


@app.post("/api/location/{uid}")
async def create_item(uid: str, item: ProvidedLocation):
    try:
        redis_db.lpush(uid, json.dumps(item.dict()))
    except Exception as e:
        return JSONResponse(content={
            "ok": False,
            "error": "Couldn't save user location",
            'error_detail': str(e)
        })
    return JSONResponse(content={"ok": True})
