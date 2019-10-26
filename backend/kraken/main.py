from fastapi import FastAPI
from pydantic import BaseModel

from redis_sync import BaseRedisSyncStorage
from redis import Redis
from starlette.responses import JSONResponse
import json


class WriteLocation(BaseModel):
    long: float
    lat: float
    duration: int


redis_db = BaseRedisSyncStorage(Redis(host='localhost', port=6379, db='0'), '')
app = FastAPI()


@app.post("/api/location/{uid}")
async def create_item(uid: str, item: WriteLocation):
    try:
        redis_db.lpush(uid, json.dumps(item.dict()))
    except Exception as e:
        return JSONResponse(content={"ok": False, "error":"Couldn't write to DB", 'error_detail': str(e)})
    return JSONResponse(content={"ok": True})
