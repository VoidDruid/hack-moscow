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


redis_db = BaseRedisSyncStorage(Redis(host='localhost', port=6379, db='13'), "")
app = FastAPI()


@app.post("/users/{uid}/location")
async def create_item(uid: str, item: WriteLocation):
    try:
        redis_db.lpush(uid, json.dumps(item))
    except:
        return JSONResponse(content={"ok": False, "Error": "Couldn't write to DB"})
    return JSONResponse(content={"ok": True})
