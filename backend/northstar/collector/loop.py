import time

from redis import Redis

from conf.settings import COLLECT_TIMEOUT

from common.redis_sync import BaseRedisSyncStorage

redis = BaseRedisSyncStorage(Redis(host='localhost', port=6379, db='0'), '')
USER_LOCATIONS_PER_SCAN = 5


def loop():
    while True:
        collect()
        time.sleep(COLLECT_TIMEOUT)


def collect():
    for key in redis:
        for _ in range(USER_LOCATIONS_PER_SCAN):
            process_key(key)


def process_key(key):
    value =